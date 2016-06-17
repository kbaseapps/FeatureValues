
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
 * <p>Original spec-file type: ClustersFromDendrogramParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "feature_height_cutoff",
    "condition_height_cutoff",
    "input_data",
    "out_workspace",
    "out_clusterset_id"
})
public class ClustersFromDendrogramParams {

    @JsonProperty("feature_height_cutoff")
    private Double featureHeightCutoff;
    @JsonProperty("condition_height_cutoff")
    private Double conditionHeightCutoff;
    @JsonProperty("input_data")
    private String inputData;
    @JsonProperty("out_workspace")
    private String outWorkspace;
    @JsonProperty("out_clusterset_id")
    private String outClustersetId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("feature_height_cutoff")
    public Double getFeatureHeightCutoff() {
        return featureHeightCutoff;
    }

    @JsonProperty("feature_height_cutoff")
    public void setFeatureHeightCutoff(Double featureHeightCutoff) {
        this.featureHeightCutoff = featureHeightCutoff;
    }

    public ClustersFromDendrogramParams withFeatureHeightCutoff(Double featureHeightCutoff) {
        this.featureHeightCutoff = featureHeightCutoff;
        return this;
    }

    @JsonProperty("condition_height_cutoff")
    public Double getConditionHeightCutoff() {
        return conditionHeightCutoff;
    }

    @JsonProperty("condition_height_cutoff")
    public void setConditionHeightCutoff(Double conditionHeightCutoff) {
        this.conditionHeightCutoff = conditionHeightCutoff;
    }

    public ClustersFromDendrogramParams withConditionHeightCutoff(Double conditionHeightCutoff) {
        this.conditionHeightCutoff = conditionHeightCutoff;
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

    public ClustersFromDendrogramParams withInputData(String inputData) {
        this.inputData = inputData;
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

    public ClustersFromDendrogramParams withOutWorkspace(String outWorkspace) {
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

    public ClustersFromDendrogramParams withOutClustersetId(String outClustersetId) {
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
        return ((((((((((((("ClustersFromDendrogramParams"+" [featureHeightCutoff=")+ featureHeightCutoff)+", conditionHeightCutoff=")+ conditionHeightCutoff)+", inputData=")+ inputData)+", outWorkspace=")+ outWorkspace)+", outClustersetId=")+ outClustersetId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
