
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
 * <p>Original spec-file type: SubmatrixStat</p>
 * <pre>
 * Data type for bulk queries. It provides various statistics calculated on sub-matrix. The sub-matrix is defined by a subset of rows and columns via parameters.
 * Parameters will also define the required types of statics.
 *                 
 * mtx_descriptor - basic properties of the source matrix
 * row_descriptors - descriptor for each row in a subset defined in the parameters
 * column_descriptors - descriptor for each column in a subset defined in the parameters
 * row_set_stats - basic statistics for a subset of rows calculated on a subset of columns 
 * column_set_stat - basic statistics for a subset of columns calculated on a subset of rows
 * mtx_row_set_stat - basic statistics for a subset of rows calculated on ALL columns in the matrix (can be used as a backgound in comparison with row_set_stats)
 * mtx_column_set_stat - basic statistics for a subset of columns calculated on ALL rows in the matrix (can be used as a backgound in comparison with column_set_stat)
 * row_pairwise_correlation - pariwise perason correlation for a subset of rows (features)  
 * column_pairwise_correlation - pariwise perason correlation for a subset of columns (conditions)
 * values - sub-matrix representing actual values for a given subset of rows and a subset of columns
 * </pre>
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Generated("com.googlecode.jsonschema2pojo")
@JsonPropertyOrder({
    "mtx_descriptor",
    "row_descriptors",
    "column_descriptors",
    "row_set_stats",
    "column_set_stat",
    "mtx_row_set_stat",
    "mtx_column_set_stat",
    "row_pairwise_correlation",
    "column_pairwise_correlation",
    "values"
})
public class SubmatrixStat {

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
    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("row_set_stats")
    private ItemSetStat rowSetStats;
    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("column_set_stat")
    private ItemSetStat columnSetStat;
    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("mtx_row_set_stat")
    private ItemSetStat mtxRowSetStat;
    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("mtx_column_set_stat")
    private ItemSetStat mtxColumnSetStat;
    /**
     * <p>Original spec-file type: PairwiseComparison</p>
     * <pre>
     * To represent a pairwise comparison of several elements defined by 'indeces'.  
     * This data type can be used to model represent pairwise correlation of expression profiles for a set of genes.                 
     * indeces - indeces of elements to be compared
     * comparison_values - values representing a parituclar type of comparison between elements. 
     *         Expected to be symmetric: comparison_values[i][j] = comparison_values[j][i].
     *         Diagonal values: comparison_values[i][i] = 0
     *         
     * avgs - mean of comparison_values for each element        
     * mins - min of comparison_values for each element
     * maxs - max of comparison_values for each element
     * stds - std of comparison_values for each element
     * </pre>
     * 
     */
    @JsonProperty("row_pairwise_correlation")
    private PairwiseComparison rowPairwiseCorrelation;
    /**
     * <p>Original spec-file type: PairwiseComparison</p>
     * <pre>
     * To represent a pairwise comparison of several elements defined by 'indeces'.  
     * This data type can be used to model represent pairwise correlation of expression profiles for a set of genes.                 
     * indeces - indeces of elements to be compared
     * comparison_values - values representing a parituclar type of comparison between elements. 
     *         Expected to be symmetric: comparison_values[i][j] = comparison_values[j][i].
     *         Diagonal values: comparison_values[i][i] = 0
     *         
     * avgs - mean of comparison_values for each element        
     * mins - min of comparison_values for each element
     * maxs - max of comparison_values for each element
     * stds - std of comparison_values for each element
     * </pre>
     * 
     */
    @JsonProperty("column_pairwise_correlation")
    private PairwiseComparison columnPairwiseCorrelation;
    @JsonProperty("values")
    private List<List<Double>> values;
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

    public SubmatrixStat withMtxDescriptor(MatrixDescriptor mtxDescriptor) {
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

    public SubmatrixStat withRowDescriptors(List<us.kbase.kbasefeaturevalues.ItemDescriptor> rowDescriptors) {
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

    public SubmatrixStat withColumnDescriptors(List<us.kbase.kbasefeaturevalues.ItemDescriptor> columnDescriptors) {
        this.columnDescriptors = columnDescriptors;
        return this;
    }

    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("row_set_stats")
    public ItemSetStat getRowSetStats() {
        return rowSetStats;
    }

    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("row_set_stats")
    public void setRowSetStats(ItemSetStat rowSetStats) {
        this.rowSetStats = rowSetStats;
    }

    public SubmatrixStat withRowSetStats(ItemSetStat rowSetStats) {
        this.rowSetStats = rowSetStats;
        return this;
    }

    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("column_set_stat")
    public ItemSetStat getColumnSetStat() {
        return columnSetStat;
    }

    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("column_set_stat")
    public void setColumnSetStat(ItemSetStat columnSetStat) {
        this.columnSetStat = columnSetStat;
    }

    public SubmatrixStat withColumnSetStat(ItemSetStat columnSetStat) {
        this.columnSetStat = columnSetStat;
        return this;
    }

    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("mtx_row_set_stat")
    public ItemSetStat getMtxRowSetStat() {
        return mtxRowSetStat;
    }

    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("mtx_row_set_stat")
    public void setMtxRowSetStat(ItemSetStat mtxRowSetStat) {
        this.mtxRowSetStat = mtxRowSetStat;
    }

    public SubmatrixStat withMtxRowSetStat(ItemSetStat mtxRowSetStat) {
        this.mtxRowSetStat = mtxRowSetStat;
        return this;
    }

    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("mtx_column_set_stat")
    public ItemSetStat getMtxColumnSetStat() {
        return mtxColumnSetStat;
    }

    /**
     * <p>Original spec-file type: ItemSetStat</p>
     * <pre>
     * Same as ItemStat, but for a set of Items. Actually it can be modeled as a list<ItemStat>, but this way we can optimize data transfer in two ways:
     *  1. In parameters we can specify that we need a subset of properties, e.g. only "avgs". 
     *  2. No field names in json (avg, min, max, etc) for each element in the list
     *             indeces_for - indeces of items in a collection FOR which all statitics is collected
     *             indeces_on - indeces of items in the associated vector ON which the statistics is calculated
     *             size - number of elements defined by indeces_on (expected to be the same for all items defined by indeces_for)
     *             avgs - mean values for each item defined by indeces_for across all elements defined by indeces_on 
     *             mins - min values for each item defined by indeces_for across all elements defined by indeces_on 
     *             maxs - max values for each item defined by indeces_for across all elements defined by indeces_on 
     *             stds - std values for each item defined by indeces_for across all elements defined by indeces_on 
     *             missing_values - number of missing values for each item defined by indeces_for across all elements defined by indeces_on
     * </pre>
     * 
     */
    @JsonProperty("mtx_column_set_stat")
    public void setMtxColumnSetStat(ItemSetStat mtxColumnSetStat) {
        this.mtxColumnSetStat = mtxColumnSetStat;
    }

    public SubmatrixStat withMtxColumnSetStat(ItemSetStat mtxColumnSetStat) {
        this.mtxColumnSetStat = mtxColumnSetStat;
        return this;
    }

    /**
     * <p>Original spec-file type: PairwiseComparison</p>
     * <pre>
     * To represent a pairwise comparison of several elements defined by 'indeces'.  
     * This data type can be used to model represent pairwise correlation of expression profiles for a set of genes.                 
     * indeces - indeces of elements to be compared
     * comparison_values - values representing a parituclar type of comparison between elements. 
     *         Expected to be symmetric: comparison_values[i][j] = comparison_values[j][i].
     *         Diagonal values: comparison_values[i][i] = 0
     *         
     * avgs - mean of comparison_values for each element        
     * mins - min of comparison_values for each element
     * maxs - max of comparison_values for each element
     * stds - std of comparison_values for each element
     * </pre>
     * 
     */
    @JsonProperty("row_pairwise_correlation")
    public PairwiseComparison getRowPairwiseCorrelation() {
        return rowPairwiseCorrelation;
    }

    /**
     * <p>Original spec-file type: PairwiseComparison</p>
     * <pre>
     * To represent a pairwise comparison of several elements defined by 'indeces'.  
     * This data type can be used to model represent pairwise correlation of expression profiles for a set of genes.                 
     * indeces - indeces of elements to be compared
     * comparison_values - values representing a parituclar type of comparison between elements. 
     *         Expected to be symmetric: comparison_values[i][j] = comparison_values[j][i].
     *         Diagonal values: comparison_values[i][i] = 0
     *         
     * avgs - mean of comparison_values for each element        
     * mins - min of comparison_values for each element
     * maxs - max of comparison_values for each element
     * stds - std of comparison_values for each element
     * </pre>
     * 
     */
    @JsonProperty("row_pairwise_correlation")
    public void setRowPairwiseCorrelation(PairwiseComparison rowPairwiseCorrelation) {
        this.rowPairwiseCorrelation = rowPairwiseCorrelation;
    }

    public SubmatrixStat withRowPairwiseCorrelation(PairwiseComparison rowPairwiseCorrelation) {
        this.rowPairwiseCorrelation = rowPairwiseCorrelation;
        return this;
    }

    /**
     * <p>Original spec-file type: PairwiseComparison</p>
     * <pre>
     * To represent a pairwise comparison of several elements defined by 'indeces'.  
     * This data type can be used to model represent pairwise correlation of expression profiles for a set of genes.                 
     * indeces - indeces of elements to be compared
     * comparison_values - values representing a parituclar type of comparison between elements. 
     *         Expected to be symmetric: comparison_values[i][j] = comparison_values[j][i].
     *         Diagonal values: comparison_values[i][i] = 0
     *         
     * avgs - mean of comparison_values for each element        
     * mins - min of comparison_values for each element
     * maxs - max of comparison_values for each element
     * stds - std of comparison_values for each element
     * </pre>
     * 
     */
    @JsonProperty("column_pairwise_correlation")
    public PairwiseComparison getColumnPairwiseCorrelation() {
        return columnPairwiseCorrelation;
    }

    /**
     * <p>Original spec-file type: PairwiseComparison</p>
     * <pre>
     * To represent a pairwise comparison of several elements defined by 'indeces'.  
     * This data type can be used to model represent pairwise correlation of expression profiles for a set of genes.                 
     * indeces - indeces of elements to be compared
     * comparison_values - values representing a parituclar type of comparison between elements. 
     *         Expected to be symmetric: comparison_values[i][j] = comparison_values[j][i].
     *         Diagonal values: comparison_values[i][i] = 0
     *         
     * avgs - mean of comparison_values for each element        
     * mins - min of comparison_values for each element
     * maxs - max of comparison_values for each element
     * stds - std of comparison_values for each element
     * </pre>
     * 
     */
    @JsonProperty("column_pairwise_correlation")
    public void setColumnPairwiseCorrelation(PairwiseComparison columnPairwiseCorrelation) {
        this.columnPairwiseCorrelation = columnPairwiseCorrelation;
    }

    public SubmatrixStat withColumnPairwiseCorrelation(PairwiseComparison columnPairwiseCorrelation) {
        this.columnPairwiseCorrelation = columnPairwiseCorrelation;
        return this;
    }

    @JsonProperty("values")
    public List<List<Double>> getValues() {
        return values;
    }

    @JsonProperty("values")
    public void setValues(List<List<Double>> values) {
        this.values = values;
    }

    public SubmatrixStat withValues(List<List<Double>> values) {
        this.values = values;
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
        return ((((((((((((((((((((((("SubmatrixStat"+" [mtxDescriptor=")+ mtxDescriptor)+", rowDescriptors=")+ rowDescriptors)+", columnDescriptors=")+ columnDescriptors)+", rowSetStats=")+ rowSetStats)+", columnSetStat=")+ columnSetStat)+", mtxRowSetStat=")+ mtxRowSetStat)+", mtxColumnSetStat=")+ mtxColumnSetStat)+", rowPairwiseCorrelation=")+ rowPairwiseCorrelation)+", columnPairwiseCorrelation=")+ columnPairwiseCorrelation)+", values=")+ values)+", additionalProperties=")+ additionalProperties)+"]");
    }

}
