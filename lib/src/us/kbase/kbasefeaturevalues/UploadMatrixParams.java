
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
 * <p>Original spec-file type: UploadMatrixParams</p>
 * <pre>
 * input_shock_id and input_file_path - alternative intput params,
 * genome_ref - optional reference to a Genome object that will be
 *     used for mapping feature IDs to,
 * fill_missing_values - optional flag for filling in missing 
 *     values in matrix (default value is false),
 * data_type - optional filed, value is one of 'untransformed',
 *     'log2_level', 'log10_level', 'log2_ratio', 'log10_ratio' or
 *     'unknown' (last one is default value),
 * data_scale - optional parameter (default value is '1.0').
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_shock_id",
    "input_file_path",
    "genome_ref",
    "fill_missing_values",
    "data_type",
    "data_scale",
    "output_ws_name",
    "output_obj_name"
})
public class UploadMatrixParams {

    @JsonProperty("input_shock_id")
    private String inputShockId;
    @JsonProperty("input_file_path")
    private String inputFilePath;
    @JsonProperty("genome_ref")
    private String genomeRef;
    @JsonProperty("fill_missing_values")
    private Long fillMissingValues;
    @JsonProperty("data_type")
    private String dataType;
    @JsonProperty("data_scale")
    private String dataScale;
    @JsonProperty("output_ws_name")
    private String outputWsName;
    @JsonProperty("output_obj_name")
    private String outputObjName;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("input_shock_id")
    public String getInputShockId() {
        return inputShockId;
    }

    @JsonProperty("input_shock_id")
    public void setInputShockId(String inputShockId) {
        this.inputShockId = inputShockId;
    }

    public UploadMatrixParams withInputShockId(String inputShockId) {
        this.inputShockId = inputShockId;
        return this;
    }

    @JsonProperty("input_file_path")
    public String getInputFilePath() {
        return inputFilePath;
    }

    @JsonProperty("input_file_path")
    public void setInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
    }

    public UploadMatrixParams withInputFilePath(String inputFilePath) {
        this.inputFilePath = inputFilePath;
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

    public UploadMatrixParams withGenomeRef(String genomeRef) {
        this.genomeRef = genomeRef;
        return this;
    }

    @JsonProperty("fill_missing_values")
    public Long getFillMissingValues() {
        return fillMissingValues;
    }

    @JsonProperty("fill_missing_values")
    public void setFillMissingValues(Long fillMissingValues) {
        this.fillMissingValues = fillMissingValues;
    }

    public UploadMatrixParams withFillMissingValues(Long fillMissingValues) {
        this.fillMissingValues = fillMissingValues;
        return this;
    }

    @JsonProperty("data_type")
    public String getDataType() {
        return dataType;
    }

    @JsonProperty("data_type")
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public UploadMatrixParams withDataType(String dataType) {
        this.dataType = dataType;
        return this;
    }

    @JsonProperty("data_scale")
    public String getDataScale() {
        return dataScale;
    }

    @JsonProperty("data_scale")
    public void setDataScale(String dataScale) {
        this.dataScale = dataScale;
    }

    public UploadMatrixParams withDataScale(String dataScale) {
        this.dataScale = dataScale;
        return this;
    }

    @JsonProperty("output_ws_name")
    public String getOutputWsName() {
        return outputWsName;
    }

    @JsonProperty("output_ws_name")
    public void setOutputWsName(String outputWsName) {
        this.outputWsName = outputWsName;
    }

    public UploadMatrixParams withOutputWsName(String outputWsName) {
        this.outputWsName = outputWsName;
        return this;
    }

    @JsonProperty("output_obj_name")
    public String getOutputObjName() {
        return outputObjName;
    }

    @JsonProperty("output_obj_name")
    public void setOutputObjName(String outputObjName) {
        this.outputObjName = outputObjName;
    }

    public UploadMatrixParams withOutputObjName(String outputObjName) {
        this.outputObjName = outputObjName;
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
        return ((((((((((((((((((("UploadMatrixParams"+" [inputShockId=")+ inputShockId)+", inputFilePath=")+ inputFilePath)+", genomeRef=")+ genomeRef)+", fillMissingValues=")+ fillMissingValues)+", dataType=")+ dataType)+", dataScale=")+ dataScale)+", outputWsName=")+ outputWsName)+", outputObjName=")+ outputObjName)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
