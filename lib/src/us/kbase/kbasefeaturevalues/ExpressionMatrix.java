
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
 * <p>Original spec-file type: ExpressionMatrix</p>
 * <pre>
 * A wrapper around a FloatMatrix2D designed for simple matricies of Expression
 * data.  Rows map to features, and columns map to conditions.  The data type 
 * includes some information about normalization factors and contains
 * mappings from row ids to features and col ids to conditions.
 * description - short optional description of the dataset
 * type - ? level, ratio, log-ratio
 * scale - ? probably: raw, ln, log2, log10
 * col_normalization - mean_center, median_center, mode_center, zscore
 * row_normalization - mean_center, median_center, mode_center, zscore
 * feature_mapping - map from row_id to feature id in the genome
 * data - contains values for (feature,condition) pairs, where 
 *     features correspond to rows and conditions are columns
 *     (ie data.values[feature][condition])
 * diff_expr_matrix_ref - added to connect filtered expression matrix to differential expression matrix
 *     used for filtering
 * @optional description row_normalization col_normalization
 * @optional genome_ref feature_mapping conditionset_ref condition_mapping report diff_expr_matrix_ref
 * @metadata ws type
 * @metadata ws scale
 * @metadata ws row_normalization
 * @metadata ws col_normalization
 * @metadata ws genome_ref as Genome
 * @metadata ws conditionset_ref as ConditionSet
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
    "feature_mapping",
    "conditionset_ref",
    "condition_mapping",
    "diff_expr_matrix_ref",
    "data",
    "report"
})
public class ExpressionMatrix {

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
    @JsonProperty("feature_mapping")
    private Map<String, String> featureMapping;
    @JsonProperty("conditionset_ref")
    private java.lang.String conditionsetRef;
    @JsonProperty("condition_mapping")
    private Map<String, String> conditionMapping;
    @JsonProperty("diff_expr_matrix_ref")
    private java.lang.String diffExprMatrixRef;
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

    public ExpressionMatrix withDescription(java.lang.String description) {
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

    public ExpressionMatrix withType(java.lang.String type) {
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

    public ExpressionMatrix withScale(java.lang.String scale) {
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

    public ExpressionMatrix withRowNormalization(java.lang.String rowNormalization) {
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

    public ExpressionMatrix withColNormalization(java.lang.String colNormalization) {
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

    public ExpressionMatrix withGenomeRef(java.lang.String genomeRef) {
        this.genomeRef = genomeRef;
        return this;
    }

    @JsonProperty("feature_mapping")
    public Map<String, String> getFeatureMapping() {
        return featureMapping;
    }

    @JsonProperty("feature_mapping")
    public void setFeatureMapping(Map<String, String> featureMapping) {
        this.featureMapping = featureMapping;
    }

    public ExpressionMatrix withFeatureMapping(Map<String, String> featureMapping) {
        this.featureMapping = featureMapping;
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

    public ExpressionMatrix withConditionsetRef(java.lang.String conditionsetRef) {
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

    public ExpressionMatrix withConditionMapping(Map<String, String> conditionMapping) {
        this.conditionMapping = conditionMapping;
        return this;
    }

    @JsonProperty("diff_expr_matrix_ref")
    public java.lang.String getDiffExprMatrixRef() {
        return diffExprMatrixRef;
    }

    @JsonProperty("diff_expr_matrix_ref")
    public void setDiffExprMatrixRef(java.lang.String diffExprMatrixRef) {
        this.diffExprMatrixRef = diffExprMatrixRef;
    }

    public ExpressionMatrix withDiffExprMatrixRef(java.lang.String diffExprMatrixRef) {
        this.diffExprMatrixRef = diffExprMatrixRef;
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

    public ExpressionMatrix withData(FloatMatrix2D data) {
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

    public ExpressionMatrix withReport(AnalysisReport report) {
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
        return ((((((((((((((((((((((((((("ExpressionMatrix"+" [description=")+ description)+", type=")+ type)+", scale=")+ scale)+", rowNormalization=")+ rowNormalization)+", colNormalization=")+ colNormalization)+", genomeRef=")+ genomeRef)+", featureMapping=")+ featureMapping)+", conditionsetRef=")+ conditionsetRef)+", conditionMapping=")+ conditionMapping)+", diffExprMatrixRef=")+ diffExprMatrixRef)+", data=")+ data)+", report=")+ report)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
