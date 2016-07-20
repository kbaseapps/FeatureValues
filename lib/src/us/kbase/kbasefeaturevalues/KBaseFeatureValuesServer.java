package us.kbase.kbasefeaturevalues;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import us.kbase.auth.AuthToken;
import us.kbase.common.service.JsonServerMethod;
import us.kbase.common.service.JsonServerServlet;
import us.kbase.common.service.JsonServerSyslog;
import us.kbase.common.service.RpcContext;

//BEGIN_HEADER
import us.kbase.workspace.ProvenanceAction;
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
    private static final String version = "0.0.9";
    private static final String gitUrl = "https://github.com/rsutormin/feature_values";
    private static final String gitCommitHash = "024a734645bf64adb230e97837efaf8ec1b59ff1";

    //BEGIN_CLASS_HEADER
    public static final String CONFIG_PARAM_WS_URL = "ws.url";
    public static final String CONFIG_PARAM_CLIENT_BIN_DIR = "client.bin.dir";
    public static final String CONFIG_PARAM_SCRATCH = "scratch";
    
    private KBaseFeatureValuesImpl impl(AuthToken authPart) throws Exception {
        return new KBaseFeatureValuesImpl(null, authPart.toString(), config, null);
    }
    
    @SuppressWarnings("unchecked")
    private List<ProvenanceAction> prov(RpcContext jsonRpcContext) {
        return (List<ProvenanceAction>)jsonRpcContext.getProvenance();
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
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.estimate_k", async=true)
    public void estimateK(EstimateKParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN estimate_k
        impl(authPart).estimateK(params, prov(jsonRpcContext));
        //END estimate_k
    }

    /**
     * <p>Original spec-file function name: estimate_k_new</p>
     * <pre>
     * Used as an analysis step before generating clusters using K-means clustering, this method
     * provides an estimate of K by [...]
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.EstimateKParamsNew EstimateKParamsNew}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.estimate_k_new", async=true)
    public void estimateKNew(EstimateKParamsNew params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN estimate_k_new
        impl(authPart).estimateKNew(params, prov(jsonRpcContext));
        //END estimate_k_new
    }

    /**
     * <p>Original spec-file function name: cluster_k_means</p>
     * <pre>
     * Clusters features by K-means clustering.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClusterKMeansParams ClusterKMeansParams}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.cluster_k_means", async=true)
    public void clusterKMeans(ClusterKMeansParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN cluster_k_means
        impl(authPart).clusterKMeans(params, prov(jsonRpcContext));
        //END cluster_k_means
    }

    /**
     * <p>Original spec-file function name: cluster_hierarchical</p>
     * <pre>
     * Clusters features by hierarchical clustering.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClusterHierarchicalParams ClusterHierarchicalParams}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.cluster_hierarchical", async=true)
    public void clusterHierarchical(ClusterHierarchicalParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN cluster_hierarchical
        impl(authPart).clusterHierarchical(params, prov(jsonRpcContext));
        //END cluster_hierarchical
    }

    /**
     * <p>Original spec-file function name: clusters_from_dendrogram</p>
     * <pre>
     * Given a FeatureClusters with a dendogram built from a hierarchical clustering
     * method, this function creates new clusters by cutting the dendogram at
     * a specific hieght or by some other approach.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClustersFromDendrogramParams ClustersFromDendrogramParams}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.clusters_from_dendrogram", async=true)
    public void clustersFromDendrogram(ClustersFromDendrogramParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN clusters_from_dendrogram
        impl(authPart).clustersFromDendrogram(params, prov(jsonRpcContext));
        //END clusters_from_dendrogram
    }

    /**
     * <p>Original spec-file function name: evaluate_clusterset_quality</p>
     * <pre>
     * Given a FeatureClusters with a dendogram built from a hierarchical clustering
     * method, this function creates new clusters by cutting the dendogram at
     * a specific hieght or by some other approach.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.EvaluateClustersetQualityParams EvaluateClustersetQualityParams}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.evaluate_clusterset_quality", async=true)
    public void evaluateClustersetQuality(EvaluateClustersetQualityParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN evaluate_clusterset_quality
        impl(authPart).evaluateClustersetQuality(params, prov(jsonRpcContext));
        //END evaluate_clusterset_quality
    }

    /**
     * <p>Original spec-file function name: validate_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ValidateMatrixParams ValidateMatrixParams}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.validate_matrix", authOptional=true, async=true)
    public void validateMatrix(ValidateMatrixParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN validate_matrix
        impl(authPart).validateMatrix(params, prov(jsonRpcContext));
        //END validate_matrix
    }

    /**
     * <p>Original spec-file function name: correct_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.CorrectMatrixParams CorrectMatrixParams}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.correct_matrix", async=true)
    public void correctMatrix(CorrectMatrixParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN correct_matrix
        impl(authPart).correctMatrix(params, prov(jsonRpcContext));
        //END correct_matrix
    }

    /**
     * <p>Original spec-file function name: reconnect_matrix_to_genome</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ReconnectMatrixToGenomeParams ReconnectMatrixToGenomeParams}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.reconnect_matrix_to_genome", async=true)
    public void reconnectMatrixToGenome(ReconnectMatrixToGenomeParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN reconnect_matrix_to_genome
        impl(authPart).reconnectMatrixToGenome(params, prov(jsonRpcContext));
        //END reconnect_matrix_to_genome
    }

    /**
     * <p>Original spec-file function name: build_feature_set</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.BuildFeatureSetParams BuildFeatureSetParams}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.build_feature_set", async=true)
    public void buildFeatureSet(BuildFeatureSetParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        //BEGIN build_feature_set
        impl(authPart).buildFeatureSet(params, prov(jsonRpcContext));
        //END build_feature_set
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

    /**
     * <p>Original spec-file function name: tsv_file_to_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.TsvFileToMatrixParams TsvFileToMatrixParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.TsvFileToMatrixOutput TsvFileToMatrixOutput}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.tsv_file_to_matrix", async=true)
    public TsvFileToMatrixOutput tsvFileToMatrix(TsvFileToMatrixParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        TsvFileToMatrixOutput returnVal = null;
        //BEGIN tsv_file_to_matrix
        returnVal = impl(authPart).tsvFileToMatrix(params);
        //END tsv_file_to_matrix
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: matrix_to_tsv_file</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.MatrixToTsvFileParams MatrixToTsvFileParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.MatrixToTsvFileOutput MatrixToTsvFileOutput}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.matrix_to_tsv_file", async=true)
    public MatrixToTsvFileOutput matrixToTsvFile(MatrixToTsvFileParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        MatrixToTsvFileOutput returnVal = null;
        //BEGIN matrix_to_tsv_file
        returnVal = impl(authPart).matrixToTsvFile(params);
        //END matrix_to_tsv_file
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: export_matrix</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ExportMatrixParams ExportMatrixParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ExportMatrixOutput ExportMatrixOutput}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.export_matrix", async=true)
    public ExportMatrixOutput exportMatrix(ExportMatrixParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        ExportMatrixOutput returnVal = null;
        //BEGIN export_matrix
        returnVal = impl(authPart).exportMatrix(params);
        //END export_matrix
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: clusters_to_tsv_file</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClustersToTsvFileParams ClustersToTsvFileParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ClustersToTsvFileOutput ClustersToTsvFileOutput}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.clusters_to_tsv_file", async=true)
    public ClustersToTsvFileOutput clustersToTsvFile(ClustersToTsvFileParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        ClustersToTsvFileOutput returnVal = null;
        //BEGIN clusters_to_tsv_file
        returnVal = impl(authPart).clustersToTsvFile(params);
        //END clusters_to_tsv_file
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: export_clusters</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersParams ExportClustersParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersOutput ExportClustersOutput}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.export_clusters", async=true)
    public ExportClustersOutput exportClusters(ExportClustersParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        ExportClustersOutput returnVal = null;
        //BEGIN export_clusters
        returnVal = impl(authPart).exportClusters(params);
        //END export_clusters
        return returnVal;
    }
    @JsonServerMethod(rpc = "KBaseFeatureValues.status")
    public Map<String, Object> status() {
        Map<String, Object> returnVal = null;
        //BEGIN_STATUS
        returnVal = new LinkedHashMap<String, Object>();
        returnVal.put("state", "OK");
        returnVal.put("message", "");
        returnVal.put("version", version);
        returnVal.put("git_url", gitUrl);
        returnVal.put("git_commit_hash", gitCommitHash);
        //END_STATUS
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
