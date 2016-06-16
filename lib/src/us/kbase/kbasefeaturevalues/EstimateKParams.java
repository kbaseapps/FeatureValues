
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
 * <p>Original spec-file type: EstimateKParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_matrix",
    "min_k",
    "max_k",
    "max_iter",
    "random_seed",
    "neighb_size",
    "max_items",
    "out_workspace",
    "out_estimate_result"
})
public class EstimateKParams {

    @JsonProperty("input_matrix")
    private String inputMatrix;
    @JsonProperty("min_k")
    private Long minK;
    @JsonProperty("max_k")
    private Long maxK;
    @JsonProperty("max_iter")
    private Long maxIter;
    @JsonProperty("random_seed")
    private Long randomSeed;
    @JsonProperty("neighb_size")
    private Long neighbSize;
    @JsonProperty("max_items")
    private Long maxItems;
    @JsonProperty("out_workspace")
    private String outWorkspace;
    @JsonProperty("out_estimate_result")
    private String outEstimateResult;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("input_matrix")
    public String getInputMatrix() {
        return inputMatrix;
    }

    @JsonProperty("input_matrix")
    public void setInputMatrix(String inputMatrix) {
        this.inputMatrix = inputMatrix;
    }

    public EstimateKParams withInputMatrix(String inputMatrix) {
        this.inputMatrix = inputMatrix;
        return this;
    }

    @JsonProperty("min_k")
    public Long getMinK() {
        return minK;
    }

    @JsonProperty("min_k")
    public void setMinK(Long minK) {
        this.minK = minK;
    }

    public EstimateKParams withMinK(Long minK) {
        this.minK = minK;
        return this;
    }

    @JsonProperty("max_k")
    public Long getMaxK() {
        return maxK;
    }

    @JsonProperty("max_k")
    public void setMaxK(Long maxK) {
        this.maxK = maxK;
    }

    public EstimateKParams withMaxK(Long maxK) {
        this.maxK = maxK;
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

    public EstimateKParams withMaxIter(Long maxIter) {
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

    public EstimateKParams withRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
        return this;
    }

    @JsonProperty("neighb_size")
    public Long getNeighbSize() {
        return neighbSize;
    }

    @JsonProperty("neighb_size")
    public void setNeighbSize(Long neighbSize) {
        this.neighbSize = neighbSize;
    }

    public EstimateKParams withNeighbSize(Long neighbSize) {
        this.neighbSize = neighbSize;
        return this;
    }

    @JsonProperty("max_items")
    public Long getMaxItems() {
        return maxItems;
    }

    @JsonProperty("max_items")
    public void setMaxItems(Long maxItems) {
        this.maxItems = maxItems;
    }

    public EstimateKParams withMaxItems(Long maxItems) {
        this.maxItems = maxItems;
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

    public EstimateKParams withOutWorkspace(String outWorkspace) {
        this.outWorkspace = outWorkspace;
        return this;
    }

    @JsonProperty("out_estimate_result")
    public String getOutEstimateResult() {
        return outEstimateResult;
    }

    @JsonProperty("out_estimate_result")
    public void setOutEstimateResult(String outEstimateResult) {
        this.outEstimateResult = outEstimateResult;
    }

    public EstimateKParams withOutEstimateResult(String outEstimateResult) {
        this.outEstimateResult = outEstimateResult;
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
        return ((((((((((((((((((((("EstimateKParams"+" [inputMatrix=")+ inputMatrix)+", minK=")+ minK)+", maxK=")+ maxK)+", maxIter=")+ maxIter)+", randomSeed=")+ randomSeed)+", neighbSize=")+ neighbSize)+", maxItems=")+ maxItems)+", outWorkspace=")+ outWorkspace)+", outEstimateResult=")+ outEstimateResult)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
