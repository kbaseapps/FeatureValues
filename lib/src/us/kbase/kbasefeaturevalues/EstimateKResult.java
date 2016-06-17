
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
import us.kbase.common.service.Tuple2;


/**
 * <p>Original spec-file type: EstimateKResult</p>
 * <pre>
 * note: this needs review from Marcin
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "best_k",
    "estimate_cluster_sizes"
})
public class EstimateKResult {

    @JsonProperty("best_k")
    private java.lang.Long bestK;
    @JsonProperty("estimate_cluster_sizes")
    private List<Tuple2 <Long, Double>> estimateClusterSizes;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("best_k")
    public java.lang.Long getBestK() {
        return bestK;
    }

    @JsonProperty("best_k")
    public void setBestK(java.lang.Long bestK) {
        this.bestK = bestK;
    }

    public EstimateKResult withBestK(java.lang.Long bestK) {
        this.bestK = bestK;
        return this;
    }

    @JsonProperty("estimate_cluster_sizes")
    public List<Tuple2 <Long, Double>> getEstimateClusterSizes() {
        return estimateClusterSizes;
    }

    @JsonProperty("estimate_cluster_sizes")
    public void setEstimateClusterSizes(List<Tuple2 <Long, Double>> estimateClusterSizes) {
        this.estimateClusterSizes = estimateClusterSizes;
    }

    public EstimateKResult withEstimateClusterSizes(List<Tuple2 <Long, Double>> estimateClusterSizes) {
        this.estimateClusterSizes = estimateClusterSizes;
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
        return ((((((("EstimateKResult"+" [bestK=")+ bestK)+", estimateClusterSizes=")+ estimateClusterSizes)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
