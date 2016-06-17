package us.kbase.clusterservice.test;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import junit.framework.Assert;

import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

import us.kbase.clusterservice.ClusterResults;
import us.kbase.clusterservice.ClusterServicePyLocalClient;
import us.kbase.clusterservice.ClusterServiceRLocalClient;
import us.kbase.common.service.ServerException;
import us.kbase.common.service.Tuple2;
import us.kbase.kbasefeaturevalues.EstimateKResult;
import us.kbase.kbasefeaturevalues.FloatMatrix2D;

public class ClusterServiceTest {
    private static File rootTempDir = null;

    @Test
    public void pyTest() throws Exception {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("mac"))
            return;
        File workDir = generateTempDir(rootTempDir, "test_clusterservice_py1_", "");
        workDir.mkdirs();
        ClusterServicePyLocalClient cl = new ClusterServicePyLocalClient(workDir);
        cl.setBinDir(new File("bin"));
        ClusterServiceRLocalClient cl2 = new ClusterServiceRLocalClient(workDir);
        cl2.setBinDir(new File("bin"));
        try {
            FloatMatrix2D matrix = getSampleMatrix();
            List<Long> clusterLabels = cl.clusterKMeans(
                    matrix, 3L, null, null, null, null).getClusterLabels();
            for (int pos = 0; pos < clusterLabels.size(); pos++)
                clusterLabels.set(pos, 1 + (long)clusterLabels.get(pos));
            checkClusterLabels(clusterLabels);
            ClusterResults cr2 = cl2.calcClusterQualities(matrix, clusterLabels);
            Assert.assertEquals(3, cr2.getMeancor().size());
            Assert.assertEquals(3, cr2.getMsecs().size());
        } catch (ServerException ex) {
            System.out.println(ex.getData());
            throw ex;
        }
    }

    @Test
    public void rTest() throws Exception {
        ClusterServiceRLocalClient cl = getRClient("r1");
        FloatMatrix2D matrix = getSampleMatrix();
        try {
            EstimateKResult estK = cl.estimateKNew(matrix, null, null, null, null, null, null, 123L);
            long k = estK.getBestK();
            Assert.assertEquals(3, k);
            Assert.assertEquals(5, estK.getEstimateClusterSizes().size());
            for (int i = 0; i < estK.getEstimateClusterSizes().size(); i++) {
                Tuple2 <Long, Double> item = estK.getEstimateClusterSizes().get(i);
                Assert.assertEquals(2L + i, (long)item.getE1());
                Assert.assertTrue((double)item.getE2() > 0);
            }
            Long randomSeed = 403L;
            ClusterResults cr1 = cl.clusterKMeans(matrix, k, null, null, randomSeed, null);
            List<Long> clusterLabels = cr1.getClusterLabels();
            checkClusterLabels(clusterLabels);
            ClusterResults cr2 = cl.clusterHierarchical(matrix, "", "", 0.5, null, null);
            //System.out.println(cr2);
            String dendrogram = cr2.getDendrogram();
            Assert.assertTrue(dendrogram.startsWith("("));
            Assert.assertTrue(dendrogram.endsWith(");"));
            ClusterResults cr3 = cl.clustersFromDendrogram(matrix, dendrogram, 0.2);
            List<Long> clusterLabels2 = cr3.getClusterLabels();
            checkClusterLabels(clusterLabels2);
            ClusterResults cr4 = cl.calcClusterQualities(matrix, clusterLabels);
            Assert.assertEquals(3, cr4.getMeancor().size());
            Assert.assertEquals(3, cr4.getMsecs().size());
        } catch (ServerException ex) {
            System.out.println(ex.getData());
            throw ex;
        }
    }

    @Test
    public void rEstimateKNewTest() throws Exception {
        ClusterServiceRLocalClient cl = getRClient("r_estimate_k_new");
        FloatMatrix2D matrix = getSampleMatrix();
        try {
            EstimateKResult estK = cl.estimateKNew(matrix, 3L, null, null, null, null, null, 123L);
            long k = estK.getBestK();
            Assert.assertEquals(3, k);
            Assert.assertEquals(4, estK.getEstimateClusterSizes().size());
            for (int i = 0; i < estK.getEstimateClusterSizes().size(); i++) {
                Tuple2 <Long, Double> item = estK.getEstimateClusterSizes().get(i);
                Assert.assertEquals(3L + i, (long)item.getE1());
                Assert.assertTrue((double)item.getE2() > 0);
            }
        } catch (ServerException ex) {
            System.out.println(ex.getData());
            throw ex;
        }
    }

    private ClusterServiceRLocalClient getRClient(String testType) {
        File workDir = generateTempDir(rootTempDir, "test_clusterservice_" + testType + "_", "");
        workDir.mkdirs();
        ClusterServiceRLocalClient cl = new ClusterServiceRLocalClient(workDir);
        cl.setBinDir(new File("bin"));
        return cl;
    }

    private static void checkClusterLabels(List<Long> labels) throws Exception {
        String errMsg = "Unexpected labels: " + labels;
        Assert.assertEquals(errMsg, 7, labels.size());
        long c1 = labels.get(0);
        Assert.assertEquals(errMsg, c1, (long)labels.get(1));
        long c2 = labels.get(2);
        Assert.assertEquals(errMsg, c2, (long)labels.get(3));
        long c3 = labels.get(4);
        Assert.assertEquals(errMsg, c3, (long)labels.get(5));
        Assert.assertEquals(errMsg, c3, (long)labels.get(6));
    }
    
    private static FloatMatrix2D getSampleMatrix() {
        List<List<Double>> values = new ArrayList<List<Double>>();
        values.add(Arrays.asList(13.0, 2.0, 3.0));
        values.add(Arrays.asList(10.9, 1.95, 2.9));
        values.add(Arrays.asList(2.45, 13.4, 4.4));
        values.add(Arrays.asList(2.5, 11.5, 3.55));
        values.add(Arrays.asList(-1.05, -2.0, -14.0));
        values.add(Arrays.asList(-1.2, -2.25, -13.2));
        values.add(Arrays.asList(-1.1, -2.1, -15.15));
        return new FloatMatrix2D().withValues(values)
                .withRowIds(Arrays.asList("g:1", "g,2", "g-3", "g(4", "g)5", "g;6", "g\"7"))
                .withColIds(Arrays.asList("c1", "c2", "c3"));
    }

    @BeforeClass
    public static void prepare() throws Exception {
        rootTempDir = new File("work");
        if (!rootTempDir.exists())
            rootTempDir.mkdirs();
        for (File dir : rootTempDir.listFiles()) {
            if (dir.isDirectory() && dir.getName().startsWith("test_clusterservice_"))
                try {
                    deleteRecursively(dir);
                } catch (Exception e) {
                    System.out.println("Can not delete directory [" + dir.getName() + "]: " + e.getMessage());
                }
        }
    }

    @After
    public void cleanup() throws Exception {
        //if (rootTempDir != null && rootTempDir.exists())
        //    deleteRecursively(rootTempDir);
    }
    
    private static File generateTempDir(File parentTempDir, String prefix, String suffix) {
        long start = System.currentTimeMillis();
        while (true) {
            File dir = new File(parentTempDir, prefix + start + suffix);
            if (!dir.exists()) {
                dir.mkdirs();
                return dir;
            }
            start++;
        }
    }

    private static void deleteRecursively(File fileOrDir) {
        if (fileOrDir.isDirectory() && !Files.isSymbolicLink(fileOrDir.toPath()))
            for (File f : fileOrDir.listFiles()) 
                deleteRecursively(f);
        fileOrDir.delete();
    }
}


