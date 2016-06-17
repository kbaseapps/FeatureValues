
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
 * <p>Original spec-file type: AnalysisReport</p>
 * <pre>
 * A basic report object used for a variety of cases to mark informational
 * messages, warnings, and errors related to processing or quality control
 * checks of raw data.
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "checkTypeDetected",
    "checkUsed",
    "checkDescriptions",
    "checkResults",
    "messages",
    "warnings",
    "errors"
})
public class AnalysisReport {

    @JsonProperty("checkTypeDetected")
    private java.lang.String checkTypeDetected;
    @JsonProperty("checkUsed")
    private java.lang.String checkUsed;
    @JsonProperty("checkDescriptions")
    private List<String> checkDescriptions;
    @JsonProperty("checkResults")
    private List<Long> checkResults;
    @JsonProperty("messages")
    private List<String> messages;
    @JsonProperty("warnings")
    private List<String> warnings;
    @JsonProperty("errors")
    private List<String> errors;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("checkTypeDetected")
    public java.lang.String getCheckTypeDetected() {
        return checkTypeDetected;
    }

    @JsonProperty("checkTypeDetected")
    public void setCheckTypeDetected(java.lang.String checkTypeDetected) {
        this.checkTypeDetected = checkTypeDetected;
    }

    public AnalysisReport withCheckTypeDetected(java.lang.String checkTypeDetected) {
        this.checkTypeDetected = checkTypeDetected;
        return this;
    }

    @JsonProperty("checkUsed")
    public java.lang.String getCheckUsed() {
        return checkUsed;
    }

    @JsonProperty("checkUsed")
    public void setCheckUsed(java.lang.String checkUsed) {
        this.checkUsed = checkUsed;
    }

    public AnalysisReport withCheckUsed(java.lang.String checkUsed) {
        this.checkUsed = checkUsed;
        return this;
    }

    @JsonProperty("checkDescriptions")
    public List<String> getCheckDescriptions() {
        return checkDescriptions;
    }

    @JsonProperty("checkDescriptions")
    public void setCheckDescriptions(List<String> checkDescriptions) {
        this.checkDescriptions = checkDescriptions;
    }

    public AnalysisReport withCheckDescriptions(List<String> checkDescriptions) {
        this.checkDescriptions = checkDescriptions;
        return this;
    }

    @JsonProperty("checkResults")
    public List<Long> getCheckResults() {
        return checkResults;
    }

    @JsonProperty("checkResults")
    public void setCheckResults(List<Long> checkResults) {
        this.checkResults = checkResults;
    }

    public AnalysisReport withCheckResults(List<Long> checkResults) {
        this.checkResults = checkResults;
        return this;
    }

    @JsonProperty("messages")
    public List<String> getMessages() {
        return messages;
    }

    @JsonProperty("messages")
    public void setMessages(List<String> messages) {
        this.messages = messages;
    }

    public AnalysisReport withMessages(List<String> messages) {
        this.messages = messages;
        return this;
    }

    @JsonProperty("warnings")
    public List<String> getWarnings() {
        return warnings;
    }

    @JsonProperty("warnings")
    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public AnalysisReport withWarnings(List<String> warnings) {
        this.warnings = warnings;
        return this;
    }

    @JsonProperty("errors")
    public List<String> getErrors() {
        return errors;
    }

    @JsonProperty("errors")
    public void setErrors(List<String> errors) {
        this.errors = errors;
    }

    public AnalysisReport withErrors(List<String> errors) {
        this.errors = errors;
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
        return ((((((((((((((((("AnalysisReport"+" [checkTypeDetected=")+ checkTypeDetected)+", checkUsed=")+ checkUsed)+", checkDescriptions=")+ checkDescriptions)+", checkResults=")+ checkResults)+", messages=")+ messages)+", warnings=")+ warnings)+", errors=")+ errors)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
