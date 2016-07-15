
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
 * <p>Original spec-file type: TsvFileToMatrixOutput</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "output_matrix_ref"
})
public class TsvFileToMatrixOutput {

    @JsonProperty("output_matrix_ref")
    private String outputMatrixRef;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("output_matrix_ref")
    public String getOutputMatrixRef() {
        return outputMatrixRef;
    }

    @JsonProperty("output_matrix_ref")
    public void setOutputMatrixRef(String outputMatrixRef) {
        this.outputMatrixRef = outputMatrixRef;
    }

    public TsvFileToMatrixOutput withOutputMatrixRef(String outputMatrixRef) {
        this.outputMatrixRef = outputMatrixRef;
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
        return ((((("TsvFileToMatrixOutput"+" [outputMatrixRef=")+ outputMatrixRef)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
