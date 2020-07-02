package us.kbase.kbasefeaturevalues.test;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.ini4j.Ini;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import datafileutil.DataFileUtilClient;
import datafileutil.FileToShockParams;

import us.kbase.kbasefeaturevalues.BuildFeatureSetParams;
import us.kbase.kbasefeaturevalues.ClusterHierarchicalParams;
import us.kbase.kbasefeaturevalues.ClusterKMeansParams;
import us.kbase.kbasefeaturevalues.ClustersFromDendrogramParams;
import us.kbase.kbasefeaturevalues.CorrectMatrixParams;
import us.kbase.kbasefeaturevalues.EstimateKParams;
import us.kbase.kbasefeaturevalues.EstimateKParamsNew;
import us.kbase.kbasefeaturevalues.EstimateKResult;
import us.kbase.kbasefeaturevalues.ExportClustersSifParams;
import us.kbase.kbasefeaturevalues.ExportClustersTsvParams;
import us.kbase.kbasefeaturevalues.ExportMatrixParams;
import us.kbase.kbasefeaturevalues.ExpressionMatrix;
import us.kbase.kbasefeaturevalues.FeatureClusters;
import us.kbase.kbasefeaturevalues.FloatMatrix2D;
import us.kbase.kbasefeaturevalues.GetMatrixDescriptorParams;
import us.kbase.kbasefeaturevalues.GetMatrixStatParams;
import us.kbase.kbasefeaturevalues.GetSubmatrixStatParams;
import us.kbase.kbasefeaturevalues.KBaseFeatureValuesImpl;
import us.kbase.kbasefeaturevalues.KBaseFeatureValuesServer;
import us.kbase.kbasefeaturevalues.LabeledCluster;
import us.kbase.kbasefeaturevalues.MatrixDescriptor;
import us.kbase.kbasefeaturevalues.MatrixStat;
import us.kbase.kbasefeaturevalues.ReconnectMatrixToGenomeParams;
import us.kbase.kbasefeaturevalues.SubmatrixStat;
import us.kbase.kbasefeaturevalues.TsvFileToMatrixParams;
import us.kbase.kbasefeaturevalues.transform.ExpressionUploader;
import us.kbase.kbasefeaturevalues.transform.FeatureClustersDownloader;
import us.kbase.auth.AuthConfig;
import us.kbase.auth.AuthToken;
import us.kbase.auth.ConfigurableAuthService;
import us.kbase.clusterservice.ClusterResults;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.RpcContext;
import us.kbase.common.service.ServerException;
import us.kbase.common.service.Tuple2;
import us.kbase.common.service.Tuple11;
import us.kbase.common.service.UObject;
import us.kbase.shock.client.BasicShockClient;
import us.kbase.shock.client.ShockNodeId;
import us.kbase.shock.client.exceptions.InvalidShockUrlException;
import us.kbase.shock.client.exceptions.ShockHttpException;
import us.kbase.workspace.CreateWorkspaceParams;
import us.kbase.workspace.ObjectData;
import us.kbase.workspace.ObjectIdentity;
import us.kbase.workspace.ObjectSaveData;
import us.kbase.workspace.ProvenanceAction;
import us.kbase.workspace.SaveObjectsParams;
import us.kbase.workspace.WorkspaceClient;
import us.kbase.workspace.WorkspaceIdentity;

public class KBaseFeatureValuesServerTest {
    private static AuthToken token = null;
    private static Map<String, String> config = null;
    private static WorkspaceClient wsClient = null;
    private static String wsUrl = null;
    private static String wsName = null;
    private static KBaseFeatureValuesServer impl = null;
    private static List<String> tempShockIdsToDelete = new ArrayList<>();

    private static final String commonGenomeObjectName = "Desulfovibrio_vulgaris_Hildenborough.genome";
    private static final String commonExpressionObjectName = "Desulfovibrio_vulgaris_Hildenborough.expression";

    @BeforeClass
    public static void init() throws Exception {
        // Config loading
        String configFilePath = System.getenv("KB_DEPLOYMENT_CONFIG");
        File deploy = new File(configFilePath);
        Ini ini = new Ini(deploy);
        config = ini.get("KBaseFeatureValues");
        // Token validation
        String authUrl = config.get("auth-service-url");
        String authUrlInsecure = config.get("auth-service-url-allow-insecure");
        ConfigurableAuthService authService = new ConfigurableAuthService(
                new AuthConfig().withKBaseAuthServerURL(new URL(authUrl))
                        .withAllowInsecureURLs("true".equals(authUrlInsecure)));
        token = authService.validateToken(System.getenv("KB_AUTH_TOKEN"));
        // Reading URLs from config
        wsUrl = config.get("ws.url");
        wsClient = new WorkspaceClient(new URL(wsUrl), token);
        wsClient.setIsInsecureHttpConnectionAllowed(true);
        // These lines are necessary because we don't want to start linux syslog bridge service
        JsonServerSyslog.setStaticUseSyslog(false);
        JsonServerSyslog.setStaticMlogFile(new File(config.get("scratch"), "test.log").getAbsolutePath());
        impl = new KBaseFeatureValuesServer();
        ////////////////////////////Prepare common data //////////////////////////////
        String testWsName = getWsName();
        String genomeObjName = commonGenomeObjectName;
        File inputDir = new File("test/data/upload1");
        File inputFile = new File(inputDir, "Desulfovibrio_vulgaris_Hildenborough_microarray_log_level_data.txt");
        @SuppressWarnings("unchecked")
        Map<String, Object> genomeData = UObject.getMapper().readValue(new File(inputDir,
                "Desulfovibrio_vulgaris_Hildenborough_reduced_genome.json"), Map.class);
        wsClient.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(genomeObjName).withType("KBaseGenomes.Genome")
                        .withData(new UObject(genomeData)))));
        ExpressionMatrix data = ExpressionUploader.parse(testWsName, inputFile, "MO",
                genomeObjName, true, null, null, token);
        wsClient.saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(commonExpressionObjectName)
                        .withType("KBaseFeatureValues.ExpressionMatrix").withData(new UObject(data)))));
    }

    private static String getWsName() throws Exception {
        if (wsName == null) {
            long suffix = System.currentTimeMillis();
            wsName = "test_KBaseFeatureValues_" + suffix;
            wsClient.createWorkspace(new CreateWorkspaceParams().withWorkspace(wsName));
        }
        return wsName;
    }

    private static RpcContext getContext() {
        return new RpcContext().withProvenance(Arrays.asList(new ProvenanceAction()
                .withService("KBaseFeatureValues").withMethod("please_never_use_it_in_production")
                .withMethodParams(new ArrayList<UObject>())));
    }

    @AfterClass
    public static void cleanup() throws Exception {
        if (wsName != null) {
            try {
                wsClient.deleteWorkspace(new WorkspaceIdentity().withWorkspace(wsName));
                System.out.println("Test workspace [" + wsName + "] was deleted");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if (tempShockIdsToDelete.size() > 0) {
            BasicShockClient shCl = createShockClient();
            for (String shockId : tempShockIdsToDelete) {
                try {
                    shCl.deleteNode(new ShockNodeId(shockId));
                    System.out.println("Shock node [" + shockId + "] was deleted");
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static BasicShockClient createShockClient() throws IOException,
            InvalidShockUrlException, ShockHttpException, MalformedURLException {
        return new BasicShockClient(new URL(config.get("shock.url")), token);
    }

    private static WorkspaceClient getWsClient() {
        return wsClient;
    }

    @Test
    public void testMainPipeline() throws Exception {
        String testWsName = getWsName();
        String exprObjName = "expression1";
        String estimObjName = "estimate1";
        String estimNewObjName = "estimate2";
        String clustObj1Name = "clusters1";
        String clustObj2Name = "clusters2";
        String clustObj3Name = "clusters3";
        ExpressionMatrix data = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                .withData(getSampleMatrix());
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(exprObjName).withType("KBaseFeatureValues.ExpressionMatrix")
                        .withData(new UObject(data)))));
        /////////////// estimate K /////////////////
        EstimateKParams ekp = new EstimateKParams().withInputMatrix(testWsName + "/" +
                exprObjName).withOutWorkspace(testWsName).withOutEstimateResult(estimObjName);
        EstimateKResult estKRes = impl.estimateK(ekp, token, getContext());

        //ObjectData res1 = getWsClient().getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
        //        .withName(estimObjName))).get(0);
        //EstimateKResult estKRes = res1.getData().asClassInstance(EstimateKResult.class);
        long k = estKRes.getBestK();
        Assert.assertNotNull("k exists ", k);
        Assert.assertNotNull("c size exists ", estKRes.getEstimateClusterSizes().size());
        Assert.assertEquals(3, k);
        Assert.assertEquals(5, estKRes.getEstimateClusterSizes().size());
        for (int i = 0; i < estKRes.getEstimateClusterSizes().size(); i++) {
            Tuple2<Long, Double> item = estKRes.getEstimateClusterSizes().get(i);
            Assert.assertEquals(2L + i, (long) item.getE1());
            Assert.assertTrue((double) item.getE2() > 0);
        }
        System.err.println("estKRes\n"+estKRes.toString());

        EstimateKResult estKResNew = impl.estimateKNew(new EstimateKParamsNew().withInputMatrix(testWsName + "/" +
                exprObjName).withRandomSeed(123L).withOutWorkspace(testWsName)
                .withOutEstimateResult(estimNewObjName), token, getContext());

        System.err.println("estKResNew\n"+estKResNew.toString());
        long kNew = estKResNew.getBestK();
        Assert.assertEquals(k, kNew);
        //System.out.println("Cluster count qualities: " + estKResNew.getEstimateClusterSizes());
        Assert.assertEquals(estKRes.getEstimateClusterSizes().size(), estKResNew.getEstimateClusterSizes().size());
        for (int i = 0; i < estKResNew.getEstimateClusterSizes().size(); i++) {
            Tuple2<Long, Double> entry = estKRes.getEstimateClusterSizes().get(i);
            Tuple2<Long, Double> entryNew = estKResNew.getEstimateClusterSizes().get(i);
            Assert.assertEquals((long) entry.getE1(), (long) entryNew.getE1());
            Assert.assertEquals((double) entry.getE2(), (double) entryNew.getE2(), 1e-10);
        }
        /////////////// K-means /////////////////
        String wsRefKM = impl.clusterKMeans(new ClusterKMeansParams().withInputData(testWsName + "/" +
                exprObjName).withK(k).withOutWorkspace(testWsName).withOutClustersetId(clustObj1Name),
                token, getContext());
        ObjectData res2 = getWsClient().getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObj1Name))).get(0);
        FeatureClusters clSet2 = res2.getData().asClassInstance(FeatureClusters.class);
        checkKMeansForSample(clSet2);

        //test that returned ref matched object id
        Tuple11<Long,String,String,String,Long,String,Long,String,String,Long,Map<String,String>> getInfoKM = res2.getInfo();
        Assert.assertEquals(getInfoKM.getE7()  + "/" + getInfoKM.getE1() + "/" +  getInfoKM.getE5(), wsRefKM);

        /////////////// Hierarchical /////////////////
        String wsRefHcl = impl.clusterHierarchical(new ClusterHierarchicalParams().withInputData(testWsName + "/" +
                exprObjName).withFeatureHeightCutoff(0.5).withOutWorkspace(testWsName)
                .withOutClustersetId(clustObj2Name), token, getContext());
        ObjectData res3 = getWsClient().getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObj2Name))).get(0);
        FeatureClusters clSet3 = res3.getData().asClassInstance(FeatureClusters.class);
        // TODO: check why it's not different from clSet4 case
        Assert.assertEquals(2, clSet3.getFeatureClusters().size());
        Assert.assertEquals(5, clSet3.getFeatureClusters().get(0).getIdToPos().size());
        Assert.assertEquals(2, clSet3.getFeatureClusters().get(1).getIdToPos().size());
        Assert.assertTrue(clSet3.getFeatureDendrogram().startsWith("("));
        Assert.assertTrue(clSet3.getFeatureDendrogram().endsWith(");"));

        //test that object id returned by method matches id returns by ws
        Tuple11<Long,String,String,String,Long,String,Long,String,String,Long,Map<String,String>> getInfoHcl = res3.getInfo();
        Assert.assertEquals(getInfoHcl.getE7()  + "/" + getInfoHcl.getE1() + "/" +  getInfoHcl.getE5(), wsRefHcl);

        /////////////// From dendrogram /////////////////
        String wsRefCfd = impl.clustersFromDendrogram(new ClustersFromDendrogramParams().withInputData(testWsName + "/" +
                clustObj2Name).withFeatureHeightCutoff(0.2).withOutWorkspace(testWsName)
                .withOutClustersetId(clustObj3Name), token, getContext());
        ObjectData res4 = getWsClient().getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObj3Name))).get(0);
        FeatureClusters clSet4 = res4.getData().asClassInstance(FeatureClusters.class);
        //check that saved object satisfies expected values
        Assert.assertEquals(3, clSet4.getFeatureClusters().size());
        Assert.assertEquals(2, clSet4.getFeatureClusters().get(0).getIdToPos().size());
        Assert.assertEquals(2, clSet4.getFeatureClusters().get(1).getIdToPos().size());
        Assert.assertEquals(3, clSet4.getFeatureClusters().get(2).getIdToPos().size());

        //test that object id returned by method matches id returns by ws
        Tuple11<Long,String,String,String,Long,String,Long,String,String,Long,Map<String,String>> getInfoCfd = res4.getInfo();
        Assert.assertEquals(getInfoCfd.getE7()  + "/" + getInfoCfd.getE1() + "/" +  getInfoCfd.getE5(), wsRefCfd);

                /////////////// Clusters download ///////////////
        File tempDir = new File("test/temp");
        tempDir.mkdir();
        File tsvTempFile = new File(tempDir, "clusters.tsv");
        try {
            FeatureClustersDownloader.generate(wsUrl, testWsName, clustObj1Name, 1, "TSV", token,
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
        } finally {
            if (tsvTempFile.exists())
                try {
                    tsvTempFile.delete();
                    tempDir.delete();
                } catch (Exception ignore) {
                }
        }
    }

    @Test
    public void testClustersFromLabels() throws Exception {
        FloatMatrix2D matrixData = getSampleMatrix();
        ClusterResults res = new ClusterResults().withClusterLabels(
                Arrays.asList(1L, -1L, -1L, -1L, 2L, 2L, 2L))
                .withMeancor(Arrays.asList(Double.NaN, 0.9999))
                .withMsecs(Arrays.asList(Double.NaN, 0.0062));
        List<LabeledCluster> clusters = KBaseFeatureValuesImpl.clustersFromLabels(matrixData, res);
        Assert.assertEquals(2, clusters.size());
    }


    private static void checkKMeansForSample(FeatureClusters clSet) {
        Assert.assertEquals(3, clSet.getFeatureClusters().size());
        Assert.assertEquals(2, clSet.getFeatureClusters().get(0).getIdToPos().size());
        Assert.assertEquals(0.9999, (double) clSet.getFeatureClusters().get(0).getMeancor(), 1e-4);


        Assert.assertEquals(0.018, (double) clSet.getFeatureClusters().get(0).getMsec(), 1e-4);
        Assert.assertEquals(2, clSet.getFeatureClusters().get(1).getIdToPos().size());
        Assert.assertEquals(0.9982, (double) clSet.getFeatureClusters().get(1).getMeancor(), 1e-4);
        Assert.assertEquals(0.0184, (double) clSet.getFeatureClusters().get(1).getMsec(), 1e-4);
        Assert.assertEquals(3, clSet.getFeatureClusters().get(2).getIdToPos().size());
        Assert.assertEquals(0.9999, (double) clSet.getFeatureClusters().get(2).getMeancor(), 1e-4);
        Assert.assertEquals(0.0062, (double) clSet.getFeatureClusters().get(2).getMsec(), 1e-4);
    }

    @Test
    public void testPyScikitKMeans() throws Exception {
        String testWsName = getWsName();
        String osName = System.getProperty("os.name");
        if (osName.toLowerCase().contains("mac"))
            return;
        String exprObjName = "py_expression1";
        String clustObj1Name = "py_clusters1";
        ExpressionMatrix data = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                .withData(getSampleMatrix());
        List<Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String, String>>> getInfoEKN = getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(exprObjName).withType("KBaseFeatureValues.ExpressionMatrix")
                        .withData(new UObject(data)))));
        String getId = impl.clusterKMeans(new ClusterKMeansParams().withInputData(testWsName + "/" +
                exprObjName).withK(3L).withOutWorkspace(testWsName).withOutClustersetId(clustObj1Name),
                token, getContext());
        ObjectData res = getWsClient().getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObj1Name))).get(0);

        //test if returned id matches id returned by ws saveobjects
        Assert.assertEquals(getInfoEKN.get(0).getE7()  + "/" + (getInfoEKN.get(0).getE1()+1) + "/" +  getInfoEKN.get(0).getE5(), getId);

        FeatureClusters clSet = res.getData().asClassInstance(FeatureClusters.class);
        checkKMeansForSample(clSet);
    }

    @Test
    public void testCorrectMatrix() throws Exception {
        String testWsName = getWsName();
        String sourceMatrixId = "notcorrected_matrix.1";

        FloatMatrix2D testmat = getSampleMatrix();
        ExpressionMatrix data = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                .withData(testmat);
        //introduce null value for imputation test
        data.getData().getValues().get(0).set(0, null);
        Assert.assertEquals(1, getNullCount(data.getData()));
        List<Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String, String>>> infoCM = getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(sourceMatrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                        .withData(new UObject(data)))));
        String targetMatrixId = "corrected_matrix.1";
        String getId = impl.correctMatrix(new CorrectMatrixParams().withInputData(
                testWsName + "/" + sourceMatrixId).withOutWorkspace(testWsName)
                .withOutMatrixId(targetMatrixId).withTransformType("missing"), token, getContext());

        ExpressionMatrix matrix = getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(targetMatrixId)))
                .get(0).getData().asClassInstance(ExpressionMatrix.class);

        //test matrix imputation
        Assert.assertEquals(0, getNullCount(matrix.getData()));
        Assert.assertEquals(0.325, (double) matrix.getData().getValues().get(0).get(0), 1e-10);

        //reload with returned object id
        ExpressionMatrix res = loadfromWs(getId).asClassInstance(ExpressionMatrix.class);
        //reset from null in data before imputation
        data.getData().getValues().get(0).set(0, res.getData().getValues().get(0).get(0));
        Assert.assertEquals(data.getData().toString(), matrix.getData().toString());

        //test if returned id matches id returned by ws saveobjects
        Assert.assertEquals(infoCM.get(0).getE7()  + "/" + (infoCM.get(0).getE1()+1) + "/" +  infoCM.get(0).getE5(), getId);

    }

    @Test
    public void testReconnectToGenome() throws Exception {
        String testWsName = getWsName();
        String genomeObjName = "Desulfovibrio_vulgaris_Hildenborough.genome";
        File inputDir = new File("test/data/upload1");
        File inputFile = new File(inputDir, "Desulfovibrio_vulgaris_Hildenborough_microarray_log_level_data.txt");
        ExpressionMatrix data = ExpressionUploader.parse(null, inputFile, "MO",
                null, true, null, null, null);
        String matrixId = "connected_matrix.1";
        List<Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String, String>>> infoEM = getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(matrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                        .withData(new UObject(data)))));
        String getId = impl.reconnectMatrixToGenome(new ReconnectMatrixToGenomeParams().withInputData(
                testWsName + "/" + matrixId).withOutWorkspace(testWsName).withGenomeRef(
                testWsName + "/" + genomeObjName), token, getContext());
        ExpressionMatrix matrix = getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(matrixId)))
                .get(0).getData().asClassInstance(ExpressionMatrix.class);

        //test feature mapping result
        Assert.assertEquals(2669, matrix.getFeatureMapping().size());

        //test if returned id matches id returned by ws saveobjects
        Assert.assertEquals(infoEM.get(0).getE7()  + "/" + infoEM.get(0).getE1() + "/" +  (infoEM.get(0).getE5()+1), getId);

    }

    @Test
    public void testBuildRowDescriptors() throws Exception {
        String testWsName = getWsName();
        ExpressionMatrix data = UObject.getMapper().readValue(new File("test/data/upload7/poplar_roots_exp_matrix_5000.json"), ExpressionMatrix.class);
        String matrixId = "row_descr_matrix.1";
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(matrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                        .withData(new UObject(data)))));
        try {
            MatrixStat stat = impl.getMatrixStat(new GetMatrixStatParams().withInputData(testWsName + "/" + matrixId),
                    token, getContext());
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
        String testWsName = getWsName();
        MatrixDescriptor md1 = impl.getMatrixDescriptor(new GetMatrixDescriptorParams().withInputData(
                testWsName + "/" + commonExpressionObjectName), token, getContext());
        Assert.assertEquals(2680L, (long) md1.getRowsCount());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testBuildFeatureSet() throws Exception {
        String testWsName = getWsName();
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
            String getId = impl.buildFeatureSet(new BuildFeatureSetParams().withDescription("Testing...")
                    .withBaseFeatureSet(testWsName + "/" + outFeatureSetObj1)
                    .withFeatureIds("DVUA1000").withGenome(testWsName + "/" + genomeObjName)
                    .withOutWorkspace(testWsName).withOutputFeatureSet(outFeatureSetObj2),
                    token, getContext());

            Assert.fail("Method should fail");
        } catch (Exception ex) {
            Assert.assertTrue(ex.getMessage(), ex.getMessage().contains("Some features are not found: "));
        }
        BuildFeatureSetParams bfsp = new BuildFeatureSetParams().withDescription("Testing...")
                .withBaseFeatureSet(testWsName + "/" + outFeatureSetObj1)
                .withFeatureIds("DVU1000").withGenome(testWsName + "/" + genomeObjName)
                .withOutWorkspace(testWsName).withOutputFeatureSet(outFeatureSetObj2);

        String getId = impl.buildFeatureSet(bfsp, token, getContext());

        //retrieve saved object
        ObjectData res1 = getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(outFeatureSetObj2)))
                .get(0);
        Map<String, Object> fs2 = res1.getData().asClassInstance(Map.class);

        Map<String, List<String>> elements2 = (Map<String, List<String>>) fs2.get("elements");
        Assert.assertEquals(4, elements2.size());

        //test that returned ref matched object id
        Tuple11<Long,String,String,String,Long,String,Long,String,String,Long,Map<String,String>> getInfoFS = res1.getInfo();
        Assert.assertEquals(getInfoFS.getE7()  + "/" + getInfoFS.getE1() + "/" +  getInfoFS.getE5(), getId);
    }

    @SuppressWarnings("unchecked")
    public Map<String, List<String>> buildFeatureSetForTesting(
            String genomeObjName, String outFeatureSetObj1, String featureIds,
            String featureIdsCustom) throws Exception {
        String testWsName = getWsName();
        String getId = impl.buildFeatureSet(new BuildFeatureSetParams().withDescription("Testing...")
                .withFeatureIds(featureIds).withFeatureIdsCustom(featureIdsCustom)
                .withGenome(testWsName + "/" + genomeObjName)
                .withOutWorkspace(testWsName).withOutputFeatureSet(outFeatureSetObj1),
                token, getContext());

        Map<String, Object> fs1 = getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(outFeatureSetObj1)))
                .get(0).getData().asClassInstance(Map.class);

        Map<String, List<String>> elements1 = (Map<String, List<String>>) fs1.get("elements");
        return elements1;
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSubMatrixStat() throws Exception {
        String testWsName = getWsName();
        File dir = new File("test/data/upload8");
        GZIPInputStream is = new GZIPInputStream(new FileInputStream(new File(dir, "Rhodobacter.genome.json.gz")));
        Map<String, Object> genomeData = UObject.getMapper().readValue(is, Map.class);
        is.close();
        String genomeObjName = "submatrix_genome.1";
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
            SubmatrixStat stat = impl.getSubmatrixStat(new GetSubmatrixStatParams().withInputData(testWsName + "/" + matrixId)
                    .withRowIds(Arrays.asList("RSP_0046", "RSP_1584", "RSP_1588")).withFlRowPairwiseCorrelation(1L)
                    .withFlRowSetStats(1L), token, getContext());
            Assert.assertEquals(3, stat.getRowPairwiseCorrelation().getComparisonValues().size());
            Assert.assertEquals(3, stat.getRowPairwiseCorrelation().getComparisonValues().get(0).size());
        } catch (ServerException ex) {
            System.err.println(ex.getData());
            throw ex;
        }
    }

    @Test
    public void testHierarchicalClustering() throws Exception {
        String testWsName = getWsName();
        String matrixId = "hierarchical_matrix.1";
        String clustObjName = "hierarchical_clusters.1";
        File inputFile = new File("test/data/upload6/E_coli_v4_Build_6_subdata.tsv");
        ExpressionMatrix data = ExpressionUploader.parse(null, inputFile, "Simple",
                null, true, null, null, null);
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                new ObjectSaveData().withName(matrixId).withType("KBaseFeatureValues.ExpressionMatrix")
                        .withData(new UObject(data)))));
        String wsRefHCL = impl.clusterHierarchical(new ClusterHierarchicalParams().withInputData(testWsName + "/" +
                matrixId).withFeatureHeightCutoff(0.2).withAlgorithm("flashClust").withMaxItems(1000L)
                .withOutWorkspace(testWsName).withOutClustersetId(clustObjName), token, getContext());
        ObjectData res1 = getWsClient().getObjects(Arrays.asList(new ObjectIdentity().withWorkspace(testWsName)
                .withName(clustObjName))).get(0);

        //test returned object values
        FeatureClusters clSet1 = res1.getData().asClassInstance(FeatureClusters.class);
        Assert.assertEquals(108, clSet1.getFeatureClusters().size());
        int nullCount = 0;
        for (LabeledCluster lc : clSet1.getFeatureClusters()) {
            if (lc.getMeancor() == null || lc.getMsec() == null)
                nullCount++;
        }
        Assert.assertEquals(10, nullCount);

        //test that returned ref matched object id
        Tuple11<Long,String,String,String,Long,String,Long,String,String,Long,Map<String,String>> getInfoKM = res1.getInfo();
        Assert.assertEquals(getInfoKM.getE7()  + "/" + getInfoKM.getE1() + "/" +  getInfoKM.getE5(), wsRefHCL);
    }

    @Test
    public void testTsvFileToMatrix() throws Exception {
        String testWsName = getWsName();
        String genomeObjName = commonGenomeObjectName;
        String exprObjName = "Desulfovibrio_vulgaris_Hildenborough.expression.2";
        File testFile = new File("test/data/upload2",
                "Desulfovibrio_vulgaris_Hildenborough_microarray_log_level_data.tsv");
        File tmpDir = Files.createTempDirectory(new File(config.get(
                KBaseFeatureValuesServer.CONFIG_PARAM_SCRATCH)).toPath(), "FromShock").toFile();
        try {
            File inputFile = new File(tmpDir, testFile.getName());
            FileUtils.copyFile(testFile, inputFile);
            URL callbackUrl = new URL(System.getenv("SDK_CALLBACK_URL"));
            DataFileUtilClient dataFileUtil = new DataFileUtilClient(callbackUrl, token);
            dataFileUtil.setIsInsecureHttpConnectionAllowed(true);
            String shockId = dataFileUtil.fileToShock(new FileToShockParams().withFilePath(
                    inputFile.getCanonicalPath())).getShockId();
            tempShockIdsToDelete.add(shockId);
            String matrixRef = impl.tsvFileToMatrix(new TsvFileToMatrixParams().withGenomeRef(
                    testWsName + "/" + genomeObjName).withInputShockId(shockId)
                    .withFillMissingValues(1L).withOutputWsName(testWsName)
                    .withOutputObjName(exprObjName), token, getContext()).getOutputMatrixRef();
            MatrixStat stats = impl.getMatrixStat(new GetMatrixStatParams().withInputData(
                    matrixRef), token, getContext());
            Assert.assertEquals("Desulfovibrio vulgaris str. Hildenborough",
                    stats.getMtxDescriptor().getGenomeName());
        } finally {
            FileUtils.deleteQuietly(tmpDir);
        }
    }

    @Test
    public void testExportMatrix() throws Exception {
        File tmpDir = Files.createTempDirectory(new File(config.get(
                KBaseFeatureValuesServer.CONFIG_PARAM_SCRATCH)).toPath(), "FromShock").toFile();
        try {
            String testWsName = getWsName();
            String matrixObjName = "matrix.1";
            File inputMatrixFile = new File(tmpDir, "input.tsv");
            String inputMatrix = "feature_ids\tval1\ngene.1\t1.234\n";
            FileUtils.writeStringToFile(inputMatrixFile, inputMatrix);
            String matrixRef = impl.tsvFileToMatrix(new TsvFileToMatrixParams().withInputFilePath(
                    inputMatrixFile.getCanonicalPath()).withFillMissingValues(0L)
                    .withOutputWsName(testWsName).withOutputObjName(matrixObjName), token,
                    getContext()).getOutputMatrixRef();
            String shockId = impl.exportMatrix(new ExportMatrixParams().withInputRef(matrixRef),
                    token, getContext()).getShockId();
            tempShockIdsToDelete.add(shockId);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            createShockClient().getFile(new ShockNodeId(shockId), baos);
            baos.close();
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));
            String outputMatrix = null;  //new String(baos.toByteArray());
            String infoJson = null;
            while (true) {
                ZipEntry ze = zis.getNextEntry();
                if (ze == null)
                    break;
                byte[] data = IOUtils.toByteArray(zis);
                if (ze.getName().endsWith(".tsv")) {
                    outputMatrix = new String(data);
                } else if (ze.getName().endsWith(".json")) {
                    infoJson = new String(data);
                } else {
                    throw new IllegalStateException("Unexpected zip entry: " + ze.getName());
                }
            }
            Assert.assertEquals(inputMatrix, outputMatrix);
            Assert.assertTrue(infoJson, infoJson.contains("\"metadata\": [") &&
                    infoJson.contains("\"provenance\": ["));
        } finally {
            FileUtils.deleteQuietly(tmpDir);
        }
    }

    @Test
    public void testExportClustersTsv() throws Exception {
        File tmpDir = Files.createTempDirectory(new File(config.get(
                KBaseFeatureValuesServer.CONFIG_PARAM_SCRATCH)).toPath(), "FromShock").toFile();
        try {
            String testWsName = getWsName();
            String matrixObjName = "matrix_for_export_clusters";
            ExpressionMatrix mdata = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                    .withData(getSampleMatrix());
            List<Tuple11<Long,String,String,String,Long,String,Long,String,String,Long,Map<String,String>>> getInfoEM = getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                    new ObjectSaveData().withName(matrixObjName)
                            .withType("KBaseFeatureValues.ExpressionMatrix")
                            .withData(new UObject(mdata)))));
            String clustersObjName = "export_clusters";
            String getId = impl.clusterKMeans(new ClusterKMeansParams().withInputData(testWsName + "/" +
                    matrixObjName).withK(3L).withOutWorkspace(testWsName)
                    .withOutClustersetId(clustersObjName),
                    token, getContext());

            //test if returned id matches id returned by ws saveobjects
            Assert.assertEquals(getInfoEM.get(0).getE7()  + "/" + (getInfoEM.get(0).getE1()+1) + "/" +  getInfoEM.get(0).getE5(), getId);

            String shockId = impl.exportClustersTsv(new ExportClustersTsvParams().withInputRef(
                    testWsName + "/" + clustersObjName), token, getContext()).getShockId();
            tempShockIdsToDelete.add(shockId);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            createShockClient().getFile(new ShockNodeId(shockId), baos);
            baos.close();
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));
            String outputClusters = null;
            String infoJson = null;
            while (true) {
                ZipEntry ze = zis.getNextEntry();
                if (ze == null)
                    break;
                byte[] data = IOUtils.toByteArray(zis);
                if (ze.getName().endsWith(".tsv")) {
                    outputClusters = new String(data);
                } else if (ze.getName().endsWith(".json")) {
                    infoJson = new String(data);
                } else {
                    throw new IllegalStateException("Unexpected zip entry: " + ze.getName());
                }
            }
            Assert.assertTrue(outputClusters, outputClusters.contains("g1\t"));
            Assert.assertTrue(infoJson, infoJson.contains("\"metadata\": [") &&
                    infoJson.contains("\"provenance\": ["));
        } finally {
            FileUtils.deleteQuietly(tmpDir);
        }
    }

    @Test
    public void testExportClustersSif() throws Exception {
        File tmpDir = Files.createTempDirectory(new File(config.get(
                KBaseFeatureValuesServer.CONFIG_PARAM_SCRATCH)).toPath(), "FromShock").toFile();
        try {
            String testWsName = getWsName();
            String matrixObjName = "matrix_for_export_clusters";
            ExpressionMatrix mdata = new ExpressionMatrix().withType("log-ratio").withScale("1.0")
                    .withData(getSampleMatrix());
            getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(testWsName).withObjects(Arrays.asList(
                    new ObjectSaveData().withName(matrixObjName)
                            .withType("KBaseFeatureValues.ExpressionMatrix")
                            .withData(new UObject(mdata)))));
            String clustersObjName = "export_clusters";

            impl.clusterKMeans(new ClusterKMeansParams().withInputData(testWsName + "/" +
                    matrixObjName).withK(3L).withOutWorkspace(testWsName)
                    .withOutClustersetId(clustersObjName),
                    token, getContext());

            String shockId = impl.exportClustersSif(new ExportClustersSifParams().withInputRef(
                    testWsName + "/" + clustersObjName), token, getContext()).getShockId();
            tempShockIdsToDelete.add(shockId);
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            createShockClient().getFile(new ShockNodeId(shockId), baos);
            baos.close();
            ZipInputStream zis = new ZipInputStream(new ByteArrayInputStream(baos.toByteArray()));
            String outputClusters = null;
            String infoJson = null;
            while (true) {
                ZipEntry ze = zis.getNextEntry();
                if (ze == null)
                    break;
                byte[] data = IOUtils.toByteArray(zis);
                if (ze.getName().endsWith(".sif")) {
                    outputClusters = new String(data);
                } else if (ze.getName().endsWith(".json")) {
                    infoJson = new String(data);
                } else {
                    throw new IllegalStateException("Unexpected zip entry: " + ze.getName());
                }
            }
            Assert.assertTrue(outputClusters, outputClusters.contains("g1 pc "));
            Assert.assertTrue(infoJson, infoJson.contains("\"metadata\": [") &&
                    infoJson.contains("\"provenance\": ["));
        } finally {
            FileUtils.deleteQuietly(tmpDir);
        }
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


    /*
    getWsClient().getObjects(Arrays.asList(
                new ObjectIdentity().withWorkspace(testWsName).withName(targetMatrixId)))
                .get(0).getData().asClassInstance(ExpressionMatrix.class);
	*/


    private static UObject loadfromWs(String strId) {
        String[] getIdParts = strId.split("/");
        UObject res = null;
        try {
            res = getWsClient().getObjects(Arrays.asList(new ObjectIdentity().withWsid(Long.parseLong(getIdParts[0])).
                    withObjid(Long.parseLong(getIdParts[1])).withVer(Long.parseLong(getIdParts[2])))).get(0).getData();
        } catch (IOException e) {
            System.out.println("error saving object as " + strId);
            System.out.println(e.toString());
        } catch (JsonClientException ee) {
            System.out.println("Json client exception");
            System.out.println(ee.toString());
        }

        return res;
    }
}

