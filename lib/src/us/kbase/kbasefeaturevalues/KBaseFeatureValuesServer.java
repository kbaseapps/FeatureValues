package us.kbase.kbasefeaturevalues;

import java.io.File;
import java.util.List;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;

//BEGIN_HEADER
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import us.kbase.workspace.WorkspaceClient;
//END_HEADER

/**
 * <p>Original spec-file module name: KBaseFeatureValues</p>
 * <pre>
 * The KBaseFeatureValues set of data types and service provides a mechanism for
 * representing numeric values associated with genome features and conditions, together
 * with some basic operations on this data.  Essentially, the data is stored as a simple
 * 2D matrix of floating point numbers.  Currently, this is exposed as support for
 * expression data and single gene knockout fitness data.  (Fitness data being growth
 * rate relative to WT growth with the specified single gene knockout in a specified
 * condition).
 * The operations supported on this data is simple clustering of genes and clustering 
 * related tools.
 * </pre>
 */
public class KBaseFeatureValuesServer extends JsonServerServlet {
    private static final long serialVersionUID = 1L;
    private static final String version = "0.0.8";
    private static final String gitUrl = "https://github.com/rsutormin/feature_values";
    private static final String gitCommitHash = "04f66d38c9f4a139ff7d17a142bfb6f25ee603dc";

    //BEGIN_CLASS_HEADER
    public static final String SERVICE_NAME = "KBaseFeatureValues";
    public static final String CONFIG_PARAM_SCRATCH = "scratch";
    public static final String CONFIG_PARAM_WS_URL = "ws.url";
    public static final String CONFIG_PARAM_UJS_URL = "ujs.url";
    public static final String CONFIG_PARAM_SHOCK_URL = "shock.url";
    public static final String CONFIG_PARAM_AWE_URL = "awe.url";
    public static final String CONFIG_PARAM_CLIENT_BIN_DIR = "client.bin.dir";
    public static final String CONFIG_PARAM_CLIENT_WORK_DIR = "client.work.dir";
    public static final String AWE_CLIENT_SCRIPT_NAME = "awe_" + SERVICE_NAME + "_run_job.sh";
    public static final String SERVICE_VERSION = "0.8";
    
    public WorkspaceClient getWsClient(AuthToken authPart) throws Exception {
        String wsUrl = config.get(CONFIG_PARAM_WS_URL);
        if (wsUrl == null)
            throw new IllegalStateException("Parameter '" + CONFIG_PARAM_WS_URL +
                    "' is not defined in configuration");
        WorkspaceClient wsClient = new WorkspaceClient(new URL(wsUrl), authPart);
        wsClient.setAuthAllowedForHttp(true);
        return wsClient;
    }

    private KBaseFeatureValuesImpl impl(AuthToken authPart) throws Exception {
        return new KBaseFeatureValuesImpl(null, authPart.toString(), config, null);
    }
    //END_CLASS_HEADER

    public KBaseFeatureValuesServer() throws Exception {
        super("KBaseFeatureValues");
        //BEGIN_CONSTRUCTOR
        //END_CONSTRUCTOR
    }

    /**
     * <p>Original spec-file function name: estimate_k</p>
     * <pre>
     * Used as an analysis step before generating clusters using K-means clustering, this method
     * provides an estimate of K by [...]
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.EstimateKParams EstimateKParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.estimate_k", async=true)
    public String estimateK(EstimateKParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN estimate_k
        returnVal = impl(authPart).estimateK(params);
        //END estimate_k
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: estimate_k_new</p>
     * <pre>
     * Used as an analysis step before generating clusters using K-means clustering, this method
     * provides an estimate of K by [...]
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.EstimateKParamsNew EstimateKParamsNew}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.estimate_k_new", async=true)
    public String estimateKNew(EstimateKParamsNew params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN estimate_k_new
        returnVal = impl(authPart).estimateKNew(params);
        //END estimate_k_new
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: cluster_k_means</p>
     * <pre>
     * Clusters features by K-means clustering.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClusterKMeansParams ClusterKMeansParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.cluster_k_means", async=true)
    public String clusterKMeans(ClusterKMeansParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN cluster_k_means
        returnVal = impl(authPart).clusterKMeans(params);
        //END cluster_k_means
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: cluster_hierarchical</p>
     * <pre>
     * Clusters features by hierarchical clustering.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClusterHierarchicalParams ClusterHierarchicalParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.cluster_hierarchical", async=true)
    public String clusterHierarchical(ClusterHierarchicalParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN cluster_hierarchical
        returnVal = impl(authPart).clusterHierarchical(params);
        //END cluster_hierarchical
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: clusters_from_dendrogram</p>
     * <pre>
     * Given a FeatureClusters with a dendogram built from a hierarchical clustering
     * method, this function creates new clusters by cutting the dendogram at
     * a specific hieght or by some other approach.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClustersFromDendrogramParams ClustersFromDendrogramParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.clusters_from_dendrogram", async=true)
    public String clustersFromDendrogram(ClustersFromDendrogramParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN clusters_from_dendrogram
        returnVal = impl(authPart).clustersFromDendrogram(params);
        //END clusters_from_dendrogram
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: evaluate_clusterset_quality</p>
     * <pre>
     * Given a FeatureClusters with a dendogram built from a hierarchical clustering
     * method, this function creates new clusters by cutting the dendogram at
     * a specific hieght or by some other approach.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.EvaluateClustersetQualityParams EvaluateClustersetQualityParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.evaluate_clusterset_quality", async=true)
    public String evaluateClustersetQuality(EvaluateClustersetQualityParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN evaluate_clusterset_quality
        impl(authPart).evaluateClustersetQuality(params);
        //END evaluate_clusterset_quality
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: validate_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ValidateMatrixParams ValidateMatrixParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.validate_matrix", authOptional=true, async=true)
    public String validateMatrix(ValidateMatrixParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN validate_matrix
        impl(authPart).validateMatrix(params);
        //END validate_matrix
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: correct_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.CorrectMatrixParams CorrectMatrixParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.correct_matrix", async=true)
    public String correctMatrix(CorrectMatrixParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN correct_matrix
        returnVal = impl(authPart).correctMatrix(params);
        //END correct_matrix
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: status</p>
     * <pre>
     * </pre>
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ServiceStatus ServiceStatus}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.status", async=true)
    public ServiceStatus status(RpcContext jsonRpcContext) throws Exception {
        ServiceStatus returnVal = null;
        //BEGIN status
        String status = "OK";
        String startupTime = "";
        String gitUrl = "";
        String branch = "";
        String commit = "";
        String deployCfgPath = "";
        Map<String, String> safeConfig = new LinkedHashMap<String, String>();
        try {
            long dateTime = ManagementFactory.getRuntimeMXBean().getStartTime();
            startupTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(dateTime);
            String envVar = "KB_DEPLOYMENT_CONFIG";
            deployCfgPath = System.getProperty(envVar) == null ? System.getenv(envVar) : 
                System.getProperty(envVar);
            if (config == null) {
                status = "Error: configuration is not found";
            } else {
                String[] keys = {CONFIG_PARAM_SCRATCH, CONFIG_PARAM_WS_URL, CONFIG_PARAM_UJS_URL, 
                        CONFIG_PARAM_SHOCK_URL, CONFIG_PARAM_AWE_URL};
                for (String key : keys) {
                    String value = config.get(key);
                    if (value == null) {
                        status = "Error: configuration parameter '" + key + "' is not defined";
                    } else {
                        safeConfig.put(key, value);
                    }
                }
            }
            Properties gitProps = new Properties();
            InputStream is = this.getClass().getResourceAsStream("git.properties");
            gitProps.load(is);
            is.close();
            gitUrl = gitProps.getProperty("giturl");
            branch = gitProps.getProperty("branch");
            commit = gitProps.getProperty("commit");
        } catch (Exception ex) {
            status = "Error: " + ex.getMessage();
        }
        returnVal = new ServiceStatus().withVersion(SERVICE_VERSION).withStatus(status)
                .withStartupTime(startupTime).withGiturl(gitUrl).withBranch(branch)
                .withCommit(commit).withSafeConfiguration(safeConfig)
                .withDeploymentCfgPath(deployCfgPath);
        //END status
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: reconnect_matrix_to_genome</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ReconnectMatrixToGenomeParams ReconnectMatrixToGenomeParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.reconnect_matrix_to_genome", async=true)
    public String reconnectMatrixToGenome(ReconnectMatrixToGenomeParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN reconnect_matrix_to_genome
        returnVal = impl(authPart).reconnectMatrixToGenome(params);
        //END reconnect_matrix_to_genome
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: build_feature_set</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.BuildFeatureSetParams BuildFeatureSetParams}
     * @return   parameter "job_id" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.build_feature_set", async=true)
    public String buildFeatureSet(BuildFeatureSetParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN build_feature_set
        returnVal = impl(authPart).buildFeatureSet(params);
        //END build_feature_set
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_matrix_descriptor</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixDescriptorParams GetMatrixDescriptorParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.MatrixDescriptor MatrixDescriptor}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_matrix_descriptor", async=true)
    public MatrixDescriptor getMatrixDescriptor(GetMatrixDescriptorParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        MatrixDescriptor returnVal = null;
        //BEGIN get_matrix_descriptor
        returnVal = impl(authPart).getMatrixDescriptor(arg1);
        //END get_matrix_descriptor
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_matrix_row_descriptors</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixItemDescriptorsParams GetMatrixItemDescriptorsParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemDescriptor ItemDescriptor}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_matrix_row_descriptors", async=true)
    public List<ItemDescriptor> getMatrixRowDescriptors(GetMatrixItemDescriptorsParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<ItemDescriptor> returnVal = null;
        //BEGIN get_matrix_row_descriptors
        //END get_matrix_row_descriptors
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_matrix_column_descriptors</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixItemDescriptorsParams GetMatrixItemDescriptorsParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemDescriptor ItemDescriptor}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_matrix_column_descriptors", async=true)
    public List<ItemDescriptor> getMatrixColumnDescriptors(GetMatrixItemDescriptorsParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<ItemDescriptor> returnVal = null;
        //BEGIN get_matrix_column_descriptors
        //END get_matrix_column_descriptors
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_matrix_rows_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixItemsStatParams GetMatrixItemsStatParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemStat ItemStat}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_matrix_rows_stat", async=true)
    public List<ItemStat> getMatrixRowsStat(GetMatrixItemsStatParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<ItemStat> returnVal = null;
        //BEGIN get_matrix_rows_stat
        returnVal = impl(authPart).getMatrixRowsStat(arg1);
        //END get_matrix_rows_stat
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_matrix_columns_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixItemsStatParams GetMatrixItemsStatParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemStat ItemStat}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_matrix_columns_stat", async=true)
    public List<ItemStat> getMatrixColumnsStat(GetMatrixItemsStatParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<ItemStat> returnVal = null;
        //BEGIN get_matrix_columns_stat
        returnVal = impl(authPart).getMatrixColumnsStat(arg1);
        //END get_matrix_columns_stat
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_matrix_row_sets_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixSetsStatParams GetMatrixSetsStatParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemSetStat ItemSetStat}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_matrix_row_sets_stat", async=true)
    public List<ItemSetStat> getMatrixRowSetsStat(GetMatrixSetsStatParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<ItemSetStat> returnVal = null;
        //BEGIN get_matrix_row_sets_stat
        returnVal = impl(authPart).getMatrixRowSetsStat(arg1);
        //END get_matrix_row_sets_stat
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_matrix_column_sets_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixSetsStatParams GetMatrixSetsStatParams}
     * @return   instance of list of type {@link us.kbase.kbasefeaturevalues.ItemSetStat ItemSetStat}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_matrix_column_sets_stat", async=true)
    public List<ItemSetStat> getMatrixColumnSetsStat(GetMatrixSetsStatParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        List<ItemSetStat> returnVal = null;
        //BEGIN get_matrix_column_sets_stat
        returnVal = impl(authPart).getMatrixColumnSetsStat(arg1);
        //END get_matrix_column_sets_stat
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_matrix_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetMatrixStatParams GetMatrixStatParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.MatrixStat MatrixStat}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_matrix_stat", async=true)
    public MatrixStat getMatrixStat(GetMatrixStatParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        MatrixStat returnVal = null;
        //BEGIN get_matrix_stat
        returnVal = impl(authPart).getMatrixStat(arg1);
        //END get_matrix_stat
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: get_submatrix_stat</p>
     * <pre>
     * </pre>
     * @param   arg1   instance of type {@link us.kbase.kbasefeaturevalues.GetSubmatrixStatParams GetSubmatrixStatParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.SubmatrixStat SubmatrixStat}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.get_submatrix_stat", async=true)
    public SubmatrixStat getSubmatrixStat(GetSubmatrixStatParams arg1, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        SubmatrixStat returnVal = null;
        //BEGIN get_submatrix_stat
        returnVal = impl(authPart).getSubmatrixStat(arg1);
        //END get_submatrix_stat
        return returnVal;
    }

    public static void main(String[] args) throws Exception {
        if (args.length == 1) {
            new KBaseFeatureValuesServer().startupServer(Integer.parseInt(args[0]));
        } else if (args.length == 3) {
            JsonServerSyslog.setStaticUseSyslog(false);
            JsonServerSyslog.setStaticMlogFile(args[1] + ".log");
            new KBaseFeatureValuesServer().processRpcCall(new File(args[0]), new File(args[1]), args[2]);
        } else {
            System.out.println("Usage: <program> <server_port>");
            System.out.println("   or: <program> <context_json_file> <output_json_file> <token>");
            return;
        }
    }
}
