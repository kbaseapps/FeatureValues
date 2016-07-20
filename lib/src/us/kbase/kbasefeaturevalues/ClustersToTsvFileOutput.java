
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
 * <p>Original spec-file type: ClustersToTsvFileOutput</p>
 * 
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "file_path",
    "shock_id"
})
public class ClustersToTsvFileOutput {

    @JsonProperty("file_path")
    private String filePath;
    @JsonProperty("shock_id")
    private String shockId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("file_path")
    public String getFilePath() {
        return filePath;
    }

    @JsonProperty("file_path")
    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public ClustersToTsvFileOutput withFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    @JsonProperty("shock_id")
    public String getShockId() {
        return shockId;
    }

    @JsonProperty("shock_id")
    public void setShockId(String shockId) {
        this.shockId = shockId;
    }

    public ClustersToTsvFileOutput withShockId(String shockId) {
        this.shockId = shockId;
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
        return ((((((("ClustersToTsvFileOutput"+" [filePath=")+ filePath)+", shockId=")+ shockId)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
