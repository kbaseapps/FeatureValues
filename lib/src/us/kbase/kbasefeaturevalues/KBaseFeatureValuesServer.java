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
    private static final String version = "0.0.22";
    private static final String gitUrl = "https://github.com/mrcreosote/FeatureValues.git";
    private static final String gitCommitHash = "6fd397b4efefa132665237cee1a5f1363642362a";

    //BEGIN_CLASS_HEADER
    public static final String CONFIG_PARAM_WS_URL = "ws.url";
    public static final String CONFIG_PARAM_CLIENT_BIN_DIR = "client.bin.dir";
    public static final String CONFIG_PARAM_SCRATCH = "scratch";
    public static final String CONFIG_PARAM_SRV_WIZ_URL = "service.wizard.url";
    
    private KBaseFeatureValuesImpl impl(AuthToken authPart) throws Exception {
        return new KBaseFeatureValuesImpl(null, authPart, config, null);
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
     * @return   parameter "output" of type {@link us.kbase.kbasefeaturevalues.EstimateKResult EstimateKResult}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.estimate_k", async=true)
    public EstimateKResult estimateK(EstimateKParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        EstimateKResult returnVal = null;
        //BEGIN estimate_k
        returnVal = impl(authPart).estimateK(params, prov(jsonRpcContext));
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
     * @return   parameter "output" of type {@link us.kbase.kbasefeaturevalues.EstimateKResult EstimateKResult}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.estimate_k_new", async=true)
    public EstimateKResult estimateKNew(EstimateKParamsNew params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        EstimateKResult returnVal = null;
        //BEGIN estimate_k_new
        returnVal = impl(authPart).estimateKNew(params, prov(jsonRpcContext));
        //END estimate_k_new
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: cluster_k_means</p>
     * <pre>
     * Clusters features by K-means clustering.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClusterKMeansParams ClusterKMeansParams}
     * @return   parameter "workspace_ref" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.cluster_k_means", async=true)
    public String clusterKMeans(ClusterKMeansParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN cluster_k_means
        returnVal = impl(authPart).clusterKMeans(params, prov(jsonRpcContext));
        //END cluster_k_means
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: cluster_hierarchical</p>
     * <pre>
     * Clusters features by hierarchical clustering.
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClusterHierarchicalParams ClusterHierarchicalParams}
     * @return   parameter "workspace_ref" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.cluster_hierarchical", async=true)
    public String clusterHierarchical(ClusterHierarchicalParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN cluster_hierarchical
        returnVal = impl(authPart).clusterHierarchical(params, prov(jsonRpcContext));
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
     * @return   parameter "workspace_ref" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.clusters_from_dendrogram", async=true)
    public String clustersFromDendrogram(ClustersFromDendrogramParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN clusters_from_dendrogram
        returnVal = impl(authPart).clustersFromDendrogram(params, prov(jsonRpcContext));
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
     * @return   parameter "workspace_ref" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.correct_matrix", async=true)
    public String correctMatrix(CorrectMatrixParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN correct_matrix
        returnVal = impl(authPart).correctMatrix(params, prov(jsonRpcContext));
        //END correct_matrix
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: reconnect_matrix_to_genome</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ReconnectMatrixToGenomeParams ReconnectMatrixToGenomeParams}
     * @return   parameter "workspace_ref" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.reconnect_matrix_to_genome", async=true)
    public String reconnectMatrixToGenome(ReconnectMatrixToGenomeParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN reconnect_matrix_to_genome
        returnVal = impl(authPart).reconnectMatrixToGenome(params, prov(jsonRpcContext));
        //END reconnect_matrix_to_genome
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: build_feature_set</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.BuildFeatureSetParams BuildFeatureSetParams}
     * @return   parameter "workspace_ref" of String
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.build_feature_set", async=true)
    public String buildFeatureSet(BuildFeatureSetParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        String returnVal = null;
        //BEGIN build_feature_set
        returnVal = impl(authPart).buildFeatureSet(params, prov(jsonRpcContext));
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
     * <p>Original spec-file function name: clusters_to_file</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ClustersToFileParams ClustersToFileParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ClustersToFileOutput ClustersToFileOutput}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.clusters_to_file", async=true)
    public ClustersToFileOutput clustersToFile(ClustersToFileParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        ClustersToFileOutput returnVal = null;
        //BEGIN clusters_to_file
        returnVal = impl(authPart).clustersToFile(params);
        //END clusters_to_file
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: export_clusters_tsv</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersTsvParams ExportClustersTsvParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersTsvOutput ExportClustersTsvOutput}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.export_clusters_tsv", async=true)
    public ExportClustersTsvOutput exportClustersTsv(ExportClustersTsvParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        ExportClustersTsvOutput returnVal = null;
        //BEGIN export_clusters_tsv
        returnVal = impl(authPart).exportClustersTsv(params);
        //END export_clusters_tsv
        return returnVal;
    }

    /**
     * <p>Original spec-file function name: export_clusters_sif</p>
     * <pre>
     * </pre>
     * @param   params   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersSifParams ExportClustersSifParams}
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.ExportClustersSifOutput ExportClustersSifOutput}
     */
    @JsonServerMethod(rpc = "KBaseFeatureValues.export_clusters_sif", async=true)
    public ExportClustersSifOutput exportClustersSif(ExportClustersSifParams params, AuthToken authPart, RpcContext jsonRpcContext) throws Exception {
        ExportClustersSifOutput returnVal = null;
        //BEGIN export_clusters_sif
        returnVal = impl(authPart).exportClustersSif(params);
        //END export_clusters_sif
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
