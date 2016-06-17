
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
 * <p>Original spec-file type: CorrectMatrixParams</p>
 * <pre>
 * transform_type - type of matrix change (one of: add, multiply,
 *     normalize, missing, ?).
 * transform_value - optional field defining volume of change if
 *     it's necessary for chosen transform_type.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "transform_type",
    "transform_value",
    "input_data",
    "out_workspace",
    "out_matrix_id"
})
public class CorrectMatrixParams {

    @JsonProperty("transform_type")
    private String transformType;
    @JsonProperty("transform_value")
    private Double transformValue;
    @JsonProperty("input_data")
    private String inputData;
    @JsonProperty("out_workspace")
    private String outWorkspace;
    @JsonProperty("out_matrix_id")
    private String outMatrixId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("transform_type")
    public String getTransformType() {
        return transformType;
    }

    @JsonProperty("transform_type")
    public void setTransformType(String transformType) {
        this.transformType = transformType;
    }

    public CorrectMatrixParams withTransformType(String transformType) {
        this.transformType = transformType;
        return this;
    }

    @JsonProperty("transform_value")
    public Double getTransformValue() {
        return transformValue;
    }

    @JsonProperty("transform_value")
    public void setTransformValue(Double transformValue) {
        this.transformValue = transformValue;
    }

    public CorrectMatrixParams withTransformValue(Double transformValue) {
        this.transformValue = transformValue;
        return this;
    }

    @JsonProperty("input_data")
    public String getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public CorrectMatrixParams withInputData(String inputData) {
        this.inputData = inputData;
        return this;
    }

    @JsonProperty("out_workspace")
    public String getOutWorkspace() {
        return outWorkspace;
    }

    @JsonProperty("out_workspace")
    public void setOutWorkspace(String outWorkspace) {
        this.outWorkspace = outWorkspace;
    }

    public CorrectMatrixParams withOutWorkspace(String outWorkspace) {
        this.outWorkspace = outWorkspace;
        return this;
    }

    @JsonProperty("out_matrix_id")
    public String getOutMatrixId() {
        return outMatrixId;
    }

    @JsonProperty("out_matrix_id")
    public void setOutMatrixId(String outMatrixId) {
        this.outMatrixId = outMatrixId;
    }

    public CorrectMatrixParams withOutMatrixId(String outMatrixId) {
        this.outMatrixId = outMatrixId;
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
        return ((((((((((((("CorrectMatrixParams"+" [transformType=")+ transformType)+", transformValue=")+ transformValue)+", inputData=")+ inputData)+", outWorkspace=")+ outWorkspace)+", outMatrixId=")+ outMatrixId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
