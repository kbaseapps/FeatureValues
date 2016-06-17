
package us.kbase.kbasefeaturevalues;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: ClusterKMeansParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "k",
    "input_data",
    "n_start",
    "max_iter",
    "random_seed",
    "algorithm",
    "out_workspace",
    "out_clusterset_id"
})
public class ClusterKMeansParams {

    @JsonProperty("k")
    private Long k;
    @JsonProperty("input_data")
    private String inputData;
    @JsonProperty("n_start")
    private Long nStart;
    @JsonProperty("max_iter")
    private Long maxIter;
    @JsonProperty("random_seed")
    private Long randomSeed;
    @JsonProperty("algorithm")
    private String algorithm;
    @JsonProperty("out_workspace")
    private String outWorkspace;
    @JsonProperty("out_clusterset_id")
    private String outClustersetId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("k")
    public Long getK() {
        return k;
    }

    @JsonProperty("k")
    public void setK(Long k) {
        this.k = k;
    }

    public ClusterKMeansParams withK(Long k) {
        this.k = k;
        return this;
    }

    @JsonProperty("input_data")
    public String getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public ClusterKMeansParams withInputData(String inputData) {
        this.inputData = inputData;
        return this;
    }

    @JsonProperty("n_start")
    public Long getNStart() {
        return nStart;
    }

    @JsonProperty("n_start")
    public void setNStart(Long nStart) {
        this.nStart = nStart;
    }

    public ClusterKMeansParams withNStart(Long nStart) {
        this.nStart = nStart;
        return this;
    }

    @JsonProperty("max_iter")
    public Long getMaxIter() {
        return maxIter;
    }

    @JsonProperty("max_iter")
    public void setMaxIter(Long maxIter) {
        this.maxIter = maxIter;
    }

    public ClusterKMeansParams withMaxIter(Long maxIter) {
        this.maxIter = maxIter;
        return this;
    }

    @JsonProperty("random_seed")
    public Long getRandomSeed() {
        return randomSeed;
    }

    @JsonProperty("random_seed")
    public void setRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
    }

    public ClusterKMeansParams withRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
        return this;
    }

    @JsonProperty("algorithm")
    public String getAlgorithm() {
        return algorithm;
    }

    @JsonProperty("algorithm")
    public void setAlgorithm(String algorithm) {
        this.algorithm = algorithm;
    }

    public ClusterKMeansParams withAlgorithm(String algorithm) {
        this.algorithm = algorithm;
        return this;
    }

    @JsonProperty("out_workspace")
    public String getOutWorkspace() {
        return outWorkspace;
    }

    @JsonProperty("out_workspace")
    public void setOutWorkspace(String outWorkspace) {
        this.outWorkspace = outWorkspace;
    }

    public ClusterKMeansParams withOutWorkspace(String outWorkspace) {
        this.outWorkspace = outWorkspace;
        return this;
    }

    @JsonProperty("out_clusterset_id")
    public String getOutClustersetId() {
        return outClustersetId;
    }

    @JsonProperty("out_clusterset_id")
    public void setOutClustersetId(String outClustersetId) {
        this.outClustersetId = outClustersetId;
    }

    public ClusterKMeansParams withOutClustersetId(String outClustersetId) {
        this.outClustersetId = outClustersetId;
        return this;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public String toString() {
        return ((((((((((((((((((("ClusterKMeansParams"+" [k=")+ k)+", inputData=")+ inputData)+", nStart=")+ nStart)+", maxIter=")+ maxIter)+", randomSeed=")+ randomSeed)+", algorithm=")+ algorithm)+", outWorkspace=")+ outWorkspace)+", outClustersetId=")+ outClustersetId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
