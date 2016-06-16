
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
 * <p>Original spec-file type: GetMatrixSetsStatParams</p>
 * <pre>
 * Parameters to retrieve statistics for set of sets of elements. 
 * In relation to ExpressionMatrix, these parameters can be used to retrive sparklines for several gene clusters generated on the 
 * same ExpressionMatrix in one call.  
 * params - list of params to retrive statistics for a set of items from the Float2D type of matrices.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "params"
})
public class GetMatrixSetsStatParams {

    @JsonProperty("params")
    private List<GetMatrixSetStatParams> params;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("params")
    public List<GetMatrixSetStatParams> getParams() {
        return params;
    }

    @JsonProperty("params")
    public void setParams(List<GetMatrixSetStatParams> params) {
        this.params = params;
    }

    public GetMatrixSetsStatParams withParams(List<GetMatrixSetStatParams> params) {
        this.params = params;
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
        return ((((("GetMatrixSetsStatParams"+" [params=")+ params)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
