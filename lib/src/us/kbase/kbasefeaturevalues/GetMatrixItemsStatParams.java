
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
 * <p>Original spec-file type: GetMatrixItemsStatParams</p>
 * <pre>
 * Parameters to get statics for a set of items from the Float2D type of matrices. 
 * input_data - worskapce reference to the ExpressionMatrix object (later we should allow to work with other Float2DMatrix-like matrices, e.g. fitness)
 * item_indeces_for - indeces of items FOR whch statistics should be calculated 
 * item_indeces_on - indeces of items ON whch statistics should be calculated
 * fl_indeces_on - defines whether the indeces_on should be populated in ItemStat objects. The default value = 0.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_data",
    "item_indeces_for",
    "item_indeces_on",
    "fl_indeces_on"
})
public class GetMatrixItemsStatParams {

    @JsonProperty("input_data")
    private String inputData;
    @JsonProperty("item_indeces_for")
    private List<Long> itemIndecesFor;
    @JsonProperty("item_indeces_on")
    private List<Long> itemIndecesOn;
    @JsonProperty("fl_indeces_on")
    private java.lang.Long flIndecesOn;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("input_data")
    public String getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public GetMatrixItemsStatParams withInputData(String inputData) {
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

    public GetMatrixItemsStatParams withItemIndecesFor(List<Long> itemIndecesFor) {
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

    public GetMatrixItemsStatParams withItemIndecesOn(List<Long> itemIndecesOn) {
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

    public GetMatrixItemsStatParams withFlIndecesOn(java.lang.Long flIndecesOn) {
        this.flIndecesOn = flIndecesOn;
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
        return ((((((((((("GetMatrixItemsStatParams"+" [inputData=")+ inputData)+", itemIndecesFor=")+ itemIndecesFor)+", itemIndecesOn=")+ itemIndecesOn)+", flIndecesOn=")+ flIndecesOn)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
