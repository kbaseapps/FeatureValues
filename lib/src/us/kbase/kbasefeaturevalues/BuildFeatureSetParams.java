
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
 * <p>Original spec-file type: BuildFeatureSetParams</p>
 * <pre>
 * base_feature_set - optional field,
 * description - optional field.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "genome",
    "feature_ids",
    "feature_ids_custom",
    "base_feature_set",
    "description",
    "out_workspace",
    "output_feature_set"
})
public class BuildFeatureSetParams {

    @JsonProperty("genome")
    private String genome;
    @JsonProperty("feature_ids")
    private String featureIds;
    @JsonProperty("feature_ids_custom")
    private String featureIdsCustom;
    @JsonProperty("base_feature_set")
    private String baseFeatureSet;
    @JsonProperty("description")
    private String description;
    @JsonProperty("out_workspace")
    private String outWorkspace;
    @JsonProperty("output_feature_set")
    private String outputFeatureSet;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("genome")
    public String getGenome() {
        return genome;
    }

    @JsonProperty("genome")
    public void setGenome(String genome) {
        this.genome = genome;
    }

    public BuildFeatureSetParams withGenome(String genome) {
        this.genome = genome;
        return this;
    }

    @JsonProperty("feature_ids")
    public String getFeatureIds() {
        return featureIds;
    }

    @JsonProperty("feature_ids")
    public void setFeatureIds(String featureIds) {
        this.featureIds = featureIds;
    }

    public BuildFeatureSetParams withFeatureIds(String featureIds) {
        this.featureIds = featureIds;
        return this;
    }

    @JsonProperty("feature_ids_custom")
    public String getFeatureIdsCustom() {
        return featureIdsCustom;
    }

    @JsonProperty("feature_ids_custom")
    public void setFeatureIdsCustom(String featureIdsCustom) {
        this.featureIdsCustom = featureIdsCustom;
    }

    public BuildFeatureSetParams withFeatureIdsCustom(String featureIdsCustom) {
        this.featureIdsCustom = featureIdsCustom;
        return this;
    }

    @JsonProperty("base_feature_set")
    public String getBaseFeatureSet() {
        return baseFeatureSet;
    }

    @JsonProperty("base_feature_set")
    public void setBaseFeatureSet(String baseFeatureSet) {
        this.baseFeatureSet = baseFeatureSet;
    }

    public BuildFeatureSetParams withBaseFeatureSet(String baseFeatureSet) {
        this.baseFeatureSet = baseFeatureSet;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public BuildFeatureSetParams withDescription(String description) {
        this.description = description;
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

    public BuildFeatureSetParams withOutWorkspace(String outWorkspace) {
        this.outWorkspace = outWorkspace;
        return this;
    }

    @JsonProperty("output_feature_set")
    public String getOutputFeatureSet() {
        return outputFeatureSet;
    }

    @JsonProperty("output_feature_set")
    public void setOutputFeatureSet(String outputFeatureSet) {
        this.outputFeatureSet = outputFeatureSet;
    }

    public BuildFeatureSetParams withOutputFeatureSet(String outputFeatureSet) {
        this.outputFeatureSet = outputFeatureSet;
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
        return ((((((((((((((((("BuildFeatureSetParams"+" [genome=")+ genome)+", featureIds=")+ featureIds)+", featureIdsCustom=")+ featureIdsCustom)+", baseFeatureSet=")+ baseFeatureSet)+", description=")+ description)+", outWorkspace=")+ outWorkspace)+", outputFeatureSet=")+ outputFeatureSet)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
