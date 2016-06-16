
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
 * <p>Original spec-file type: ItemStat</p>
 * <pre>
 * Statistics for a given item in a collection (defined by index) , calculated on the associated vector of values. 
 * Typical example is 2D matrix: item is a given row, and correposnding values from all columns is an associated vector.   
 *             
 *             In relation to ExpressionMatrix we can think about a gene (defined by row index in Float2DMatrix) and a vector of expression 
 *             values across all (or a subset of) conditions. In this case, index_for - index of a row representing a gene in the Float2DMatrix,  
 *             indeces_on - indeces of columns represnting a set of conditions on which we want to calculate statistics. 
 *              
 *             index_for - index of the item in a collection FOR which all statitics is collected
 *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
 *             size - number of elements in the associated vector
 *             avg - mean value for a given item across all elements in the associated vector 
 *             min - min value for a given item across all elements in the associated vector 
 *             max - max value for a given item across all elements in the associated vector 
 *             std - std value for a given item across all elements in the associated vector 
 *             missing_values - number of missing values for a given item across all elements in the associated vector
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "index_for",
    "indeces_on",
    "size",
    "avg",
    "min",
    "max",
    "std",
    "missing_values"
})
public class ItemStat {

    @JsonProperty("index_for")
    private java.lang.Long indexFor;
    @JsonProperty("indeces_on")
    private List<Long> indecesOn;
    @JsonProperty("size")
    private java.lang.Long size;
    @JsonProperty("avg")
    private Double avg;
    @JsonProperty("min")
    private Double min;
    @JsonProperty("max")
    private Double max;
    @JsonProperty("std")
    private Double std;
    @JsonProperty("missing_values")
    private java.lang.Long missingValues;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("index_for")
    public java.lang.Long getIndexFor() {
        return indexFor;
    }

    @JsonProperty("index_for")
    public void setIndexFor(java.lang.Long indexFor) {
        this.indexFor = indexFor;
    }

    public ItemStat withIndexFor(java.lang.Long indexFor) {
        this.indexFor = indexFor;
        return this;
    }

    @JsonProperty("indeces_on")
    public List<Long> getIndecesOn() {
        return indecesOn;
    }

    @JsonProperty("indeces_on")
    public void setIndecesOn(List<Long> indecesOn) {
        this.indecesOn = indecesOn;
    }

    public ItemStat withIndecesOn(List<Long> indecesOn) {
        this.indecesOn = indecesOn;
        return this;
    }

    @JsonProperty("size")
    public java.lang.Long getSize() {
        return size;
    }

    @JsonProperty("size")
    public void setSize(java.lang.Long size) {
        this.size = size;
    }

    public ItemStat withSize(java.lang.Long size) {
        this.size = size;
        return this;
    }

    @JsonProperty("avg")
    public Double getAvg() {
        return avg;
    }

    @JsonProperty("avg")
    public void setAvg(Double avg) {
        this.avg = avg;
    }

    public ItemStat withAvg(Double avg) {
        this.avg = avg;
        return this;
    }

    @JsonProperty("min")
    public Double getMin() {
        return min;
    }

    @JsonProperty("min")
    public void setMin(Double min) {
        this.min = min;
    }

    public ItemStat withMin(Double min) {
        this.min = min;
        return this;
    }

    @JsonProperty("max")
    public Double getMax() {
        return max;
    }

    @JsonProperty("max")
    public void setMax(Double max) {
        this.max = max;
    }

    public ItemStat withMax(Double max) {
        this.max = max;
        return this;
    }

    @JsonProperty("std")
    public Double getStd() {
        return std;
    }

    @JsonProperty("std")
    public void setStd(Double std) {
        this.std = std;
    }

    public ItemStat withStd(Double std) {
        this.std = std;
        return this;
    }

    @JsonProperty("missing_values")
    public java.lang.Long getMissingValues() {
        return missingValues;
    }

    @JsonProperty("missing_values")
    public void setMissingValues(java.lang.Long missingValues) {
        this.missingValues = missingValues;
    }

    public ItemStat withMissingValues(java.lang.Long missingValues) {
        this.missingValues = missingValues;
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
        return ((((((((((((((((((("ItemStat"+" [indexFor=")+ indexFor)+", indecesOn=")+ indecesOn)+", size=")+ size)+", avg=")+ avg)+", min=")+ min)+", max=")+ max)+", std=")+ std)+", missingValues=")+ missingValues)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
