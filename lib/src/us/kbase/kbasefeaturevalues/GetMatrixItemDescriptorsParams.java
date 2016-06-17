
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
 * <p>Original spec-file type: GetMatrixItemDescriptorsParams</p>
 * <pre>
 * Parameters to get basic properties for items from the Float2D type of matrices. 
 * input_data - worskapce reference to the ExpressionMatrix object (later we should allow to work with other Float2DMatrix-like matrices, e.g. fitness)
 * item_indeces - indeces of items for whch descriptors should be built. Either item_indeces or item_ids should be provided. If both are provided, item_indeces will be used.
 * item_ids - ids of items for whch descriptors should be built. Either item_indeces or item_ids should be provided. If both are provided, item_indeces will be used.
 * requested_property_types - list of property types to be populated for each item. Currently supported property types are: 'function'
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_data",
    "item_indeces",
    "item_ids",
    "requested_property_types"
})
public class GetMatrixItemDescriptorsParams {

    @JsonProperty("input_data")
    private java.lang.String inputData;
    @JsonProperty("item_indeces")
    private List<Long> itemIndeces;
    @JsonProperty("item_ids")
    private List<String> itemIds;
    @JsonProperty("requested_property_types")
    private List<String> requestedPropertyTypes;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("input_data")
    public java.lang.String getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(java.lang.String inputData) {
        this.inputData = inputData;
    }

    public GetMatrixItemDescriptorsParams withInputData(java.lang.String inputData) {
        this.inputData = inputData;
        return this;
    }

    @JsonProperty("item_indeces")
    public List<Long> getItemIndeces() {
        return itemIndeces;
    }

    @JsonProperty("item_indeces")
    public void setItemIndeces(List<Long> itemIndeces) {
        this.itemIndeces = itemIndeces;
    }

    public GetMatrixItemDescriptorsParams withItemIndeces(List<Long> itemIndeces) {
        this.itemIndeces = itemIndeces;
        return this;
    }

    @JsonProperty("item_ids")
    public List<String> getItemIds() {
        return itemIds;
    }

    @JsonProperty("item_ids")
    public void setItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
    }

    public GetMatrixItemDescriptorsParams withItemIds(List<String> itemIds) {
        this.itemIds = itemIds;
        return this;
    }

    @JsonProperty("requested_property_types")
    public List<String> getRequestedPropertyTypes() {
        return requestedPropertyTypes;
    }

    @JsonProperty("requested_property_types")
    public void setRequestedPropertyTypes(List<String> requestedPropertyTypes) {
        this.requestedPropertyTypes = requestedPropertyTypes;
    }

    public GetMatrixItemDescriptorsParams withRequestedPropertyTypes(List<String> requestedPropertyTypes) {
        this.requestedPropertyTypes = requestedPropertyTypes;
        return this;
    }

    @JsonAnyGetter
    public Map<java.lang.String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperties(java.lang.String name, Object value) {
        this.additionalProperties.put(name, value);
    }

    @Override
    public java.lang.String toString() {
        return ((((((((((("GetMatrixItemDescriptorsParams"+" [inputData=")+ inputData)+", itemIndeces=")+ itemIndeces)+", itemIds=")+ itemIds)+", requestedPropertyTypes=")+ requestedPropertyTypes)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
