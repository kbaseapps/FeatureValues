package us.kbase.clusterservice;

import com.fasterxml.jackson.core.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import us.kbase.common.service.JsonClientException;
import us.kbase.common.service.JsonLocalClientCaller;
import us.kbase.kbasefeaturevalues.EstimateKResult;
import us.kbase.kbasefeaturevalues.FloatMatrix2D;

/**
 * <p>Original spec-file module name: ClusterServiceR</p>
 * <pre>
 * </pre>
 */
public class ClusterServiceRLocalClient extends JsonLocalClientCaller implements ClusterServiceLocalClient {
    
    public ClusterServiceRLocalClient(File workDir) {
        super(workDir);
    }

    /**
     * <p>Original spec-file function name: cluster_k_means</p>
     * <pre>
     * Clusters features by K-means clustering.
     * </pre>
     * @param   matrix   instance of type {@link us.kbase.kbasefeaturevalues.FloatMatrix2D FloatMatrix2D}
     * @param   k   instance of Long
     * @param   nStart   instance of Long
     * @param   maxIter   instance of Long
     * @param   randomSeed   instance of Long
     * @param   algorithm   instance of String
     * @return   instance of type {@link us.kbase.clusterservice.ClusterResults ClusterResults}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public ClusterResults clusterKMeans(FloatMatrix2D matrix, Long k, Long nStart, Long maxIter, Long randomSeed, String algorithm) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(matrix);
        args.add(k);
        args.add(nStart);
        args.add(maxIter);
        args.add(randomSeed);
        args.add(algorithm);
        TypeReference<List<ClusterResults>> retType = new TypeReference<List<ClusterResults>>() {};
        List<ClusterResults> res = jsonrpcCall("ClusterServiceR.cluster_k_means", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: estimate_k</p>
     * <pre>
     * Used as an analysis step before generating clusters using K-means 
     * clustering, this method provides an estimate of K by [...]
     * </pre>
     * @param   matrix   instance of type {@link us.kbase.kbasefeaturevalues.FloatMatrix2D FloatMatrix2D}
     * @param   minK   instance of Long
     * @param   maxK   instance of Long
     * @param   maxIter   instance of Long
     * @param   randomSeed   instance of Long
     * @param   neighbSize   instance of Long
     * @param   maxItems   instance of Long
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.EstimateKResult EstimateKResult}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public EstimateKResult estimateK(FloatMatrix2D matrix, Long minK, Long maxK, Long maxIter, Long randomSeed, Long neighbSize, Long maxItems) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(matrix);
        args.add(minK);
        args.add(maxK);
        args.add(maxIter);
        args.add(randomSeed);
        args.add(neighbSize);
        args.add(maxItems);
        TypeReference<List<EstimateKResult>> retType = new TypeReference<List<EstimateKResult>>() {};
        List<EstimateKResult> res = jsonrpcCall("ClusterServiceR.estimate_k", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: estimate_k_new</p>
     * <pre>
     * Used as an analysis step before generating clusters using K-means
     * clustering, this method provides an estimate of K by [...]
     * </pre>
     * @param   matrix   instance of type {@link us.kbase.kbasefeaturevalues.FloatMatrix2D FloatMatrix2D}
     * @param   minK   instance of Long
     * @param   maxK   instance of Long
     * @param   criterion   instance of String
     * @param   usepam   instance of original type "boolean" (Indicates true or false values, false = 0, true = 1 @range [0,1])
     * @param   alpha   instance of Double
     * @param   diss   instance of original type "boolean" (Indicates true or false values, false = 0, true = 1 @range [0,1])
     * @param   randomSeed   instance of Long
     * @return   instance of type {@link us.kbase.kbasefeaturevalues.EstimateKResult EstimateKResult}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public EstimateKResult estimateKNew(FloatMatrix2D matrix, Long minK, Long maxK, String criterion, Long usepam, Double alpha, Long diss, Long randomSeed) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(matrix);
        args.add(minK);
        args.add(maxK);
        args.add(criterion);
        args.add(usepam);
        args.add(alpha);
        args.add(diss);
        args.add(randomSeed);
        TypeReference<List<EstimateKResult>> retType = new TypeReference<List<EstimateKResult>>() {};
        List<EstimateKResult> res = jsonrpcCall("ClusterServiceR.estimate_k_new", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: cluster_hierarchical</p>
     * <pre>
     * Clusters features by hierarchical clustering.
     * </pre>
     * @param   matrix   instance of type {@link us.kbase.kbasefeaturevalues.FloatMatrix2D FloatMatrix2D}
     * @param   distanceMetric   instance of String
     * @param   linkageCriteria   instance of String
     * @param   heightCutoff   instance of Double
     * @param   processRows   instance of original type "boolean" (Indicates true or false values, false = 0, true = 1 @range [0,1])
     * @param   algorithm   instance of String
     * @return   instance of type {@link us.kbase.clusterservice.ClusterResults ClusterResults}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public ClusterResults clusterHierarchical(FloatMatrix2D matrix, String distanceMetric, String linkageCriteria, Double heightCutoff, Long processRows, String algorithm) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(matrix);
        args.add(distanceMetric);
        args.add(linkageCriteria);
        args.add(heightCutoff);
        args.add(processRows);
        args.add(algorithm);
        TypeReference<List<ClusterResults>> retType = new TypeReference<List<ClusterResults>>() {};
        List<ClusterResults> res = jsonrpcCall("ClusterServiceR.cluster_hierarchical", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: clusters_from_dendrogram</p>
     * <pre>
     * Given a ClusterSet with a dendogram built from a hierarchical clustering
     * method, this function creates new clusters by cutting the dendogram at
     * a specific hieght or by some other approach.
     * </pre>
     * @param   matrix   instance of type {@link us.kbase.kbasefeaturevalues.FloatMatrix2D FloatMatrix2D}
     * @param   dendrogram   instance of String
     * @param   heightCutoff   instance of Double
     * @return   instance of type {@link us.kbase.clusterservice.ClusterResults ClusterResults}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public ClusterResults clustersFromDendrogram(FloatMatrix2D matrix, String dendrogram, Double heightCutoff) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(matrix);
        args.add(dendrogram);
        args.add(heightCutoff);
        TypeReference<List<ClusterResults>> retType = new TypeReference<List<ClusterResults>>() {};
        List<ClusterResults> res = jsonrpcCall("ClusterServiceR.clusters_from_dendrogram", args, retType, true, false);
        return res.get(0);
    }

    /**
     * <p>Original spec-file function name: calc_cluster_qualities</p>
     * <pre>
     * </pre>
     * @param   matrix   instance of type {@link us.kbase.kbasefeaturevalues.FloatMatrix2D FloatMatrix2D}
     * @param   clusterLabels   instance of list of Long
     * @return   instance of type {@link us.kbase.clusterservice.ClusterResults ClusterResults}
     * @throws IOException if an IO exception occurs
     * @throws JsonClientException if a JSON RPC exception occurs
     */
    public ClusterResults calcClusterQualities(FloatMatrix2D matrix, List<Long> clusterLabels) throws IOException, JsonClientException {
        List<Object> args = new ArrayList<Object>();
        args.add(matrix);
        args.add(clusterLabels);
        TypeReference<List<ClusterResults>> retType = new TypeReference<List<ClusterResults>>() {};
        List<ClusterResults> res = jsonrpcCall("ClusterServiceR.calc_cluster_qualities", args, retType, true, false);
        return res.get(0);
    }
}
