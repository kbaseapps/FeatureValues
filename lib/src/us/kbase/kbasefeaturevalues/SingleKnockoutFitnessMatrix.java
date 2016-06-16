
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
 * <p>Original spec-file type: SingleKnockoutFitnessMatrix</p>
 * <pre>
 * A wrapper around a FloatMatrix2D designed for simple matricies of Fitness data
 * for single gene/feature knockouts.  Generally fitness is measured as growth rate
 * for the knockout strain relative to wildtype.  This data type only supports
 * single feature knockouts.
 * description - short optional description of the dataset
 * type - ? level, ratio, log-ratio
 * scale - ? probably: raw, ln, log2, log10
 * col_normalization - mean_center, median_center, mode_center, zscore
 * row_normalization - mean_center, median_center, mode_center, zscore
 * feature_mapping - map from row_id to feature id in the genome
 * data - contains values for (feature,condition) pairs, where 
 *     features correspond to rows and conditions are columns
 *     (ie data.values[feature][condition])
 * @optional description row_normalization col_normalization
 * @optional genome_ref feature_ko_mapping conditionset_ref condition_mapping report
 * @metadata ws type
 * @metadata ws scale
 * @metadata ws row_normalization
 * @metadata ws col_normalization
 * @metadata ws genome_ref as Genome
 * @metadata ws length(data.row_ids) as feature_count
 * @metadata ws length(data.col_ids) as condition_count
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "description",
    "type",
    "scale",
    "row_normalization",
    "col_normalization",
    "genome_ref",
    "feature_ko_mapping",
    "conditionset_ref",
    "condition_mapping",
    "data",
    "report"
})
public class SingleKnockoutFitnessMatrix {

    @JsonProperty("description")
    private java.lang.String description;
    @JsonProperty("type")
    private java.lang.String type;
    @JsonProperty("scale")
    private java.lang.String scale;
    @JsonProperty("row_normalization")
    private java.lang.String rowNormalization;
    @JsonProperty("col_normalization")
    private java.lang.String colNormalization;
    @JsonProperty("genome_ref")
    private java.lang.String genomeRef;
    @JsonProperty("feature_ko_mapping")
    private Map<String, String> featureKoMapping;
    @JsonProperty("conditionset_ref")
    private java.lang.String conditionsetRef;
    @JsonProperty("condition_mapping")
    private Map<String, String> conditionMapping;
    /**
     * <p>Original spec-file type: FloatMatrix2D</p>
     * <pre>
     * A simple 2D matrix of floating point numbers with labels/ids for rows and
     * columns.  The matrix is stored as a list of lists, with the outer list
     * containing rows, and the inner lists containing values for each column of
     * that row.  Row/Col ids should be unique.
     * row_ids - unique ids for rows.
     * col_ids - unique ids for columns.
     * values - two dimensional array indexed as: values[row][col]
     * @metadata ws length(row_ids) as n_rows
     * @metadata ws length(col_ids) as n_cols
     * </pre>
     * 
     */
    @JsonProperty("data")
    private FloatMatrix2D data;
    /**
     * <p>Original spec-file type: AnalysisReport</p>
     * <pre>
     * A basic report object used for a variety of cases to mark informational
     * messages, warnings, and errors related to processing or quality control
     * checks of raw data.
     * </pre>
     * 
     */
    @JsonProperty("report")
    private AnalysisReport report;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("description")
    public java.lang.String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(java.lang.String description) {
        this.description = description;
    }

    public SingleKnockoutFitnessMatrix withDescription(java.lang.String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("type")
    public java.lang.String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(java.lang.String type) {
        this.type = type;
    }

    public SingleKnockoutFitnessMatrix withType(java.lang.String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("scale")
    public java.lang.String getScale() {
        return scale;
    }

    @JsonProperty("scale")
    public void setScale(java.lang.String scale) {
        this.scale = scale;
    }

    public SingleKnockoutFitnessMatrix withScale(java.lang.String scale) {
        this.scale = scale;
        return this;
    }

    @JsonProperty("row_normalization")
    public java.lang.String getRowNormalization() {
        return rowNormalization;
    }

    @JsonProperty("row_normalization")
    public void setRowNormalization(java.lang.String rowNormalization) {
        this.rowNormalization = rowNormalization;
    }

    public SingleKnockoutFitnessMatrix withRowNormalization(java.lang.String rowNormalization) {
        this.rowNormalization = rowNormalization;
        return this;
    }

    @JsonProperty("col_normalization")
    public java.lang.String getColNormalization() {
        return colNormalization;
    }

    @JsonProperty("col_normalization")
    public void setColNormalization(java.lang.String colNormalization) {
        this.colNormalization = colNormalization;
    }

    public SingleKnockoutFitnessMatrix withColNormalization(java.lang.String colNormalization) {
        this.colNormalization = colNormalization;
        return this;
    }

    @JsonProperty("genome_ref")
    public java.lang.String getGenomeRef() {
        return genomeRef;
    }

    @JsonProperty("genome_ref")
    public void setGenomeRef(java.lang.String genomeRef) {
        this.genomeRef = genomeRef;
    }

    public SingleKnockoutFitnessMatrix withGenomeRef(java.lang.String genomeRef) {
        this.genomeRef = genomeRef;
        return this;
    }

    @JsonProperty("feature_ko_mapping")
    public Map<String, String> getFeatureKoMapping() {
        return featureKoMapping;
    }

    @JsonProperty("feature_ko_mapping")
    public void setFeatureKoMapping(Map<String, String> featureKoMapping) {
        this.featureKoMapping = featureKoMapping;
    }

    public SingleKnockoutFitnessMatrix withFeatureKoMapping(Map<String, String> featureKoMapping) {
        this.featureKoMapping = featureKoMapping;
        return this;
    }

    @JsonProperty("conditionset_ref")
    public java.lang.String getConditionsetRef() {
        return conditionsetRef;
    }

    @JsonProperty("conditionset_ref")
    public void setConditionsetRef(java.lang.String conditionsetRef) {
        this.conditionsetRef = conditionsetRef;
    }

    public SingleKnockoutFitnessMatrix withConditionsetRef(java.lang.String conditionsetRef) {
        this.conditionsetRef = conditionsetRef;
        return this;
    }

    @JsonProperty("condition_mapping")
    public Map<String, String> getConditionMapping() {
        return conditionMapping;
    }

    @JsonProperty("condition_mapping")
    public void setConditionMapping(Map<String, String> conditionMapping) {
        this.conditionMapping = conditionMapping;
    }

    public SingleKnockoutFitnessMatrix withConditionMapping(Map<String, String> conditionMapping) {
        this.conditionMapping = conditionMapping;
        return this;
    }

    /**
     * <p>Original spec-file type: FloatMatrix2D</p>
     * <pre>
     * A simple 2D matrix of floating point numbers with labels/ids for rows and
     * columns.  The matrix is stored as a list of lists, with the outer list
     * containing rows, and the inner lists containing values for each column of
     * that row.  Row/Col ids should be unique.
     * row_ids - unique ids for rows.
     * col_ids - unique ids for columns.
     * values - two dimensional array indexed as: values[row][col]
     * @metadata ws length(row_ids) as n_rows
     * @metadata ws length(col_ids) as n_cols
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
     * A simple 2D matrix of floating point numbers with labels/ids for rows and
     * columns.  The matrix is stored as a list of lists, with the outer list
     * containing rows, and the inner lists containing values for each column of
     * that row.  Row/Col ids should be unique.
     * row_ids - unique ids for rows.
     * col_ids - unique ids for columns.
     * values - two dimensional array indexed as: values[row][col]
     * @metadata ws length(row_ids) as n_rows
     * @metadata ws length(col_ids) as n_cols
     * </pre>
     * 
     */
    @JsonProperty("data")
    public void setData(FloatMatrix2D data) {
        this.data = data;
    }

    public SingleKnockoutFitnessMatrix withData(FloatMatrix2D data) {
        this.data = data;
        return this;
    }

    /**
     * <p>Original spec-file type: AnalysisReport</p>
     * <pre>
     * A basic report object used for a variety of cases to mark informational
     * messages, warnings, and errors related to processing or quality control
     * checks of raw data.
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
     * A basic report object used for a variety of cases to mark informational
     * messages, warnings, and errors related to processing or quality control
     * checks of raw data.
     * </pre>
     * 
     */
    @JsonProperty("report")
    public void setReport(AnalysisReport report) {
        this.report = report;
    }

    public SingleKnockoutFitnessMatrix withReport(AnalysisReport report) {
        this.report = report;
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
        return ((((((((((((((((((((((((("SingleKnockoutFitnessMatrix"+" [description=")+ description)+", type=")+ type)+", scale=")+ scale)+", rowNormalization=")+ rowNormalization)+", colNormalization=")+ colNormalization)+", genomeRef=")+ genomeRef)+", featureKoMapping=")+ featureKoMapping)+", conditionsetRef=")+ conditionsetRef)+", conditionMapping=")+ conditionMapping)+", data=")+ data)+", report=")+ report)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
