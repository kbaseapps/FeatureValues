
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
 * <p>Original spec-file type: ReconnectMatrixToGenomeParams</p>
 * <pre>
 * out_matrix_id - optional target matrix object name (if not specified 
 *     then target object overwrites input_data).
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_data",
    "genome_ref",
    "out_workspace",
    "out_matrix_id"
})
public class ReconnectMatrixToGenomeParams {

    @JsonProperty("input_data")
    private String inputData;
    @JsonProperty("genome_ref")
    private String genomeRef;
    @JsonProperty("out_workspace")
    private String outWorkspace;
    @JsonProperty("out_matrix_id")
    private String outMatrixId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("input_data")
    public String getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(String inputData) {
        this.inputData = inputData;
    }

    public ReconnectMatrixToGenomeParams withInputData(String inputData) {
        this.inputData = inputData;
        return this;
    }

    @JsonProperty("genome_ref")
    public String getGenomeRef() {
        return genomeRef;
    }

    @JsonProperty("genome_ref")
    public void setGenomeRef(String genomeRef) {
        this.genomeRef = genomeRef;
    }

    public ReconnectMatrixToGenomeParams withGenomeRef(String genomeRef) {
        this.genomeRef = genomeRef;
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

    public ReconnectMatrixToGenomeParams withOutWorkspace(String outWorkspace) {
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

    public ReconnectMatrixToGenomeParams withOutMatrixId(String outMatrixId) {
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
        return ((((((((((("ReconnectMatrixToGenomeParams"+" [inputData=")+ inputData)+", genomeRef=")+ genomeRef)+", outWorkspace=")+ outWorkspace)+", outMatrixId=")+ outMatrixId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
