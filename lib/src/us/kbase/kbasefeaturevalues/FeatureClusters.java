
package us.kbase.kbasefeaturevalues;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * <p>Original spec-file type: FeatureClusters</p>
 * <pre>
 * A set of clusters, typically generated for a Float2DMatrix wrapper, such as Expression
 * data or single feature knockout fitness data.
 * feature_clusters - list of labeled feature clusters
 * condition_clusters - (optional) list of labeled condition clusters
 * feature_dendrogram - (optional) maybe output from hierchical clustering approaches
 * condition_dendogram - (optional) maybe output from hierchical clustering approaches
 * original_data - pointer to the original data used to make this cluster set
 * report - information collected during cluster construction.
 * @metadata ws original_data as source_data_ref
 * @metadata ws length(feature_clusters) as n_feature_clusters
 * @metadata ws length(condition_clusters) as n_condition_clusters
 * @optional condition_clusters 
 * @optional feature_dendrogram condition_dendrogram
 * @optional original_data report
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "feature_clusters",
    "condition_clusters",
    "feature_dendrogram",
    "condition_dendrogram",
    "original_data",
    "report"
})
public class FeatureClusters {

    @JsonProperty("feature_clusters")
    private List<us.kbase.kbasefeaturevalues.LabeledCluster> featureClusters;
    @JsonProperty("condition_clusters")
    private List<us.kbase.kbasefeaturevalues.LabeledCluster> conditionClusters;
    @JsonProperty("feature_dendrogram")
    private String featureDendrogram;
    @JsonProperty("condition_dendrogram")
    private String conditionDendrogram;
    @JsonProperty("original_data")
    private String originalData;
    /**
     * <p>Original spec-file type: AnalysisReport</p>
     * <pre>
     * A basic report object used for a variety of cases to mark informational
     * messages, warnings, and errors related to processing or quality control
     * checks of raw data.
     * </pre>
     * 
     */
    @JsonProperty("report")
    private AnalysisReport report;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("feature_clusters")
    public List<us.kbase.kbasefeaturevalues.LabeledCluster> getFeatureClusters() {
        return featureClusters;
    }

    @JsonProperty("feature_clusters")
    public void setFeatureClusters(List<us.kbase.kbasefeaturevalues.LabeledCluster> featureClusters) {
        this.featureClusters = featureClusters;
    }

    public FeatureClusters withFeatureClusters(List<us.kbase.kbasefeaturevalues.LabeledCluster> featureClusters) {
        this.featureClusters = featureClusters;
        return this;
    }

    @JsonProperty("condition_clusters")
    public List<us.kbase.kbasefeaturevalues.LabeledCluster> getConditionClusters() {
        return conditionClusters;
    }

    @JsonProperty("condition_clusters")
    public void setConditionClusters(List<us.kbase.kbasefeaturevalues.LabeledCluster> conditionClusters) {
        this.conditionClusters = conditionClusters;
    }

    public FeatureClusters withConditionClusters(List<us.kbase.kbasefeaturevalues.LabeledCluster> conditionClusters) {
        this.conditionClusters = conditionClusters;
        return this;
    }

    @JsonProperty("feature_dendrogram")
    public String getFeatureDendrogram() {
        return featureDendrogram;
    }

    @JsonProperty("feature_dendrogram")
    public void setFeatureDendrogram(String featureDendrogram) {
        this.featureDendrogram = featureDendrogram;
    }

    public FeatureClusters withFeatureDendrogram(String featureDendrogram) {
        this.featureDendrogram = featureDendrogram;
        return this;
    }

    @JsonProperty("condition_dendrogram")
    public String getConditionDendrogram() {
        return conditionDendrogram;
    }

    @JsonProperty("condition_dendrogram")
    public void setConditionDendrogram(String conditionDendrogram) {
        this.conditionDendrogram = conditionDendrogram;
    }

    public FeatureClusters withConditionDendrogram(String conditionDendrogram) {
        this.conditionDendrogram = conditionDendrogram;
        return this;
    }

    @JsonProperty("original_data")
    public String getOriginalData() {
        return originalData;
    }

    @JsonProperty("original_data")
    public void setOriginalData(String originalData) {
        this.originalData = originalData;
    }

    public FeatureClusters withOriginalData(String originalData) {
        this.originalData = originalData;
        return this;
    }

    /**
     * <p>Original spec-file type: AnalysisReport</p>
     * <pre>
     * A basic report object used for a variety of cases to mark informational
     * messages, warnings, and errors related to processing or quality control
     * checks of raw data.
     * </pre>
     * 
     */
    @JsonProperty("report")
    public AnalysisReport getReport() {
        return report;
    }

    /**
     * <p>Original spec-file type: AnalysisReport</p>
     * <pre>
     * A basic report object used for a variety of cases to mark informational
     * messages, warnings, and errors related to processing or quality control
     * checks of raw data.
     * </pre>
     * 
     */
    @JsonProperty("report")
    public void setReport(AnalysisReport report) {
        this.report = report;
    }

    public FeatureClusters withReport(AnalysisReport report) {
        this.report = report;
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
        return ((((((((((((((("FeatureClusters"+" [featureClusters=")+ featureClusters)+", conditionClusters=")+ conditionClusters)+", featureDendrogram=")+ featureDendrogram)+", conditionDendrogram=")+ conditionDendrogram)+", originalData=")+ originalData)+", report=")+ report)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
