
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
 * <p>Original spec-file type: GetSubmatrixStatParams</p>
 * <pre>
 * Parameters to retrieve SubmatrixStat        
 * input_data - reference to the source matrix        
 *         row_indeces - indeces defining a subset of matrix rows. Either row_indeces (highest priorery) or row_ids should be provided.
 *         row_ids - ids defining a subset of matrix rows. Either row_indeces (highest priorery) or row_ids should be provided.
 *         
 *         column_indeces - indeces defining a subset of matrix columns. Either column_indeces (highest priorery) or column_ids should be provided.
 *         column_ids - ids defining a subset of matrix columns. Either column_indeces (highest priorery) or column_ids should be provided.
 *         
 *         fl_row_set_stats - defines whether row_set_stats should be calculated in include in the SubmatrixStat. Default value = 0
 *         fl_column_set_stat - defines whether column_set_stat should be calculated in include in the SubmatrixStat. Default value = 0
 *         
 * fl_mtx_row_set_stat - defines whether mtx_row_set_stat should be calculated in include in the SubmatrixStat. Default value = 0
 * fl_mtx_column_set_stat - defines whether mtx_column_set_stat should be calculated in include in the SubmatrixStat. Default value = 0
 * fl_row_pairwise_correlation - defines whether row_pairwise_correlation should be calculated in include in the SubmatrixStat. Default value = 0        
 * fl_column_pairwise_correlation - defines whether column_pairwise_correlation should be calculated in include in the SubmatrixStat. Default value = 0
 * fl_values - defines whether values should be calculated in include in the SubmatrixStat. Default value = 0
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "input_data",
    "row_indeces",
    "row_ids",
    "column_indeces",
    "column_ids",
    "fl_row_set_stats",
    "fl_column_set_stat",
    "fl_mtx_row_set_stat",
    "fl_mtx_column_set_stat",
    "fl_row_pairwise_correlation",
    "fl_column_pairwise_correlation",
    "fl_values"
})
public class GetSubmatrixStatParams {

    @JsonProperty("input_data")
    private java.lang.String inputData;
    @JsonProperty("row_indeces")
    private List<Long> rowIndeces;
    @JsonProperty("row_ids")
    private List<String> rowIds;
    @JsonProperty("column_indeces")
    private List<Long> columnIndeces;
    @JsonProperty("column_ids")
    private List<String> columnIds;
    @JsonProperty("fl_row_set_stats")
    private java.lang.Long flRowSetStats;
    @JsonProperty("fl_column_set_stat")
    private java.lang.Long flColumnSetStat;
    @JsonProperty("fl_mtx_row_set_stat")
    private java.lang.Long flMtxRowSetStat;
    @JsonProperty("fl_mtx_column_set_stat")
    private java.lang.Long flMtxColumnSetStat;
    @JsonProperty("fl_row_pairwise_correlation")
    private java.lang.Long flRowPairwiseCorrelation;
    @JsonProperty("fl_column_pairwise_correlation")
    private java.lang.Long flColumnPairwiseCorrelation;
    @JsonProperty("fl_values")
    private java.lang.Long flValues;
    private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();

    @JsonProperty("input_data")
    public java.lang.String getInputData() {
        return inputData;
    }

    @JsonProperty("input_data")
    public void setInputData(java.lang.String inputData) {
        this.inputData = inputData;
    }

    public GetSubmatrixStatParams withInputData(java.lang.String inputData) {
        this.inputData = inputData;
        return this;
    }

    @JsonProperty("row_indeces")
    public List<Long> getRowIndeces() {
        return rowIndeces;
    }

    @JsonProperty("row_indeces")
    public void setRowIndeces(List<Long> rowIndeces) {
        this.rowIndeces = rowIndeces;
    }

    public GetSubmatrixStatParams withRowIndeces(List<Long> rowIndeces) {
        this.rowIndeces = rowIndeces;
        return this;
    }

    @JsonProperty("row_ids")
    public List<String> getRowIds() {
        return rowIds;
    }

    @JsonProperty("row_ids")
    public void setRowIds(List<String> rowIds) {
        this.rowIds = rowIds;
    }

    public GetSubmatrixStatParams withRowIds(List<String> rowIds) {
        this.rowIds = rowIds;
        return this;
    }

    @JsonProperty("column_indeces")
    public List<Long> getColumnIndeces() {
        return columnIndeces;
    }

    @JsonProperty("column_indeces")
    public void setColumnIndeces(List<Long> columnIndeces) {
        this.columnIndeces = columnIndeces;
    }

    public GetSubmatrixStatParams withColumnIndeces(List<Long> columnIndeces) {
        this.columnIndeces = columnIndeces;
        return this;
    }

    @JsonProperty("column_ids")
    public List<String> getColumnIds() {
        return columnIds;
    }

    @JsonProperty("column_ids")
    public void setColumnIds(List<String> columnIds) {
        this.columnIds = columnIds;
    }

    public GetSubmatrixStatParams withColumnIds(List<String> columnIds) {
        this.columnIds = columnIds;
        return this;
    }

    @JsonProperty("fl_row_set_stats")
    public java.lang.Long getFlRowSetStats() {
        return flRowSetStats;
    }

    @JsonProperty("fl_row_set_stats")
    public void setFlRowSetStats(java.lang.Long flRowSetStats) {
        this.flRowSetStats = flRowSetStats;
    }

    public GetSubmatrixStatParams withFlRowSetStats(java.lang.Long flRowSetStats) {
        this.flRowSetStats = flRowSetStats;
        return this;
    }

    @JsonProperty("fl_column_set_stat")
    public java.lang.Long getFlColumnSetStat() {
        return flColumnSetStat;
    }

    @JsonProperty("fl_column_set_stat")
    public void setFlColumnSetStat(java.lang.Long flColumnSetStat) {
        this.flColumnSetStat = flColumnSetStat;
    }

    public GetSubmatrixStatParams withFlColumnSetStat(java.lang.Long flColumnSetStat) {
        this.flColumnSetStat = flColumnSetStat;
        return this;
    }

    @JsonProperty("fl_mtx_row_set_stat")
    public java.lang.Long getFlMtxRowSetStat() {
        return flMtxRowSetStat;
    }

    @JsonProperty("fl_mtx_row_set_stat")
    public void setFlMtxRowSetStat(java.lang.Long flMtxRowSetStat) {
        this.flMtxRowSetStat = flMtxRowSetStat;
    }

    public GetSubmatrixStatParams withFlMtxRowSetStat(java.lang.Long flMtxRowSetStat) {
        this.flMtxRowSetStat = flMtxRowSetStat;
        return this;
    }

    @JsonProperty("fl_mtx_column_set_stat")
    public java.lang.Long getFlMtxColumnSetStat() {
        return flMtxColumnSetStat;
    }

    @JsonProperty("fl_mtx_column_set_stat")
    public void setFlMtxColumnSetStat(java.lang.Long flMtxColumnSetStat) {
        this.flMtxColumnSetStat = flMtxColumnSetStat;
    }

    public GetSubmatrixStatParams withFlMtxColumnSetStat(java.lang.Long flMtxColumnSetStat) {
        this.flMtxColumnSetStat = flMtxColumnSetStat;
        return this;
    }

    @JsonProperty("fl_row_pairwise_correlation")
    public java.lang.Long getFlRowPairwiseCorrelation() {
        return flRowPairwiseCorrelation;
    }

    @JsonProperty("fl_row_pairwise_correlation")
    public void setFlRowPairwiseCorrelation(java.lang.Long flRowPairwiseCorrelation) {
        this.flRowPairwiseCorrelation = flRowPairwiseCorrelation;
    }

    public GetSubmatrixStatParams withFlRowPairwiseCorrelation(java.lang.Long flRowPairwiseCorrelation) {
        this.flRowPairwiseCorrelation = flRowPairwiseCorrelation;
        return this;
    }

    @JsonProperty("fl_column_pairwise_correlation")
    public java.lang.Long getFlColumnPairwiseCorrelation() {
        return flColumnPairwiseCorrelation;
    }

    @JsonProperty("fl_column_pairwise_correlation")
    public void setFlColumnPairwiseCorrelation(java.lang.Long flColumnPairwiseCorrelation) {
        this.flColumnPairwiseCorrelation = flColumnPairwiseCorrelation;
    }

    public GetSubmatrixStatParams withFlColumnPairwiseCorrelation(java.lang.Long flColumnPairwiseCorrelation) {
        this.flColumnPairwiseCorrelation = flColumnPairwiseCorrelation;
        return this;
    }

    @JsonProperty("fl_values")
    public java.lang.Long getFlValues() {
        return flValues;
    }

    @JsonProperty("fl_values")
    public void setFlValues(java.lang.Long flValues) {
        this.flValues = flValues;
    }

    public GetSubmatrixStatParams withFlValues(java.lang.Long flValues) {
        this.flValues = flValues;
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
        return ((((((((((((((((((((((((((("GetSubmatrixStatParams"+" [inputData=")+ inputData)+", rowIndeces=")+ rowIndeces)+", rowIds=")+ rowIds)+", columnIndeces=")+ columnIndeces)+", columnIds=")+ columnIds)+", flRowSetStats=")+ flRowSetStats)+", flColumnSetStat=")+ flColumnSetStat)+", flMtxRowSetStat=")+ flMtxRowSetStat)+", flMtxColumnSetStat=")+ flMtxColumnSetStat)+", flRowPairwiseCorrelation=")+ flRowPairwiseCorrelation)+", flColumnPairwiseCorrelation=")+ flColumnPairwiseCorrelation)+", flValues=")+ flValues)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
