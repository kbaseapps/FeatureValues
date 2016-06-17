
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
 * <p>Original spec-file type: EstimateKParamsNew</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_matrix",
    "min_k",
    "max_k",
    "criterion",
    "usepam",
    "alpha",
    "diss",
    "random_seed",
    "out_workspace",
    "out_estimate_result"
})
public class EstimateKParamsNew {

    @JsonProperty("input_matrix")
    private String inputMatrix;
    @JsonProperty("min_k")
    private Long minK;
    @JsonProperty("max_k")
    private Long maxK;
    @JsonProperty("criterion")
    private String criterion;
    @JsonProperty("usepam")
    private Long usepam;
    @JsonProperty("alpha")
    private Double alpha;
    @JsonProperty("diss")
    private Long diss;
    @JsonProperty("random_seed")
    private Long randomSeed;
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

    public EstimateKParamsNew withInputMatrix(String inputMatrix) {
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

    public EstimateKParamsNew withMinK(Long minK) {
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

    public EstimateKParamsNew withMaxK(Long maxK) {
        this.maxK = maxK;
        return this;
    }

    @JsonProperty("criterion")
    public String getCriterion() {
        return criterion;
    }

    @JsonProperty("criterion")
    public void setCriterion(String criterion) {
        this.criterion = criterion;
    }

    public EstimateKParamsNew withCriterion(String criterion) {
        this.criterion = criterion;
        return this;
    }

    @JsonProperty("usepam")
    public Long getUsepam() {
        return usepam;
    }

    @JsonProperty("usepam")
    public void setUsepam(Long usepam) {
        this.usepam = usepam;
    }

    public EstimateKParamsNew withUsepam(Long usepam) {
        this.usepam = usepam;
        return this;
    }

    @JsonProperty("alpha")
    public Double getAlpha() {
        return alpha;
    }

    @JsonProperty("alpha")
    public void setAlpha(Double alpha) {
        this.alpha = alpha;
    }

    public EstimateKParamsNew withAlpha(Double alpha) {
        this.alpha = alpha;
        return this;
    }

    @JsonProperty("diss")
    public Long getDiss() {
        return diss;
    }

    @JsonProperty("diss")
    public void setDiss(Long diss) {
        this.diss = diss;
    }

    public EstimateKParamsNew withDiss(Long diss) {
        this.diss = diss;
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

    public EstimateKParamsNew withRandomSeed(Long randomSeed) {
        this.randomSeed = randomSeed;
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

    public EstimateKParamsNew withOutWorkspace(String outWorkspace) {
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

    public EstimateKParamsNew withOutEstimateResult(String outEstimateResult) {
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
        return ((((((((((((((((((((((("EstimateKParamsNew"+" [inputMatrix=")+ inputMatrix)+", minK=")+ minK)+", maxK=")+ maxK)+", criterion=")+ criterion)+", usepam=")+ usepam)+", alpha=")+ alpha)+", diss=")+ diss)+", randomSeed=")+ randomSeed)+", outWorkspace=")+ outWorkspace)+", outEstimateResult=")+ outEstimateResult)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
