
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
 * <p>Original spec-file type: labeled_cluster</p>
 * <pre>
 * id_to_pos - simple representation of a cluster, which maps features/conditions of the cluster to the
 * row/col index in the data (0-based index).  The index is useful for fast lookup of data
 * for a specified feature/condition in the cluster.
 * @optional meancor msec
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "id_to_pos",
    "meancor",
    "msec"
})
public class LabeledCluster {

    @JsonProperty("id_to_pos")
    private Map<String, Long> idToPos;
    @JsonProperty("meancor")
    private Double meancor;
    @JsonProperty("msec")
    private Double msec;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("id_to_pos")
    public Map<String, Long> getIdToPos() {
        return idToPos;
    }

    @JsonProperty("id_to_pos")
    public void setIdToPos(Map<String, Long> idToPos) {
        this.idToPos = idToPos;
    }

    public LabeledCluster withIdToPos(Map<String, Long> idToPos) {
        this.idToPos = idToPos;
        return this;
    }

    @JsonProperty("meancor")
    public Double getMeancor() {
        return meancor;
    }

    @JsonProperty("meancor")
    public void setMeancor(Double meancor) {
        this.meancor = meancor;
    }

    public LabeledCluster withMeancor(Double meancor) {
        this.meancor = meancor;
        return this;
    }

    @JsonProperty("msec")
    public Double getMsec() {
        return msec;
    }

    @JsonProperty("msec")
    public void setMsec(Double msec) {
        this.msec = msec;
    }

    public LabeledCluster withMsec(Double msec) {
        this.msec = msec;
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
        return ((((((((("LabeledCluster"+" [idToPos=")+ idToPos)+", meancor=")+ meancor)+", msec=")+ msec)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
