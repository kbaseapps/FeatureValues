
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
 * <p>Original spec-file type: EvaluateClustersetQualityParams</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_clusterset",
    "out_workspace",
    "out_report_id"
})
public class EvaluateClustersetQualityParams {

    @JsonProperty("input_clusterset")
    private String inputClusterset;
    @JsonProperty("out_workspace")
    private String outWorkspace;
    @JsonProperty("out_report_id")
    private String outReportId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("input_clusterset")
    public String getInputClusterset() {
        return inputClusterset;
    }

    @JsonProperty("input_clusterset")
    public void setInputClusterset(String inputClusterset) {
        this.inputClusterset = inputClusterset;
    }

    public EvaluateClustersetQualityParams withInputClusterset(String inputClusterset) {
        this.inputClusterset = inputClusterset;
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

    public EvaluateClustersetQualityParams withOutWorkspace(String outWorkspace) {
        this.outWorkspace = outWorkspace;
        return this;
    }

    @JsonProperty("out_report_id")
    public String getOutReportId() {
        return outReportId;
    }

    @JsonProperty("out_report_id")
    public void setOutReportId(String outReportId) {
        this.outReportId = outReportId;
    }

    public EvaluateClustersetQualityParams withOutReportId(String outReportId) {
        this.outReportId = outReportId;
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
        return ((((((((("EvaluateClustersetQualityParams"+" [inputClusterset=")+ inputClusterset)+", outWorkspace=")+ outWorkspace)+", outReportId=")+ outReportId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
