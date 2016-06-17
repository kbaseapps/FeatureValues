
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
 * <p>Original spec-file type: GetMatrixSetStatParams</p>
 * <pre>
 * Parameters to get statistics for a set of items from the Float2D type of matrices in a form of ItemSetStat. 
 * This version is more flexible and will be later used to retrieve set of sets of elements.                  
 *             
 *             input_data - worskapce reference to the ExpressionMatrix object (later we should allow to work with other Float2DMatrix-like matrices, e.g. fitness)
 *             item_indeces_for - indeces of items FOR wich statistics should be calculated 
 *             item_indeces_on - indeces of items ON wich statistics should be calculated
 *             fl_indeces_on - defines whether the indeces_on should be populated in SetStat objects. The default value = 0. 
 *             fl_indeces_for - defines whether the indeces_for should be populated in SetStat objects. The default value = 0.             
 *             fl_avgs - defines whether the avgs should be populated in SetStat objects. The default value = 0. 
 *             fl_mins - defines whether the mins should be populated in SetStat objects. The default value = 0. 
 *             fl_maxs - defines whether the maxs should be populated in SetStat objects. The default value = 0. 
 *             fl_stds - defines whether the stds should be populated in SetStat objects. The default value = 0. 
 *             fl_missing_values - defines whether the missing_values should be populated in SetStat objects. The default value = 0.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_data",
    "item_indeces_for",
    "item_indeces_on",
    "fl_indeces_on",
    "fl_indeces_for",
    "fl_avgs",
    "fl_mins",
    "fl_maxs",
    "fl_stds",
    "fl_missing_values"
})
public class GetMatrixSetStatParams {

    @JsonProperty("input_data")
    private String inputData;
    @JsonProperty("item_indeces_for")
    private List<Long> itemIndecesFor;
    @JsonProperty("item_indeces_on")
    private List<Long> itemIndecesOn;
    @JsonProperty("fl_indeces_on")
    private java.lang.Long flIndecesOn;
    @JsonProperty("fl_indeces_for")
    private java.lang.Long flIndecesFor;
    @JsonProperty("fl_avgs")
    private java.lang.Long flAvgs;
    @JsonProperty("fl_mins")
    private java.lang.Long flMins;
    @JsonProperty("fl_maxs")
    private java.lang.Long flMaxs;
    @JsonProperty("fl_stds")
    private java.lang.Long flStds;
    @JsonProperty("fl_missing_values")
    private java.lang.Long flMissingValues;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("input_data")
    public String getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public GetMatrixSetStatParams withInputData(String inputData) {
        this.inputData = inputData;
        return this;
    }

    @JsonProperty("item_indeces_for")
    public List<Long> getItemIndecesFor() {
        return itemIndecesFor;
    }

    @JsonProperty("item_indeces_for")
    public void setItemIndecesFor(List<Long> itemIndecesFor) {
        this.itemIndecesFor = itemIndecesFor;
    }

    public GetMatrixSetStatParams withItemIndecesFor(List<Long> itemIndecesFor) {
        this.itemIndecesFor = itemIndecesFor;
        return this;
    }

    @JsonProperty("item_indeces_on")
    public List<Long> getItemIndecesOn() {
        return itemIndecesOn;
    }

    @JsonProperty("item_indeces_on")
    public void setItemIndecesOn(List<Long> itemIndecesOn) {
        this.itemIndecesOn = itemIndecesOn;
    }

    public GetMatrixSetStatParams withItemIndecesOn(List<Long> itemIndecesOn) {
        this.itemIndecesOn = itemIndecesOn;
        return this;
    }

    @JsonProperty("fl_indeces_on")
    public java.lang.Long getFlIndecesOn() {
        return flIndecesOn;
    }

    @JsonProperty("fl_indeces_on")
    public void setFlIndecesOn(java.lang.Long flIndecesOn) {
        this.flIndecesOn = flIndecesOn;
    }

    public GetMatrixSetStatParams withFlIndecesOn(java.lang.Long flIndecesOn) {
        this.flIndecesOn = flIndecesOn;
        return this;
    }

    @JsonProperty("fl_indeces_for")
    public java.lang.Long getFlIndecesFor() {
        return flIndecesFor;
    }

    @JsonProperty("fl_indeces_for")
    public void setFlIndecesFor(java.lang.Long flIndecesFor) {
        this.flIndecesFor = flIndecesFor;
    }

    public GetMatrixSetStatParams withFlIndecesFor(java.lang.Long flIndecesFor) {
        this.flIndecesFor = flIndecesFor;
        return this;
    }

    @JsonProperty("fl_avgs")
    public java.lang.Long getFlAvgs() {
        return flAvgs;
    }

    @JsonProperty("fl_avgs")
    public void setFlAvgs(java.lang.Long flAvgs) {
        this.flAvgs = flAvgs;
    }

    public GetMatrixSetStatParams withFlAvgs(java.lang.Long flAvgs) {
        this.flAvgs = flAvgs;
        return this;
    }

    @JsonProperty("fl_mins")
    public java.lang.Long getFlMins() {
        return flMins;
    }

    @JsonProperty("fl_mins")
    public void setFlMins(java.lang.Long flMins) {
        this.flMins = flMins;
    }

    public GetMatrixSetStatParams withFlMins(java.lang.Long flMins) {
        this.flMins = flMins;
        return this;
    }

    @JsonProperty("fl_maxs")
    public java.lang.Long getFlMaxs() {
        return flMaxs;
    }

    @JsonProperty("fl_maxs")
    public void setFlMaxs(java.lang.Long flMaxs) {
        this.flMaxs = flMaxs;
    }

    public GetMatrixSetStatParams withFlMaxs(java.lang.Long flMaxs) {
        this.flMaxs = flMaxs;
        return this;
    }

    @JsonProperty("fl_stds")
    public java.lang.Long getFlStds() {
        return flStds;
    }

    @JsonProperty("fl_stds")
    public void setFlStds(java.lang.Long flStds) {
        this.flStds = flStds;
    }

    public GetMatrixSetStatParams withFlStds(java.lang.Long flStds) {
        this.flStds = flStds;
        return this;
    }

    @JsonProperty("fl_missing_values")
    public java.lang.Long getFlMissingValues() {
        return flMissingValues;
    }

    @JsonProperty("fl_missing_values")
    public void setFlMissingValues(java.lang.Long flMissingValues) {
        this.flMissingValues = flMissingValues;
    }

    public GetMatrixSetStatParams withFlMissingValues(java.lang.Long flMissingValues) {
        this.flMissingValues = flMissingValues;
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
        return ((((((((((((((((((((((("GetMatrixSetStatParams"+" [inputData=")+ inputData)+", itemIndecesFor=")+ itemIndecesFor)+", itemIndecesOn=")+ itemIndecesOn)+", flIndecesOn=")+ flIndecesOn)+", flIndecesFor=")+ flIndecesFor)+", flAvgs=")+ flAvgs)+", flMins=")+ flMins)+", flMaxs=")+ flMaxs)+", flStds=")+ flStds)+", flMissingValues=")+ flMissingValues)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
