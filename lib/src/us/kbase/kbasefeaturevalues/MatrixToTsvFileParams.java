
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
 * <p>Original spec-file type: MatrixToTsvFileParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_ref",
    "to_shock",
    "file_path"
})
public class MatrixToTsvFileParams {

    @JsonProperty("input_ref")
    private String inputRef;
    @JsonProperty("to_shock")
    private Long toShock;
    @JsonProperty("file_path")
    private String filePath;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("input_ref")
    public String getInputRef() {
        return inputRef;
    }

    @JsonProperty("input_ref")
    public void setInputRef(String inputRef) {
        this.inputRef = inputRef;
    }

    public MatrixToTsvFileParams withInputRef(String inputRef) {
        this.inputRef = inputRef;
        return this;
    }

    @JsonProperty("to_shock")
    public Long getToShock() {
        return toShock;
    }

    @JsonProperty("to_shock")
    public void setToShock(Long toShock) {
        this.toShock = toShock;
    }

    public MatrixToTsvFileParams withToShock(Long toShock) {
        this.toShock = toShock;
        return this;
    }

    @JsonProperty("file_path")
    public String getFilePath() {
        return filePath;
    }

    @JsonProperty("file_path")
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public MatrixToTsvFileParams withFilePath(String filePath) {
        this.filePath = filePath;
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
        return ((((((((("MatrixToTsvFileParams"+" [inputRef=")+ inputRef)+", toShock=")+ toShock)+", filePath=")+ filePath)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
