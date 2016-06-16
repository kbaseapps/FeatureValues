
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
 * <p>Original spec-file type: MatrixDescriptor</p>
 * <pre>
 * General info about matrix, including genome name that needs to be extracted from the genome object
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "matrix_id",
    "matrix_name",
    "matrix_description",
    "genome_id",
    "genome_name",
    "rows_count",
    "columns_count",
    "scale",
    "type",
    "row_normalization",
    "col_normalization"
})
public class MatrixDescriptor {

    @JsonProperty("matrix_id")
    private String matrixId;
    @JsonProperty("matrix_name")
    private String matrixName;
    @JsonProperty("matrix_description")
    private String matrixDescription;
    @JsonProperty("genome_id")
    private String genomeId;
    @JsonProperty("genome_name")
    private String genomeName;
    @JsonProperty("rows_count")
    private Long rowsCount;
    @JsonProperty("columns_count")
    private Long columnsCount;
    @JsonProperty("scale")
    private String scale;
    @JsonProperty("type")
    private String type;
    @JsonProperty("row_normalization")
    private String rowNormalization;
    @JsonProperty("col_normalization")
    private String colNormalization;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("matrix_id")
    public String getMatrixId() {
        return matrixId;
    }

    @JsonProperty("matrix_id")
    public void setMatrixId(String matrixId) {
        this.matrixId = matrixId;
    }

    public MatrixDescriptor withMatrixId(String matrixId) {
        this.matrixId = matrixId;
        return this;
    }

    @JsonProperty("matrix_name")
    public String getMatrixName() {
        return matrixName;
    }

    @JsonProperty("matrix_name")
    public void setMatrixName(String matrixName) {
        this.matrixName = matrixName;
    }

    public MatrixDescriptor withMatrixName(String matrixName) {
        this.matrixName = matrixName;
        return this;
    }

    @JsonProperty("matrix_description")
    public String getMatrixDescription() {
        return matrixDescription;
    }

    @JsonProperty("matrix_description")
    public void setMatrixDescription(String matrixDescription) {
        this.matrixDescription = matrixDescription;
    }

    public MatrixDescriptor withMatrixDescription(String matrixDescription) {
        this.matrixDescription = matrixDescription;
        return this;
    }

    @JsonProperty("genome_id")
    public String getGenomeId() {
        return genomeId;
    }

    @JsonProperty("genome_id")
    public void setGenomeId(String genomeId) {
        this.genomeId = genomeId;
    }

    public MatrixDescriptor withGenomeId(String genomeId) {
        this.genomeId = genomeId;
        return this;
    }

    @JsonProperty("genome_name")
    public String getGenomeName() {
        return genomeName;
    }

    @JsonProperty("genome_name")
    public void setGenomeName(String genomeName) {
        this.genomeName = genomeName;
    }

    public MatrixDescriptor withGenomeName(String genomeName) {
        this.genomeName = genomeName;
        return this;
    }

    @JsonProperty("rows_count")
    public Long getRowsCount() {
        return rowsCount;
    }

    @JsonProperty("rows_count")
    public void setRowsCount(Long rowsCount) {
        this.rowsCount = rowsCount;
    }

    public MatrixDescriptor withRowsCount(Long rowsCount) {
        this.rowsCount = rowsCount;
        return this;
    }

    @JsonProperty("columns_count")
    public Long getColumnsCount() {
        return columnsCount;
    }

    @JsonProperty("columns_count")
    public void setColumnsCount(Long columnsCount) {
        this.columnsCount = columnsCount;
    }

    public MatrixDescriptor withColumnsCount(Long columnsCount) {
        this.columnsCount = columnsCount;
        return this;
    }

    @JsonProperty("scale")
    public String getScale() {
        return scale;
    }

    @JsonProperty("scale")
    public void setScale(String scale) {
        this.scale = scale;
    }

    public MatrixDescriptor withScale(String scale) {
        this.scale = scale;
        return this;
    }

    @JsonProperty("type")
    public String getType() {
        return type;
    }

    @JsonProperty("type")
    public void setType(String type) {
        this.type = type;
    }

    public MatrixDescriptor withType(String type) {
        this.type = type;
        return this;
    }

    @JsonProperty("row_normalization")
    public String getRowNormalization() {
        return rowNormalization;
    }

    @JsonProperty("row_normalization")
    public void setRowNormalization(String rowNormalization) {
        this.rowNormalization = rowNormalization;
    }

    public MatrixDescriptor withRowNormalization(String rowNormalization) {
        this.rowNormalization = rowNormalization;
        return this;
    }

    @JsonProperty("col_normalization")
    public String getColNormalization() {
        return colNormalization;
    }

    @JsonProperty("col_normalization")
    public void setColNormalization(String colNormalization) {
        this.colNormalization = colNormalization;
    }

    public MatrixDescriptor withColNormalization(String colNormalization) {
        this.colNormalization = colNormalization;
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
        return ((((((((((((((((((((((((("MatrixDescriptor"+" [matrixId=")+ matrixId)+", matrixName=")+ matrixName)+", matrixDescription=")+ matrixDescription)+", genomeId=")+ genomeId)+", genomeName=")+ genomeName)+", rowsCount=")+ rowsCount)+", columnsCount=")+ columnsCount)+", scale=")+ scale)+", type=")+ type)+", rowNormalization=")+ rowNormalization)+", colNormalization=")+ colNormalization)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
