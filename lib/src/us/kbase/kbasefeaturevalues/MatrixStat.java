
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
 * <p>Original spec-file type: MatrixStat</p>
 * <pre>
 * Data type for bulk queries. It provides all necessary data to visulize basic properties of ExpressionMatrix 
 * mtx_descriptor - decriptor of the matrix as a whole
 * row_descriptors - descriptor for each row in the matrix (provides basic properties of the features)
 * column_descriptors - descriptor for each column in the matrix (provides basic properties of the conditions)
 * row_stats - basic statistics for each row (feature) in the matrix, like mean, min, max, etc acorss all columns (conditions)
 * column_stats - basic statistics for each row (feature) in the matrix, like mean, min, max, etc across all rows (features)
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "mtx_descriptor",
    "row_descriptors",
    "column_descriptors",
    "row_stats",
    "column_stats"
})
public class MatrixStat {

    /**
     * <p>Original spec-file type: MatrixDescriptor</p>
     * <pre>
     * General info about matrix, including genome name that needs to be extracted from the genome object
     * </pre>
     * 
     */
    @JsonProperty("mtx_descriptor")
    private MatrixDescriptor mtxDescriptor;
    @JsonProperty("row_descriptors")
    private List<us.kbase.kbasefeaturevalues.ItemDescriptor> rowDescriptors;
    @JsonProperty("column_descriptors")
    private List<us.kbase.kbasefeaturevalues.ItemDescriptor> columnDescriptors;
    @JsonProperty("row_stats")
    private List<us.kbase.kbasefeaturevalues.ItemStat> rowStats;
    @JsonProperty("column_stats")
    private List<us.kbase.kbasefeaturevalues.ItemStat> columnStats;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * <p>Original spec-file type: MatrixDescriptor</p>
     * <pre>
     * General info about matrix, including genome name that needs to be extracted from the genome object
     * </pre>
     * 
     */
    @JsonProperty("mtx_descriptor")
    public MatrixDescriptor getMtxDescriptor() {
        return mtxDescriptor;
    }

    /**
     * <p>Original spec-file type: MatrixDescriptor</p>
     * <pre>
     * General info about matrix, including genome name that needs to be extracted from the genome object
     * </pre>
     * 
     */
    @JsonProperty("mtx_descriptor")
    public void setMtxDescriptor(MatrixDescriptor mtxDescriptor) {
        this.mtxDescriptor = mtxDescriptor;
    }

    public MatrixStat withMtxDescriptor(MatrixDescriptor mtxDescriptor) {
        this.mtxDescriptor = mtxDescriptor;
        return this;
    }

    @JsonProperty("row_descriptors")
    public List<us.kbase.kbasefeaturevalues.ItemDescriptor> getRowDescriptors() {
        return rowDescriptors;
    }

    @JsonProperty("row_descriptors")
    public void setRowDescriptors(List<us.kbase.kbasefeaturevalues.ItemDescriptor> rowDescriptors) {
        this.rowDescriptors = rowDescriptors;
    }

    public MatrixStat withRowDescriptors(List<us.kbase.kbasefeaturevalues.ItemDescriptor> rowDescriptors) {
        this.rowDescriptors = rowDescriptors;
        return this;
    }

    @JsonProperty("column_descriptors")
    public List<us.kbase.kbasefeaturevalues.ItemDescriptor> getColumnDescriptors() {
        return columnDescriptors;
    }

    @JsonProperty("column_descriptors")
    public void setColumnDescriptors(List<us.kbase.kbasefeaturevalues.ItemDescriptor> columnDescriptors) {
        this.columnDescriptors = columnDescriptors;
    }

    public MatrixStat withColumnDescriptors(List<us.kbase.kbasefeaturevalues.ItemDescriptor> columnDescriptors) {
        this.columnDescriptors = columnDescriptors;
        return this;
    }

    @JsonProperty("row_stats")
    public List<us.kbase.kbasefeaturevalues.ItemStat> getRowStats() {
        return rowStats;
    }

    @JsonProperty("row_stats")
    public void setRowStats(List<us.kbase.kbasefeaturevalues.ItemStat> rowStats) {
        this.rowStats = rowStats;
    }

    public MatrixStat withRowStats(List<us.kbase.kbasefeaturevalues.ItemStat> rowStats) {
        this.rowStats = rowStats;
        return this;
    }

    @JsonProperty("column_stats")
    public List<us.kbase.kbasefeaturevalues.ItemStat> getColumnStats() {
        return columnStats;
    }

    @JsonProperty("column_stats")
    public void setColumnStats(List<us.kbase.kbasefeaturevalues.ItemStat> columnStats) {
        this.columnStats = columnStats;
    }

    public MatrixStat withColumnStats(List<us.kbase.kbasefeaturevalues.ItemStat> columnStats) {
        this.columnStats = columnStats;
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
        return ((((((((((((("MatrixStat"+" [mtxDescriptor=")+ mtxDescriptor)+", rowDescriptors=")+ rowDescriptors)+", columnDescriptors=")+ columnDescriptors)+", rowStats=")+ rowStats)+", columnStats=")+ columnStats)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
