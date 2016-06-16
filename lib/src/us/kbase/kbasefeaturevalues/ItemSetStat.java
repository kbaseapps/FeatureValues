
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
 * <p>Original spec-file type: ItemSetStat</p>
 * <pre>
 * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
 *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
 *  2. No field names in json (avg, min, max, etc) for each element in the list
 *             indeces_for - indeces of items in a collection FOR which all statitics is collected
 *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
 *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
 *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
 *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
 *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
 *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
 *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "indeces_for",
    "indeces_on",
    "size",
    "avgs",
    "mins",
    "maxs",
    "stds",
    "missing_values"
})
public class ItemSetStat {

    @JsonProperty("indeces_for")
    private List<Long> indecesFor;
    @JsonProperty("indeces_on")
    private List<Long> indecesOn;
    @JsonProperty("size")
    private java.lang.Long size;
    @JsonProperty("avgs")
    private List<Double> avgs;
    @JsonProperty("mins")
    private List<Double> mins;
    @JsonProperty("maxs")
    private List<Double> maxs;
    @JsonProperty("stds")
    private List<Double> stds;
    @JsonProperty("missing_values")
    private List<Long> missingValues;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("indeces_for")
    public List<Long> getIndecesFor() {
        return indecesFor;
    }

    @JsonProperty("indeces_for")
    public void setIndecesFor(List<Long> indecesFor) {
        this.indecesFor = indecesFor;
    }

    public ItemSetStat withIndecesFor(List<Long> indecesFor) {
        this.indecesFor = indecesFor;
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

    public ItemSetStat withIndecesOn(List<Long> indecesOn) {
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

    public ItemSetStat withSize(java.lang.Long size) {
        this.size = size;
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

    public ItemSetStat withAvgs(List<Double> avgs) {
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

    public ItemSetStat withMins(List<Double> mins) {
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

    public ItemSetStat withMaxs(List<Double> maxs) {
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

    public ItemSetStat withStds(List<Double> stds) {
        this.stds = stds;
        return this;
    }

    @JsonProperty("missing_values")
    public List<Long> getMissingValues() {
        return missingValues;
    }

    @JsonProperty("missing_values")
    public void setMissingValues(List<Long> missingValues) {
        this.missingValues = missingValues;
    }

    public ItemSetStat withMissingValues(List<Long> missingValues) {
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
        return ((((((((((((((((((("ItemSetStat"+" [indecesFor=")+ indecesFor)+", indecesOn=")+ indecesOn)+", size=")+ size)+", avgs=")+ avgs)+", mins=")+ mins)+", maxs=")+ maxs)+", stds=")+ stds)+", missingValues=")+ missingValues)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
