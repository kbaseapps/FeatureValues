
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
 * <p>Original spec-file type: ClusterHierarchicalParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "distance_metric",
    "linkage_criteria",
    "feature_height_cutoff",
    "condition_height_cutoff",
    "max_items",
    "input_data",
    "algorithm",
    "out_workspace",
    "out_clusterset_id"
})
public class ClusterHierarchicalParams {

    @JsonProperty("distance_metric")
    private String distanceMetric;
    @JsonProperty("linkage_criteria")
    private String linkageCriteria;
    @JsonProperty("feature_height_cutoff")
    private Double featureHeightCutoff;
    @JsonProperty("condition_height_cutoff")
    private Double conditionHeightCutoff;
    @JsonProperty("max_items")
    private Long maxItems;
    @JsonProperty("input_data")
    private String inputData;
    @JsonProperty("algorithm")
    private String algorithm;
    @JsonProperty("out_workspace")
    private String outWorkspace;
    @JsonProperty("out_clusterset_id")
    private String outClustersetId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("distance_metric")
    public String getDistanceMetric() {
        return distanceMetric;
    }

    @JsonProperty("distance_metric")
    public void setDistanceMetric(String distanceMetric) {
        this.distanceMetric = distanceMetric;
    }

    public ClusterHierarchicalParams withDistanceMetric(String distanceMetric) {
        this.distanceMetric = distanceMetric;
        return this;
    }

    @JsonProperty("linkage_criteria")
    public String getLinkageCriteria() {
        return linkageCriteria;
    }

    @JsonProperty("linkage_criteria")
    public void setLinkageCriteria(String linkageCriteria) {
        this.linkageCriteria = linkageCriteria;
    }

    public ClusterHierarchicalParams withLinkageCriteria(String linkageCriteria) {
        this.linkageCriteria = linkageCriteria;
        return this;
    }

    @JsonProperty("feature_height_cutoff")
    public Double getFeatureHeightCutoff() {
        return featureHeightCutoff;
    }

    @JsonProperty("feature_height_cutoff")
    public void setFeatureHeightCutoff(Double featureHeightCutoff) {
        this.featureHeightCutoff = featureHeightCutoff;
    }

    public ClusterHierarchicalParams withFeatureHeightCutoff(Double featureHeightCutoff) {
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

    public ClusterHierarchicalParams withConditionHeightCutoff(Double conditionHeightCutoff) {
        this.conditionHeightCutoff = conditionHeightCutoff;
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

    public ClusterHierarchicalParams withMaxItems(Long maxItems) {
        this.maxItems = maxItems;
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

    public ClusterHierarchicalParams withInputData(String inputData) {
        this.inputData = inputData;
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

    public ClusterHierarchicalParams withAlgorithm(String algorithm) {
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

    public ClusterHierarchicalParams withOutWorkspace(String outWorkspace) {
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

    public ClusterHierarchicalParams withOutClustersetId(String outClustersetId) {
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
        return ((((((((((((((((((((("ClusterHierarchicalParams"+" [distanceMetric=")+ distanceMetric)+", linkageCriteria=")+ linkageCriteria)+", featureHeightCutoff=")+ featureHeightCutoff)+", conditionHeightCutoff=")+ conditionHeightCutoff)+", maxItems=")+ maxItems)+", inputData=")+ inputData)+", algorithm=")+ algorithm)+", outWorkspace=")+ outWorkspace)+", outClustersetId=")+ outClustersetId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
