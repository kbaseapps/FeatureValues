
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
 * <p>Original spec-file type: FitnessMatrix</p>
 * <pre>
 * data - contains values for (feature,condition) pairs, where 
 *     features correspond to columns and conditions are rows.
 * @optional genome_ref report
 * @meta ws type genome_ref
 * @meta ws length(data.row_labels) as feature_count
 * @meta ws length(data.col_labels) as condition_count
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "type",
    "genome_ref",
    "data",
    "report"
})
public class FitnessMatrix {

    @JsonProperty("type")
    private String type;
    @JsonProperty("genome_ref")
    private String genomeRef;
    /**
     * <p>Original spec-file type: FloatMatrix2D</p>
     * <pre>
     * row_ids - ids for rows.
     * col_ids - ids for columns.
     * values - two dimensional array values[row][col], where first index 
     *     (row) goes vertically over outer list and (col) goes horizontally 
     *     along each each internal list.
     * @meta ws length(row_ids) as n_rows
     * @meta ws length(col_ids) as n_cols
     * </pre>
     * 
     */
    @JsonProperty("data")
    private FloatMatrix2D data;
    /**
     * <p>Original spec-file type: AnalysisReport</p>
     * <pre>
     * This object is created during upload of matrices.
     * </pre>
     * 
     */
    @JsonProperty("report")
    private AnalysisReport report;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public FitnessMatrix withType(String type) {
        this.type = type;
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

    public FitnessMatrix withGenomeRef(String genomeRef) {
        this.genomeRef = genomeRef;
        return this;
    }

    /**
     * <p>Original spec-file type: FloatMatrix2D</p>
     * <pre>
     * row_ids - ids for rows.
     * col_ids - ids for columns.
     * values - two dimensional array values[row][col], where first index 
     *     (row) goes vertically over outer list and (col) goes horizontally 
     *     along each each internal list.
     * @meta ws length(row_ids) as n_rows
     * @meta ws length(col_ids) as n_cols
     * </pre>
     * 
     */
    @JsonProperty("data")
    public FloatMatrix2D getData() {
        return data;
    }

    /**
     * <p>Original spec-file type: FloatMatrix2D</p>
     * <pre>
     * row_ids - ids for rows.
     * col_ids - ids for columns.
     * values - two dimensional array values[row][col], where first index 
     *     (row) goes vertically over outer list and (col) goes horizontally 
     *     along each each internal list.
     * @meta ws length(row_ids) as n_rows
     * @meta ws length(col_ids) as n_cols
     * </pre>
     * 
     */
    @JsonProperty("data")
    public void setData(FloatMatrix2D data) {
        this.data = data;
    }

    public FitnessMatrix withData(FloatMatrix2D data) {
        this.data = data;
        return this;
    }

    /**
     * <p>Original spec-file type: AnalysisReport</p>
     * <pre>
     * This object is created during upload of matrices.
     * </pre>
     * 
     */
    @JsonProperty("report")
    public AnalysisReport getReport() {
        return report;
    }

    /**
     * <p>Original spec-file type: AnalysisReport</p>
     * <pre>
     * This object is created during upload of matrices.
     * </pre>
     * 
     */
    @JsonProperty("report")
    public void setReport(AnalysisReport report) {
        this.report = report;
    }

    public FitnessMatrix withReport(AnalysisReport report) {
        this.report = report;
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
        return ((((((((((("FitnessMatrix"+" [type=")+ type)+", genomeRef=")+ genomeRef)+", data=")+ data)+", report=")+ report)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
