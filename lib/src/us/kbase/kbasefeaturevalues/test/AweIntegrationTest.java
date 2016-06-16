package us.kbase.kbasefeaturevalues.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import junit.framework.Assert;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.log.Log;
import org.eclipse.jetty.util.log.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import us.kbase.auth.AuthException;
import us.kbase.auth.AuthService;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonClientCaller;
import us.kbase.common.service.ServerException;
import us.kbase.common.service.Tuple2;
import us.kbase.common.service.Tuple7;
import us.kbase.common.service.UObject;
import us.kbase.common.service.UnauthorizedException;
import us.kbase.common.utils.ProcessHelper;
import us.kbase.kbasefeaturevalues.BuildFeatureSetParams;
import us.kbase.kbasefeaturevalues.ClusterHierarchicalParams;
import us.kbase.kbasefeaturevalues.ClusterKMeansParams;
import us.kbase.kbasefeaturevalues.FeatureClusters;
import us.kbase.kbasefeaturevalues.ClustersFromDendrogramParams;
import us.kbase.kbasefeaturevalues.CorrectMatrixParams;
import us.kbase.kbasefeaturevalues.EstimateKParams;
import us.kbase.kbasefeaturevalues.EstimateKParamsNew;
import us.kbase.kbasefeaturevalues.EstimateKResult;
import us.kbase.kbasefeaturevalues.ExpressionMatrix;
import us.kbase.kbasefeaturevalues.FloatMatrix2D;
import us.kbase.kbasefeaturevalues.GetMatrixDescriptorParams;
import us.kbase.kbasefeaturevalues.GetMatrixStatParams;
import us.kbase.kbasefeaturevalues.GetSubmatrixStatParams;
import us.kbase.kbasefeaturevalues.KBaseFeatureValuesClient;
import us.kbase.kbasefeaturevalues.KBaseFeatureValuesServer;
import us.kbase.kbasefeaturevalues.LabeledCluster;
import us.kbase.kbasefeaturevalues.MatrixDescriptor;
import us.kbase.kbasefeaturevalues.MatrixStat;
import us.kbase.kbasefeaturevalues.ReconnectMatrixToGenomeParams;
import us.kbase.kbasefeaturevalues.ServiceStatus;
import us.kbase.kbasefeaturevalues.SubmatrixStat;
import us.kbase.kbasefeaturevalues.transform.FeatureClustersDownloader;
import us.kbase.kbasefeaturevalues.transform.ExpressionUploader;
import us.kbase.userandjobstate.UserAndJobStateClient;
import us.kbase.workspace.CreateWorkspaceParams;
import us.kbase.workspace.ObjectData;
import us.kbase.workspace.ObjectIdentity;
import us.kbase.workspace.ObjectSaveData;
import us.kbase.workspace.SaveObjectsParams;
import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspace.WorkspaceIdentity;

@SuppressWarnings("unchecked")
public class AweIntegrationTest {
    private static AuthToken token = null;
    private static KBaseFeatureValuesClient client = null;
    private static File workDir = null;
    private static File mongoDir = null;
    //private static File shockDir = null;
    private static File aweServerDir = null;
    private static File aweClientDir = null;
    private static File fvServiceDir = null;
    private static Server fvService = null;
    private static String testWsName = null;
    
    private static int mongoInitWaitSeconds = 240;
    private static int aweServerInitWaitSeconds = 60;
    private static int fvJobWaitSeconds = 60;

    public static final String commonExpressionObjectName = "Desulfovibrio_vulgaris_Hildenborough.expression";

    
    @BeforeClass
    public static void beforeClass() throws Exception {
        workDir = prepareWorkDir("integration");
        File scriptFile = new File(workDir, "check_deps.sh");
        writeFileLines(Arrays.asList(
                "#!/bin/bash",
                "bash " + new File("./deps/integration_tests.sh").getCanonicalPath()
                ), scriptFile);
        ProcessHelper.cmd("bash", scriptFile.getCanonicalPath()).exec(workDir);
        //
        mongoDir = new File(workDir, "mongo");
        //shockDir = new File(workDir, "shock");
        aweServerDir = new File(workDir, "awe_server");
        aweClientDir = new File(workDir, "awe_client");
        fvServiceDir = new File(workDir, "fv_service");
        File binDir = new File("bin");
        int mongoPort = startupMongo(System.getProperty("test.mongod.path"), mongoDir);
        //int shockPort = startupShock(System.getProperty("shock.path"), shockDir, mongoPort);
        File aweBinDir = new File("./test/deps/bin").getCanonicalFile();
        int awePort = startupAweServer(findAweBinary(aweBinDir, "awe-server"), aweServerDir, mongoPort);
        fvService = startupFVService(fvServiceDir, binDir, awePort);
        int jobServicePort = fvService.getConnectors()[0].getLocalPort();
        startupAweClient(findAweBinary(aweBinDir, "awe-client"), aweClientDir, awePort, binDir);
        token = getToken();
        client = new KBaseFeatureValuesClient(new URL("http://localhost:" + jobServicePort), token);
        client.setIsInsecureHttpConnectionAllowed(true);
        String machineName = java.net.InetAddress.getLocalHost().getHostName();
        machineName = machineName == null ? "nowhere" : machineName.toLowerCase().replaceAll("[^\\dA-Za-z_]|\\s", "_");
        long suf = System.currentTimeMillis();
        WorkspaceClient wscl = getWsClient();
        Exception error = null;
        for (int i = 0; i < 5; i++) {
            testWsName = "test_feature_values_" + machineName + "_" + suf;
            try {
                wscl.createWorkspace(new CreateWorkspaceParams().withWorkspace(testWsName));
                error = null;
                break;
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                error = ex;
            }
        }
        if (error != null)
            throw error;
        //////////////////////////// Prepare common data //////////////////////////////
        String contigsetObjName = "Desulfovibrio_vulgaris_Hildenborough.contigset";
        String genomeObjName = "Desulfovibrio_vulgaris_Hildenborough.genome";
        File inputDir = new File("test/data/upload1");
        File inputFile = new File(inputDir, "Desulfovibrio_vulgaris_Hildenborough_microarray_log_level_data.txt");
        Map<String, Object> contigsetData = new LinkedHashMap<String, Object>();
        contigsetData.put("contigs", new ArrayList<Object>());
        contigsetData.put("id", "1945.contigset");
        contigsetData.put("md5", "md5");
        contigsetData.put("name", "1945");
        contigsetData.put("source", "User uploaded data");
        contigsetData.put("source_id", "noid");
        contigsetData.put("type", "Organism");
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(contigsetObjName).withType("KBaseGenomes.ContigSet")
                .withData(new UObject(contigsetData)))));
        Map<String, Object> genomeData = UObject.getMapper().readValue(new File(inputDir,
                "Desulfovibrio_vulgaris_Hildenborough_reduced_genome.json"), Map.class);
        genomeData.put("contigset_ref", testWsName + "/" + contigsetObjName);
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(genomeObjName).withType("KBaseGenomes.Genome")
                .withData(new UObject(genomeData)))));
        ExpressionMatrix data = ExpressionUploader.parse(getWsUrl(), testWsName, inputFile, "MO", 
                genomeObjName, true, null, null, token);
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(commonExpressionObjectName)
                .withType("KBaseFeatureValues.ExpressionMatrix").withData(new UObject(data)))));
    }
    
    private static String findAweBinary(File dir, String program) throws Exception {
        if (new File(dir, program).exists())
            return new File(dir, program).getAbsolutePath();
        return program;
    }
    
    @AfterClass
    public static void afterClass() throws Exception {
        if (fvService != null) {
            try {
                fvService.stop();
                System.out.println(fvServiceDir.getName() + " was stopped");
            } catch (Exception ignore) {}
        }
        killPid(aweClientDir);
        killPid(aweServerDir);
        //killPid(shockDir);
        killPid(mongoDir);
        try {
            if (testWsName != null) {
                getWsClient().deleteWorkspace(new WorkspaceIdentity().withWorkspace(testWsName));
                //System.out.println("Test workspace " + testWsName + " was deleted");
            }
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Test
    public void testServiceStatus() throws Exception {
        ServiceStatus status = client.status();
        Assert.assertEquals(KBaseFeatureValuesServer.SERVICE_VERSION, status.getVersion());
        Assert.assertEquals("OK", status.getStatus());
        assertNotEmpty(status.getStartupTime());
        assertNotEmpty(status.getGiturl());
        assertNotEmpty(status.getBranch());
        assertNotEmpty(status.getCommit());
        assertNotEmpty(status.getDeploymentCfgPath());
        Assert.assertTrue(status.getSafeConfiguration().size() > 0);
    }
    
    private static void assertNotEmpty(String text) throws Exception {
        Assert.assertNotNull(text);
        Assert.assertTrue(text.trim().length() > 0);
    }
    
    @Test
    public void testMainPipeline() throws Exception {
        WorkspaceClient wscl = getWsClient();
        String exprObjName = "expression1";
        String estimObjName = "estimate1";
        String estimNewObjName = "estimate2";
        String clustObj1Name = "clusters1";
        String clustObj2Name = "clusters2";
        String clustObj3Name = "clusters3";
        ExpressionMatrix data = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                .withData(getSampleMatrix());
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(exprObjName).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(data)))));
        /////////////// estimate K /////////////////
        String jobId1 = client.estimateK(new EstimateKParams().withInputMatrix(testWsName + "/" + 
                exprObjName).withOutWorkspace(testWsName).withOutEstimateResult(estimObjName));
        waitForJob(jobId1);
        ObjectData res1 = wscl.getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(estimObjName))).get(0);
        EstimateKResult estKRes = res1.getData().asClassInstance(EstimateKResult.class);
        long k = estKRes.getBestK();
        Assert.assertEquals(3, k);
        Assert.assertEquals(5, estKRes.getEstimateClusterSizes().size());
        for (int i = 0; i < estKRes.getEstimateClusterSizes().size(); i++) {
            Tuple2 <Long, Double> item = estKRes.getEstimateClusterSizes().get(i);
            Assert.assertEquals(2L + i, (long)item.getE1());
            Assert.assertTrue((double)item.getE2() > 0);
        }
        String jobId1new = client.estimateKNew(new EstimateKParamsNew().withInputMatrix(testWsName + "/" + 
                exprObjName).withRandomSeed(123L).withOutWorkspace(testWsName).withOutEstimateResult(estimNewObjName));
        waitForJob(jobId1new);
        ObjectData res1new = wscl.getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(estimNewObjName))).get(0);
        EstimateKResult estKResNew = res1new.getData().asClassInstance(EstimateKResult.class);
        long kNew = estKResNew.getBestK();
        Assert.assertEquals(k, kNew);
        //System.out.println("Cluster count qualities: " + estKResNew.getEstimateClusterSizes());
        Assert.assertEquals(estKRes.getEstimateClusterSizes().size(), estKResNew.getEstimateClusterSizes().size());
        for (int i = 0; i < estKResNew.getEstimateClusterSizes().size(); i++) {
            Tuple2<Long, Double> entry = estKRes.getEstimateClusterSizes().get(i);
            Tuple2<Long, Double> entryNew = estKResNew.getEstimateClusterSizes().get(i);
            Assert.assertEquals((long)entry.getE1(), (long)entryNew.getE1());
            Assert.assertEquals((double)entry.getE2(), (double)entryNew.getE2(), 1e-10);
        }
        /////////////// K-means /////////////////
        String jobId2 = client.clusterKMeans(new ClusterKMeansParams().withInputData(testWsName + "/" + 
                exprObjName).withK(k).withOutWorkspace(testWsName).withOutClustersetId(clustObj1Name));
        waitForJob(jobId2);
        ObjectData res2 = wscl.getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObj1Name))).get(0);
        FeatureClusters clSet2 = res2.getData().asClassInstance(FeatureClusters.class);
        Assert.assertEquals(2, clSet2.getFeatureClusters().get(0).getIdToPos().size());
        Assert.assertEquals(2, clSet2.getFeatureClusters().get(1).getIdToPos().size());
        Assert.assertEquals(3, clSet2.getFeatureClusters().get(2).getIdToPos().size());
        /////////////// Hierarchikal /////////////////
        String jobId3 = client.clusterHierarchical(new ClusterHierarchicalParams().withInputData(testWsName + "/" + 
                exprObjName).withFeatureHeightCutoff(0.5).withOutWorkspace(testWsName).withOutClustersetId(clustObj2Name));
        waitForJob(jobId3);
        ObjectData res3 = wscl.getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObj2Name))).get(0);
        FeatureClusters clSet3 = res3.getData().asClassInstance(FeatureClusters.class);
        Assert.assertEquals(5, clSet3.getFeatureClusters().get(0).getIdToPos().size());
        Assert.assertEquals(2, clSet3.getFeatureClusters().get(1).getIdToPos().size());
        Assert.assertTrue(clSet3.getFeatureDendrogram().startsWith("("));
        Assert.assertTrue(clSet3.getFeatureDendrogram().endsWith(");"));
        /////////////// From dendrogram /////////////////
        String jobId4 = client.clustersFromDendrogram(new ClustersFromDendrogramParams().withInputData(testWsName + "/" + 
                clustObj2Name).withFeatureHeightCutoff(0.2).withOutWorkspace(testWsName).withOutClustersetId(clustObj3Name));
        waitForJob(jobId4);
        ObjectData res4 = wscl.getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObj3Name))).get(0);
        FeatureClusters clSet4 = res4.getData().asClassInstance(FeatureClusters.class);
        Assert.assertEquals(2, clSet4.getFeatureClusters().get(0).getIdToPos().size());
        Assert.assertEquals(2, clSet4.getFeatureClusters().get(1).getIdToPos().size());
        Assert.assertEquals(3, clSet4.getFeatureClusters().get(2).getIdToPos().size());
        /////////////// Clusters download ///////////////
        File tsvTempFile = new File(fvServiceDir, "clusters.tsv");
        FeatureClustersDownloader.generate(getWsUrl(), testWsName, clustObj1Name, 1, "TSV", token,
                new PrintWriter(tsvTempFile));
        List<String> lines = readFileLines(tsvTempFile);
        Assert.assertEquals(7, lines.size());
        Set<String> clusterCodes = new TreeSet<String>();
        for (String l : lines) {
            String[] parts = l.split(Pattern.quote("\t"));
            Assert.assertEquals(2, parts.length);
            clusterCodes.add(parts[1]);
        }
        Assert.assertEquals("[0, 1, 2]", clusterCodes.toString());
    }
    
    @Test
    public void testPyScikitKMeans() throws Exception {
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("mac"))
            return;
        String exprObjName = "py_expression1";
        String clustObj1Name = "py_clusters1";
        ExpressionMatrix data = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                .withData(getSampleMatrix());
        WorkspaceClient wscl = getWsClient();
        wscl.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(exprObjName).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(data)))));
        String jobId = client.clusterKMeans(new ClusterKMeansParams().withInputData(testWsName + "/" + 
                exprObjName).withK(3L).withOutWorkspace(testWsName).withOutClustersetId(clustObj1Name));
        waitForJob(jobId);
        ObjectData res = wscl.getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObj1Name))).get(0);
        FeatureClusters clSet = res.getData().asClassInstance(FeatureClusters.class);
        System.out.println("Python Scikit K-means: " + clSet.getFeatureClusters());
    }
    
    @Test
    public void testCorrectMatrix() throws Exception {
        String sourceMatrixId = "notcorrected_matrix.1";
        ExpressionMatrix data = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                .withData(getSampleMatrix());
        data.getData().getValues().get(0).set(0, null);
        Assert.assertEquals(1, getNullCount(data.getData()));
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(sourceMatrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(data)))));
        String targetMatrixId = "corrected_matrix.1";
        String jobId = client.correctMatrix(new CorrectMatrixParams().withInputData(
                testWsName + "/" + sourceMatrixId).withOutWorkspace(testWsName)
                .withOutMatrixId(targetMatrixId).withTransformType("missing"));
        waitForJob(jobId);
        ExpressionMatrix matrix = getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(targetMatrixId)))
                .get(0).getData().asClassInstance(ExpressionMatrix.class);
        Assert.assertEquals(0, getNullCount(matrix.getData()));
        Assert.assertEquals(0.325, (double)matrix.getData().getValues().get(0).get(0), 1e-10);
    }
    
    @Test
    public void testReconnectToGenome() throws Exception {
        String genomeObjName = "Desulfovibrio_vulgaris_Hildenborough.genome";
        File inputDir = new File("test/data/upload1");
        File inputFile = new File(inputDir, "Desulfovibrio_vulgaris_Hildenborough_microarray_log_level_data.txt");
        ExpressionMatrix data = ExpressionUploader.parse(null, null, inputFile, "MO", 
                null, true, null, null, null);
        String matrixId = "connected_matrix.1";
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(matrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(data)))));
        String jobId = client.reconnectMatrixToGenome(new ReconnectMatrixToGenomeParams().withInputData(
                testWsName + "/" + matrixId).withOutWorkspace(testWsName).withGenomeRef(
                        testWsName + "/" + genomeObjName));
        waitForJob(jobId);
        ExpressionMatrix matrix = getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(matrixId)))
                .get(0).getData().asClassInstance(ExpressionMatrix.class);
        Assert.assertEquals(2666, matrix.getFeatureMapping().size());
    }
    
    @Test
    public void testBuildRowDescriptors() throws Exception {
        ExpressionMatrix data = UObject.getMapper().readValue(new File("test/data/upload7/poplar_roots_exp_matrix_5000.json"), ExpressionMatrix.class);
        String matrixId = "row_descr_matrix.1";
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(matrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(data)))));
        try {
            MatrixStat stat = client.getMatrixStat(new GetMatrixStatParams().withInputData(testWsName + "/" + matrixId));
            Assert.assertEquals(4999, stat.getRowStats().size());
        } catch (ServerException ex) {
            System.err.println(ex.getData());
            throw ex;
        }
    }
    
    private static int getNullCount(FloatMatrix2D matrix) {
        int ret = 0;
        for (List<Double> row : matrix.getValues())
            for (Double value : row)
                if (value == null)
                    ret++;
        return ret;
    }
    
    @Test
    public void testDataAPI() throws Exception {
        MatrixDescriptor md1 = client.getMatrixDescriptor(new GetMatrixDescriptorParams().withInputData(
                testWsName + "/" + commonExpressionObjectName));
        Assert.assertEquals(2680L, (long)md1.getRowsCount());
    }
    
    @Test
    public void testBuildFeatureSet() throws Exception {
        String genomeObjName = "Desulfovibrio_vulgaris_Hildenborough.genome";
        String outFeatureSetObj1 = "featureset.1";
        Map<String, List<String>> elements1 = buildFeatureSetForTesting(genomeObjName, 
                outFeatureSetObj1, " DVUA0001 \nDVUA0075, DVUA0112", null);
        Assert.assertEquals(3, elements1.size());
        Assert.assertEquals(3, buildFeatureSetForTesting(genomeObjName, 
                "featureset.1b", " DVUA0001 \nDVUA0075", "DVUA0112").size());
        Assert.assertEquals(3, buildFeatureSetForTesting(genomeObjName, 
                "featureset.1c", " DVUA0001 \n", "DVUA0075, DVUA0112").size());
        Assert.assertEquals(3, buildFeatureSetForTesting(genomeObjName, 
                "featureset.1d", "", " DVUA0001 \nDVUA0075, DVUA0112").size());
        Assert.assertEquals(3, buildFeatureSetForTesting(genomeObjName, 
                "featureset.1e", null, " DVUA0001 \nDVUA0075, DVUA0112").size());
        String outFeatureSetObj2 = "featureset.2";
        try {
            String jobId2 = client.buildFeatureSet(new BuildFeatureSetParams().withDescription("Testing...")
                    .withBaseFeatureSet(testWsName + "/" + outFeatureSetObj1)
                    .withFeatureIds("DVUA1000").withGenome(testWsName + "/" + genomeObjName)
                    .withOutWorkspace(testWsName).withOutputFeatureSet(outFeatureSetObj2));
            waitForJob(jobId2);
            Assert.fail("Method should fail");
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex.getMessage().contains("Some features are not found: "));
        }
        String jobId3 = client.buildFeatureSet(new BuildFeatureSetParams().withDescription("Testing...")
                .withBaseFeatureSet(testWsName + "/" + outFeatureSetObj1)
                .withFeatureIds("DVU1000").withGenome(testWsName + "/" + genomeObjName)
                .withOutWorkspace(testWsName).withOutputFeatureSet(outFeatureSetObj2));
        waitForJob(jobId3);
        Map<String, Object> fs2 = getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(outFeatureSetObj2)))
                .get(0).getData().asClassInstance(Map.class);
        Map<String, List<String>> elements2 = (Map<String, List<String>>)fs2.get("elements");
        Assert.assertEquals(4, elements2.size());
    }

    public Map<String, List<String>> buildFeatureSetForTesting(
            String genomeObjName, String outFeatureSetObj1, String featureIds,
            String featureIdsCustom) throws Exception {
        String jobId1 = client.buildFeatureSet(new BuildFeatureSetParams().withDescription("Testing...")
                .withFeatureIds(featureIds).withFeatureIdsCustom(featureIdsCustom)
                .withGenome(testWsName + "/" + genomeObjName)
                .withOutWorkspace(testWsName).withOutputFeatureSet(outFeatureSetObj1));
        waitForJob(jobId1);
        Map<String, Object> fs1 = getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(outFeatureSetObj1)))
                .get(0).getData().asClassInstance(Map.class);
        Map<String, List<String>> elements1 = (Map<String, List<String>>)fs1.get("elements");
        return elements1;
    }
    
    @Test
    public void testSubMatrixStat() throws Exception {
        File dir = new File("test/data/upload8");
        GZIPInputStream is = new GZIPInputStream(new FileInputStream(new File(dir, "Rhodobacter.contigset.json.gz")));
        Map<String, Object> contigsetData = UObject.getMapper().readValue(is, Map.class);
        is.close();
        String contigsetObjName = "submatrix_contigset.1";
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(contigsetObjName).withType("KBaseGenomes.ContigSet")
                .withData(new UObject(contigsetData)))));
        is = new GZIPInputStream(new FileInputStream(new File(dir, "Rhodobacter.genome.json.gz")));
        Map<String, Object> genomeData = UObject.getMapper().readValue(is, Map.class);
        is.close();
        String genomeObjName = "submatrix_contigset.1";
        genomeData.put("contigset_ref", testWsName + "/" + contigsetObjName);
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(genomeObjName).withType("KBaseGenomes.Genome")
                .withData(new UObject(genomeData)))));
        ExpressionMatrix data = UObject.getMapper().readValue(new File(dir, "NewFakeData2.3.json"), ExpressionMatrix.class);
        data.setGenomeRef(testWsName + "/" + genomeObjName);
        String matrixId = "submatrix_matrix.1";
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(matrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(data)))));
        try {
            SubmatrixStat stat = client.getSubmatrixStat(new GetSubmatrixStatParams().withInputData(testWsName + "/" + matrixId)
                    .withRowIds(Arrays.asList("RSP_0049", "RSP_1584", "RSP_1588")).withFlRowPairwiseCorrelation(1L)
                    .withFlRowSetStats(1L));
            Assert.assertEquals(3, stat.getRowPairwiseCorrelation().getComparisonValues().size());
            Assert.assertEquals(3, stat.getRowPairwiseCorrelation().getComparisonValues().get(0).size());
        } catch (ServerException ex) {
            System.err.println(ex.getData());
            throw ex;
        }
    }
    
    @Test
    public void testHierarchicalClustering() throws Exception {
        String matrixId = "hierarchical_matrix.1";
        String clustObjName = "hierarchical_clusters.1";
        File inputFile = new File("test/data/upload6/E_coli_v4_Build_6_subdata.tsv");
        ExpressionMatrix data = ExpressionUploader.parse(null, null, inputFile, "Simple", 
                null, true, null, null, null);
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(matrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                .withData(new UObject(data)))));
        String jobId1 = client.clusterHierarchical(new ClusterHierarchicalParams().withInputData(testWsName + "/" + 
                matrixId).withFeatureHeightCutoff(0.2).withAlgorithm("flashClust").withMaxItems(1000L)
                .withOutWorkspace(testWsName).withOutClustersetId(clustObjName));
        waitForJob(jobId1);
        ObjectData res1 = getWsClient().getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObjName))).get(0);
        FeatureClusters clSet1 = res1.getData().asClassInstance(FeatureClusters.class);
        int nullCount = 0;
        for (LabeledCluster lc : clSet1.getFeatureClusters()) {
            if (lc.getMeancor() == null || lc.getMsec() == null)
                nullCount++;
        }
        Assert.assertEquals(10, nullCount);
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
                .withRowIds(Arrays.asList("g1", "g2", "g3", "g4", "g5", "g6", "g7"))
                .withColIds(Arrays.asList("c1", "c2", "c3"));
    }
    
    private static WorkspaceClient getWsClient() throws UnauthorizedException, IOException,
            MalformedURLException {
        WorkspaceClient wscl = new WorkspaceClient(new URL(getWsUrl()), token);
        wscl.setAuthAllowedForHttp(true);
        return wscl;
    }

    private static String getWsUrl() {
        return systemGetProperty("test." + KBaseFeatureValuesServer.CONFIG_PARAM_WS_URL);
    }

    private static String getUjsUrl() {
        return systemGetProperty("test." + KBaseFeatureValuesServer.CONFIG_PARAM_UJS_URL);
    }
    
    private void waitForJob(String jobId) throws Exception {
        UserAndJobStateClient jscl = new UserAndJobStateClient(new URL(getUjsUrl()), token);
        jscl.setAllSSLCertificatesTrusted(true);
        jscl.setIsInsecureHttpConnectionAllowed(true);
        for (int i = 0; i < fvJobWaitSeconds; i++) {
            Tuple7<String, String, String, Long, String, Long, Long> data = jscl.getJobStatus(jobId);
            //System.out.println("Job status: " + data);
            Long complete = data.getE6();
            Long wasError = data.getE7();
            if (complete != null && complete == 1L) {
                if (wasError == 0L) {
                    return;
                } else {
                    String error = jscl.getDetailedError(jobId);
                    throw new IllegalStateException(error);
                }
            }
            Thread.sleep(1000);
        }
        throw new IllegalStateException("Job wasn't finished in " + fvJobWaitSeconds + " seconds");
    }

    private static AuthToken getToken() throws AuthException, IOException {
        String user = systemGetProperty("test.user");
        String password = systemGetProperty("test.password");
        AuthToken token = AuthService.login(user, password).getToken();
        return token;
    }
    
    private static int startupMongo(String mongodExePath, File dir) throws Exception {
        if (mongodExePath == null)
            mongodExePath = "mongod";
        if (!dir.exists())
            dir.mkdirs();
        File dataDir = new File(dir, "data");
        dataDir.mkdir();
        File logFile = new File(dir, "mongodb.log");
        int port = findFreePort();
        File configFile = new File(dir, "mongod.conf");
        writeFileLines(Arrays.asList(
                "dbpath=" + dataDir.getAbsolutePath(),
                "logpath=" + logFile.getAbsolutePath(),
                "logappend=true",
                "port=" + port,
                "bind_ip=127.0.0.1"
                ), configFile);
        File scriptFile = new File(dir, "start_mongo.sh");
        writeFileLines(Arrays.asList(
                "#!/bin/bash",
                "cd " + dir.getAbsolutePath(),
                mongodExePath + " --config " + configFile.getAbsolutePath() + " >out.txt 2>err.txt & pid=$!",
                "echo $pid > pid.txt"
                ), scriptFile);
        ProcessHelper.cmd("bash", scriptFile.getCanonicalPath()).exec(dir);
        boolean ready = false;
        for (int n = 0; n < mongoInitWaitSeconds; n++) {
            Thread.sleep(1000);
            if (logFile.exists()) {
                if (grep(readFileLines(logFile), "waiting for connections on port " + port).size() > 0) {
                    ready = true;
                    break;
                }
            }
        }
        if (!ready) {
            if (logFile.exists())
                for (String l : readFileLines(logFile))
                    System.err.println("MongoDB log: " + l);
            throw new IllegalStateException("MongoDB couldn't startup in " + mongoInitWaitSeconds + " seconds");
        }
        System.out.println(dir.getName() + " was started up");
        return port;
    }

    /*private static int startupShock(String shockExePath, File dir, int mongoPort) throws Exception {
        if (shockExePath == null)
            shockExePath = "shock-server";
        if (!dir.exists())
            dir.mkdirs();
        File dataDir = new File(dir, "data");
        dataDir.mkdir();
        File logsDir = new File(dir, "logs");
        logsDir.mkdir();
        File siteDir = new File(dir, "site");
        siteDir.mkdir();
        int port = findFreePort();
        File configFile = new File(dir, "shock.cfg");
        writeFileLines(Arrays.asList(
                "[Address]",
                "api-ip=0.0.0.0",
                "api-port=" + port,
                "[Admin]",
                "email=shock-admin@kbase.us",
                "[Anonymous]",
                "read=true",
                "write=true",
                "create-user=false",
                "[Auth]",
                "globus_token_url=https://nexus.api.globusonline.org/goauth/token?grant_type=client_credentials",
                "globus_profile_url=https://nexus.api.globusonline.org/users",
                "[Mongodb]",
                "hosts=localhost:" + mongoPort,
                "database=ShockDBtest",
                "[Mongodb-Node-Indices]",
                "id=unique:true",
                "[Paths]",
                "data=" + dataDir.getAbsolutePath(),
                "logs=" + logsDir.getAbsolutePath(),
                "site=" + siteDir.getAbsolutePath()
                ), configFile);
        File scriptFile = new File(dir, "start_shock.sh");
        writeFileLines(Arrays.asList(
                "#!/bin/bash",
                "cd " + dir.getAbsolutePath(),
                shockExePath + " --conf " + configFile.getAbsolutePath() + " >out.txt 2>err.txt & pid=$!",
                "echo $pid > pid.txt"
                ), scriptFile);
        ProcessHelper.cmd("bash", scriptFile.getCanonicalPath()).exec(dir);
        Exception err = null;
        int waitSec = 30;
        for (int n = 0; n < waitSec; n++) {
            Thread.sleep(1000);
            try {
                BasicShockClient client = new BasicShockClient(new URL("http://localhost:" + port));
                String jsonData = "{\"key\":\"value\"}";
                InputStream is = new ByteArrayInputStream(jsonData.getBytes());
                ShockNode node = client.addNode(new TreeMap<String, Object>(), is, "test.json", "json");
                is.close();
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                client.getFile(node.getId(), os);
                os.close();
                String retJson = new String(os.toByteArray());
                if (jsonData.equals(retJson)) {
                    err = null;
                    break;
                } else {
                    err = new Exception("Shock response doesn't match expected data: " + retJson);
                }
            } catch (Exception ex) {
                err = ex;
            }
        }
        if (err != null) {
            File errorFile = new File(logsDir, "error.log");
            if (errorFile.exists())
                for (String l : readFileLines(errorFile))
                    System.err.println("Shock error: " + l);
            throw new IllegalStateException("Shock couldn't startup in " + waitSec + " seconds (" + err.getMessage() + ")", err);
        }
        System.out.println(dir.getName() + " was started up");
        return port;
    }*/

    private static int startupAweServer(String aweServerExePath, File dir, int mongoPort) throws Exception {
        if (aweServerExePath == null) {
            aweServerExePath = "awe-server";
        }
        if (!dir.exists())
            dir.mkdirs();
        File dataDir = new File(dir, "data");
        dataDir.mkdir();
        File logsDir = new File(dir, "logs");
        logsDir.mkdir();
        File siteDir = new File(dir, "site");
        siteDir.mkdir();
        File awfDir = new File(dir, "awfs");
        awfDir.mkdir();
        int port = findFreePort();
        File configFile = new File(dir, "awe.cfg");
        writeFileLines(Arrays.asList(
                "[Admin]",
                "email=shock-admin@kbase.us",
                "users=",
                "[Anonymous]",
                "read=true",
                "write=true",
                "delete=true",
                "cg_read=false",
                "cg_write=false",
                "cg_delete=false",
                "[Args]",
                "debuglevel=0",
                "[Auth]",
                "globus_token_url=https://nexus.api.globusonline.org/goauth/token?grant_type=client_credentials",
                "globus_profile_url=https://nexus.api.globusonline.org/users",
                "client_auth_required=false",
                "[Directories]",
                "data=" + dataDir.getAbsolutePath(),
                "logs=" + logsDir.getAbsolutePath(),
                "site=" + siteDir.getAbsolutePath(),
                "awf=" + awfDir.getAbsolutePath(),
                "[Mongodb]",
                "hosts=localhost:" + mongoPort,
                "database=AWEDB",
                "[Mongodb-Node-Indices]",
                "id=unique:true",
                "[Ports]",
                "site-port=" + findFreePort(),
                "api-port=" + port
                ), configFile);
        File scriptFile = new File(dir, "start_awe_server.sh");
        writeFileLines(Arrays.asList(
                "#!/bin/bash",
                "cd " + dir.getAbsolutePath(),
                aweServerExePath + " --conf " + configFile.getAbsolutePath() + " >out.txt 2>err.txt & pid=$!",
                "echo $pid > pid.txt"
                ), scriptFile);
        ProcessHelper.cmd("bash", scriptFile.getCanonicalPath()).exec(dir);
        Exception err = null;
        for (int n = 0; n < aweServerInitWaitSeconds; n++) {
            Thread.sleep(1000);
            try {
                InputStream is = new URL("http://localhost:" + port + "/job/").openStream();
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> ret = mapper.readValue(is, Map.class);
                if (ret.containsKey("limit") && ret.containsKey("total_count")) {
                    err = null;
                    break;
                } else {
                    err = new Exception("AWE server response doesn't match expected data: " + 
                            mapper.writeValueAsString(ret));
                }
            } catch (Exception ex) {
                err = ex;
            }
        }
        if (err != null) {
            File errorFile = new File(new File(logsDir, "server"), "error.log");
            if (errorFile.exists())
                for (String l : readFileLines(errorFile))
                    System.err.println("AWE server error: " + l);
            throw new IllegalStateException("AWE server couldn't startup in " + aweServerInitWaitSeconds + 
                    " seconds (" + err.getMessage() + ")", err);
        }
        System.out.println(dir.getName() + " was started up");
        return port;
    }

    private static int startupAweClient(String aweClientExePath, File dir, int aweServerPort, 
            File binDir) throws Exception {
        if (aweClientExePath == null) {
            aweClientExePath = "awe-client";
        }
        if (!dir.exists())
            dir.mkdirs();
        File dataDir = new File(dir, "data");
        dataDir.mkdir();
        File logsDir = new File(dir, "logs");
        logsDir.mkdir();
        File workDir = new File(dir, "work");
        workDir.mkdir();
        int port = findFreePort();
        File configFile = new File(dir, "awec.cfg");
        writeFileLines(Arrays.asList(
                "[Directories]",
                "data=" + dataDir.getAbsolutePath(),
                "logs=" + logsDir.getAbsolutePath(),
                "[Args]",
                "debuglevel=0",
                "[Client]",
                "workpath=" + workDir.getAbsolutePath(),
                "supported_apps=" + KBaseFeatureValuesServer.AWE_CLIENT_SCRIPT_NAME,
                "serverurl=http://localhost:" + aweServerPort + "/",
                "group=kbase",
                "name=kbase-client",
                "auto_clean_dir=false",
                "worker_overlap=false",
                "print_app_msg=true",
                "clientgroup_token=",
                "pre_work_script=",
                "pre_work_script_args="
                ), configFile);
        File scriptFile = new File(dir, "start_awe_client.sh");
        writeFileLines(Arrays.asList(
                "#!/bin/bash",
                "cd " + dir.getAbsolutePath(),
                "export PATH=" + binDir.getAbsolutePath() + ":$PATH",
                aweClientExePath + " --conf " + configFile.getAbsolutePath() + " >out.txt 2>err.txt & pid=$!",
                "echo $pid > pid.txt"
                ), scriptFile);
        ProcessHelper.cmd("bash", scriptFile.getCanonicalPath()).exec(dir);
        Exception err = null;
        int waitSec = 30;
        for (int n = 0; n < waitSec; n++) {
            Thread.sleep(1000);
            try {
                InputStream is = new URL("http://localhost:" + aweServerPort + "/client/").openStream();
                ObjectMapper mapper = new ObjectMapper();
                Map<String, Object> ret = mapper.readValue(is, Map.class);
                if (ret.containsKey("data") && 
                        ((List<Object>)ret.get("data")).size() > 0) {
                    err = null;
                    break;
                } else {
                    err = new Exception("AWE client response doesn't match expected data: " + 
                            mapper.writeValueAsString(ret));
                }
            } catch (Exception ex) {
                err = ex;
            }
        }
        if (err != null) {
            File errorFile = new File(new File(logsDir, "client"), "error.log");
            if (errorFile.exists())
                for (String l : readFileLines(errorFile))
                    System.err.println("AWE client error: " + l);
            throw new IllegalStateException("AWE client couldn't startup in " + waitSec + 
                    " seconds (" + err.getMessage() + ")", err);
        }
        System.out.println(dir.getName() + " was started up");
        return port;
    }

    private static Server startupFVService(File dir, File binDir, int awePort) throws Exception {
        Log.setLog(new Logger() {
            @Override
            public void warn(String arg0, Object arg1, Object arg2) {}
            @Override
            public void warn(String arg0, Throwable arg1) {}
            @Override
            public void warn(String arg0) {}
            @Override
            public void setDebugEnabled(boolean arg0) {}
            @Override
            public boolean isDebugEnabled() {
                return false;
            }
            @Override
            public void info(String arg0, Object arg1, Object arg2) {}
            @Override
            public void info(String arg0) {}
            @Override
            public String getName() {
                return null;
            }
            @Override
            public Logger getLogger(String arg0) {
                return this;
            }
            @Override
            public void debug(String arg0, Object arg1, Object arg2) {}
            @Override
            public void debug(String arg0, Throwable arg1) {}
            @Override
            public void debug(String arg0) {}
        });
        if (!dir.exists())
            dir.mkdirs();
        File configFile = new File(dir, "deploy.cfg");
        int port = findFreePort();
        writeFileLines(Arrays.asList(
                "[" + KBaseFeatureValuesServer.SERVICE_NAME + "]",
                KBaseFeatureValuesServer.CONFIG_PARAM_SCRATCH + "=" + dir.getAbsolutePath(),
                KBaseFeatureValuesServer.CONFIG_PARAM_UJS_URL + "=" + getUjsUrl(),
                KBaseFeatureValuesServer.CONFIG_PARAM_SHOCK_URL + "=" + 
                        systemGetProperty("test." + KBaseFeatureValuesServer.CONFIG_PARAM_SHOCK_URL),
                KBaseFeatureValuesServer.CONFIG_PARAM_AWE_URL + "=http://localhost:" + awePort + "/",
                KBaseFeatureValuesServer.CONFIG_PARAM_CLIENT_BIN_DIR + "=" + binDir.getAbsolutePath(),
                KBaseFeatureValuesServer.CONFIG_PARAM_WS_URL + "=" + getWsUrl()
                ), configFile);
        /*File jobServiceCLI = new File(dir, KBaseFeatureValuesServer.AWE_CLIENT_SCRIPT_NAME);
        writeFileLines(Arrays.asList(
                "#!/bin/bash",
                "java -cp " + System.getProperty("java.class.path") + 
                    " us.kbase.kbasefeaturevalues.KBaseFeatureValuesScript $1"
                ), jobServiceCLI);
        ProcessHelper.cmd("chmod", "a+x", jobServiceCLI.getAbsolutePath()).exec(dir);*/
        System.setProperty("KB_DEPLOYMENT_CONFIG", configFile.getAbsolutePath());
        Server jettyServer = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        jettyServer.setHandler(context);
        context.addServlet(new ServletHolder(new KBaseFeatureValuesServer()),"/*");
        jettyServer.start();
        Exception err = null;
        JsonClientCaller caller = new JsonClientCaller(new URL("http://localhost:" + port + "/"));
        int waitSec = 30;
        for (int n = 0; n < waitSec; n++) {
            Thread.sleep(1000);
            try {
                caller.jsonrpcCall("Unknown", new ArrayList<String>(), null, false, false);
            } catch (ServerException ex) {
                if (ex.getMessage().contains("Can not find method [Unknown] in server class")) {
                    err = null;
                    break;
                } else {
                    err = ex;
                }
            } catch (Exception ex) {
                err = ex;
            }
        }
        if (err != null)
            throw new IllegalStateException("KBaseFeatureValues service couldn't startup in " + 
                    waitSec + " seconds (" + err.getMessage() + ")", err);
        System.out.println(dir.getName() + " was started up");
        return jettyServer;
    }

    private static void killPid(File dir) {
        if (dir == null)
            return;
        try {
            File pidFile = new File(dir, "pid.txt");
            if (pidFile.exists()) {
                String pid = readFileLines(pidFile).get(0).trim();
                ProcessHelper.cmd("kill", pid).exec(dir);
                System.out.println(dir.getName() + " was stopped");
            }
        } catch (Exception ignore) {}
    }
    
    private static void writeFileLines(List<String> lines, File targetFile) throws IOException {
        PrintWriter pw = new PrintWriter(targetFile);
        for (String l : lines)
            pw.println(l);
        pw.close();
    }

    private static List<String> readFileLines(File f) throws IOException {
        List<String> ret = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new FileReader(f));
        while (true) {
            String l = br.readLine();
            if (l == null)
                break;
            ret.add(l);
        }
        br.close();
        return ret;
    }

    private static List<String> grep(List<String> lines, String substring) {
        List<String> ret = new ArrayList<String>();
        for (String l : lines)
            if (l.contains(substring))
                ret.add(l);
        return ret;
    }
    
    private static int findFreePort() {
        try (ServerSocket socket = new ServerSocket(0)) {
            return socket.getLocalPort();
        } catch (IOException e) {}
        throw new IllegalStateException("Can not find available port in system");
    }
    
    private static String systemGetProperty(String name) {
        String ret = System.getProperty(name);
        if (ret == null)
            throw new IllegalStateException("Parameter " + name + " is not defined " +
            		"in test configuration");
        return ret;
    }
    
    private static File prepareWorkDir(String testName) throws IOException {
        File tempDir = new File(systemGetProperty("test.temp-dir")).getCanonicalFile();
        if (!tempDir.exists())
            tempDir.mkdirs();
        for (File dir : tempDir.listFiles()) {
            if (dir.isDirectory() && dir.getName().startsWith("test_" + testName + "_"))
                try {
                    deleteRecursively(dir);
                } catch (Exception e) {
                    System.out.println("Can not delete directory [" + dir.getName() + "]: " + e.getMessage());
                }
        }
        File workDir = new File(tempDir, "test_" + testName + "_" + System.currentTimeMillis());
        if (!workDir.exists())
            workDir.mkdir();
        return workDir;
    }
    
    private static void deleteRecursively(File fileOrDir) {
        if (fileOrDir.isDirectory() && !Files.isSymbolicLink(fileOrDir.toPath()))
            for (File f : fileOrDir.listFiles()) 
                deleteRecursively(f);
        fileOrDir.delete();
    }
}
