
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
 * <p>Original spec-file type: PairwiseComparison</p>
 * <pre>
 * To represent a pairwise comparison of several elements defined by 'indeces'.  
 * This data type can be used to model represent pairwise correlation of expression profiles for a set of genes.                 
 * indeces - indeces of elements to be compared
 * comparison_values - values representing a parituclar type of comparison between elements. 
 *         Expected to be symmetric: comparison_values[i][j] = comparison_values[j][i].
 *         Diagonal values: comparison_values[i][i] = 0
 *         
 * avgs - mean of comparison_values for each element        
 * mins - min of comparison_values for each element
 * maxs - max of comparison_values for each element
 * stds - std of comparison_values for each element
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "indeces",
    "comparison_values",
    "avgs",
    "mins",
    "maxs",
    "stds"
})
public class PairwiseComparison {

    @JsonProperty("indeces")
    private List<Long> indeces;
    @JsonProperty("comparison_values")
    private List<List<Double>> comparisonValues;
    @JsonProperty("avgs")
    private List<Double> avgs;
    @JsonProperty("mins")
    private List<Double> mins;
    @JsonProperty("maxs")
    private List<Double> maxs;
    @JsonProperty("stds")
    private List<Double> stds;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("indeces")
    public List<Long> getIndeces() {
        return indeces;
    }

    @JsonProperty("indeces")
    public void setIndeces(List<Long> indeces) {
        this.indeces = indeces;
    }

    public PairwiseComparison withIndeces(List<Long> indeces) {
        this.indeces = indeces;
        return this;
    }

    @JsonProperty("comparison_values")
    public List<List<Double>> getComparisonValues() {
        return comparisonValues;
    }

    @JsonProperty("comparison_values")
    public void setComparisonValues(List<List<Double>> comparisonValues) {
        this.comparisonValues = comparisonValues;
    }

    public PairwiseComparison withComparisonValues(List<List<Double>> comparisonValues) {
        this.comparisonValues = comparisonValues;
        return this;
    }

    @JsonProperty("avgs")
    public List<Double> getAvgs() {
        return avgs;
    }

    @JsonProperty("avgs")
    public void setAvgs(List<Double> avgs) {
        this.avgs = avgs;
    }

    public PairwiseComparison withAvgs(List<Double> avgs) {
        this.avgs = avgs;
        return this;
    }

    @JsonProperty("mins")
    public List<Double> getMins() {
        return mins;
    }

    @JsonProperty("mins")
    public void setMins(List<Double> mins) {
        this.mins = mins;
    }

    public PairwiseComparison withMins(List<Double> mins) {
        this.mins = mins;
        return this;
    }

    @JsonProperty("maxs")
    public List<Double> getMaxs() {
        return maxs;
    }

    @JsonProperty("maxs")
    public void setMaxs(List<Double> maxs) {
        this.maxs = maxs;
    }

    public PairwiseComparison withMaxs(List<Double> maxs) {
        this.maxs = maxs;
        return this;
    }

    @JsonProperty("stds")
    public List<Double> getStds() {
        return stds;
    }

    @JsonProperty("stds")
    public void setStds(List<Double> stds) {
        this.stds = stds;
    }

    public PairwiseComparison withStds(List<Double> stds) {
        this.stds = stds;
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
        return ((((((((((((((("PairwiseComparison"+" [indeces=")+ indeces)+", comparisonValues=")+ comparisonValues)+", avgs=")+ avgs)+", mins=")+ mins)+", maxs=")+ maxs)+", stds=")+ stds)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
