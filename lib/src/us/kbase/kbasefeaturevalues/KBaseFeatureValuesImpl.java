package us.kbase.kbasefeaturevalues;

import genomeannotationapi.GenomeAnnotationAPIServiceClient;
import genomeannotationapi.GenomeDataV1;
import genomeannotationapi.GenomeSelectorV1;
import genomeannotationapi.GetGenomeParamsV1;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import kbasegenomes.Feature;
import kbasegenomes.Genome;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import us.kbase.auth.AuthToken;
import us.kbase.clusterservice.ClusterResults;
import us.kbase.clusterservice.ClusterServiceLocalClient;
import us.kbase.clusterservice.ClusterServicePyLocalClient;
import us.kbase.clusterservice.ClusterServiceRLocalClient;
import us.kbase.common.service.Tuple11;
import us.kbase.common.service.UObject;
import us.kbase.kbasefeaturevalues.transform.ExpressionDownloader;
import us.kbase.kbasefeaturevalues.transform.ExpressionUploader;
import us.kbase.kbasefeaturevalues.transform.FeatureClustersDownloader;
import us.kbase.workspace.GetObjects2Params;
import us.kbase.workspace.ObjectData;
import us.kbase.workspace.ObjectSaveData;
import us.kbase.workspace.ObjectSpecification;
import us.kbase.workspace.ProvenanceAction;
import us.kbase.workspace.SaveObjectsParams;
import us.kbase.workspace.WorkspaceClient;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import datafileutil.DataFileUtilClient;
import datafileutil.PackageForDownloadParams;
import datafileutil.ShockToFileParams;

public class KBaseFeatureValuesImpl {
    private String jobId;
    private AuthToken token;
    private Map<String, String> config;
    private File workDir;
    private String wsUrl = null;
    private WorkspaceClient wsClient = null;
    private String srvWizUrl = null;
    
    public KBaseFeatureValuesImpl(String jobId, AuthToken token, Map<String, String> config,
            File workDir) throws Exception {
        this.jobId = jobId;
        this.token = token;
        this.config = config;
        this.workDir = workDir == null ? new File(".").getCanonicalFile() : workDir;
    }
    
    public String getWsUrl() {
        if (wsUrl == null)
            wsUrl = config.get(KBaseFeatureValuesServer.CONFIG_PARAM_WS_URL);
        return wsUrl;
    }

    public String getSrvWizUrl() {
        if (srvWizUrl == null)
            srvWizUrl = config.get(KBaseFeatureValuesServer.CONFIG_PARAM_SRV_WIZ_URL);
        return srvWizUrl;
    }

    public WorkspaceClient getWsClient() throws Exception {
        if (wsClient != null)
            return wsClient;
        wsClient = new WorkspaceClient(new URL(getWsUrl()), token);
        wsClient.setIsInsecureHttpConnectionAllowed(true);
        return wsClient;
    }
    
    public ClusterServiceLocalClient getMathClient() throws Exception {
        ClusterServiceRLocalClient mathClient = new ClusterServiceRLocalClient(workDir);
        String binPath = config.get(KBaseFeatureValuesServer.CONFIG_PARAM_CLIENT_BIN_DIR);
        if (binPath != null)
            mathClient.setBinDir(new File(binPath));
        return mathClient;
    }
    
    public String getJobId() {
        return jobId;
    }
    
    public void estimateK(EstimateKParams params, 
            List<ProvenanceAction> provenance) throws Exception {
        ObjectData objData = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(params.getInputMatrix()))))
                .getData().get(0);
        BioMatrix matrix = objData.getData().asClassInstance(BioMatrix.class);
        ClusterServiceLocalClient mathClient = getMathClient();
        EstimateKResult toSave = mathClient.estimateK(matrix.getData(), params.getMinK(), 
                params.getMaxK(), params.getMaxIter(), params.getRandomSeed(),
                params.getNeighbSize(), params.getMaxItems());
        provenance.get(0).withDescription("K estimation for K-Means clustering method")
                .withInputWsObjects(Arrays.asList(params.getInputMatrix()));
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(params.getOutWorkspace())
                .withObjects(Arrays.asList(new ObjectSaveData()
                .withType("KBaseFeatureValues.EstimateKResult").withName(params.getOutEstimateResult())
                .withData(new UObject(toSave)).withProvenance(provenance))));
    }

    public void estimateKNew(EstimateKParamsNew params,
            List<ProvenanceAction> provenance) throws Exception {
        ObjectData objData = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(params.getInputMatrix()))))
                .getData().get(0);
        BioMatrix matrix = objData.getData().asClassInstance(BioMatrix.class);
        ClusterServiceLocalClient mathClient = getMathClient();
        EstimateKResult toSave = mathClient.estimateKNew(matrix.getData(), params.getMinK(),
                params.getMaxK(), params.getCriterion(), params.getUsepam(),params.getAlpha(),
            params.getDiss(),params.getRandomSeed());
        provenance.get(0).withDescription("K estimation for K-Means clustering method")
                .withInputWsObjects(Arrays.asList(params.getInputMatrix()));
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(params.getOutWorkspace())
                .withObjects(Arrays.asList(new ObjectSaveData()
                .withType("KBaseFeatureValues.EstimateKResult").withName(params.getOutEstimateResult())
                .withData(new UObject(toSave)).withProvenance(provenance))));
    }
    
    public void clusterKMeans(ClusterKMeansParams params, 
            List<ProvenanceAction> provenance) throws Exception {
        ObjectData objData = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(params.getInputData()))))
                .getData().get(0);
        BioMatrix matrix = objData.getData().asClassInstance(BioMatrix.class);
        ClusterServiceLocalClient mathClient = getMathClient();
        ClusterResults res = null;
        if (params.getAlgorithm() != null && params.getAlgorithm().equals("Python Scikit-learn")) {
            ClusterServicePyLocalClient pyClient = new ClusterServicePyLocalClient(workDir);
            String binPath = config.get(KBaseFeatureValuesServer.CONFIG_PARAM_CLIENT_BIN_DIR);
            if (binPath != null)
                pyClient.setBinDir(new File(binPath));
            res = pyClient.clusterKMeans(matrix.getData(), params.getK(), null, null, null, null);
            List<Long> clusterLabels = res.getClusterLabels();
            for (int pos = 0; pos < clusterLabels.size(); pos++)
                clusterLabels.set(pos, 1 + (long)clusterLabels.get(pos));
            res = mathClient.calcClusterQualities(matrix.getData(), clusterLabels);
        } else {
            res = mathClient.clusterKMeans(matrix.getData(), params.getK(), 
                    params.getNStart(), params.getMaxIter(), params.getRandomSeed(),
                    params.getAlgorithm());
        }
        FeatureClusters toSave = new FeatureClusters().withOriginalData(params.getInputData());
        toSave.withFeatureClusters(clustersFromLabels(matrix.getData(), res));
        provenance.get(0).withDescription("K-Means clustering method")
                .withInputWsObjects(Arrays.asList(params.getInputData()));
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(params.getOutWorkspace())
                .withObjects(Arrays.asList(new ObjectSaveData()
                .withType("KBaseFeatureValues.FeatureClusters").withName(params.getOutClustersetId())
                .withData(new UObject(toSave)).withProvenance(provenance))));
    }

    public static List<LabeledCluster> clustersFromLabels(FloatMatrix2D matrixData, ClusterResults res) {
        Map<Long, LabeledCluster> labelToCluster = new LinkedHashMap<Long, LabeledCluster>();
        List<LabeledCluster> featureClusters = new ArrayList<LabeledCluster>();
        int minClusterLabel = -1;
        for (int featurePos = 0; featurePos < res.getClusterLabels().size(); featurePos++) {
            long clusterLabel = res.getClusterLabels().get(featurePos);
            if (clusterLabel < 0)
                continue;
            if (minClusterLabel < 0 || minClusterLabel > clusterLabel)
                minClusterLabel = (int)clusterLabel;
            LabeledCluster cluster = labelToCluster.get(clusterLabel);
            if (cluster == null) {
                cluster = new LabeledCluster().withIdToPos(new LinkedHashMap<String, Long>());
                labelToCluster.put(clusterLabel, cluster);
                featureClusters.add(cluster);
            }
            String featureLabel = matrixData.getRowIds().get(featurePos);
            cluster.getIdToPos().put(featureLabel, (long)featurePos);
        }
        if (res.getMeancor() != null || res.getMsecs() != null) {
            for (long clusterLabel : labelToCluster.keySet()) {
                LabeledCluster cluster = labelToCluster.get(clusterLabel);
                int clusterPos = (int)clusterLabel - minClusterLabel;
                if (res.getMeancor() != null)
                    cluster.withMeancor(noNaN(res.getMeancor().get(clusterPos)));
                if (res.getMsecs() != null)
                    cluster.withMsec(noNaN(res.getMsecs().get(clusterPos)));
            }
        }
        return featureClusters;
    }

    private static Double noNaN(Double value) {
        return (value == null || Double.isNaN(value)) ? null : value;
    }
    
    public void clusterHierarchical(ClusterHierarchicalParams params,
            List<ProvenanceAction> provenance) throws Exception {
        ObjectData objData = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(params.getInputData()))))
                .getData().get(0);
        BioMatrix matrix = objData.getData().asClassInstance(BioMatrix.class);
        ClusterServiceLocalClient mathClient = getMathClient();
        ClusterResults res = mathClient.clusterHierarchical(matrix.getData(), params.getDistanceMetric(), 
                params.getLinkageCriteria(), params.getFeatureHeightCutoff(), params.getMaxItems(), params.getAlgorithm());
        FeatureClusters toSave = new FeatureClusters().withOriginalData(params.getInputData())
                .withFeatureClusters(clustersFromLabels(matrix.getData(), res))
                .withFeatureDendrogram(res.getDendrogram());
        provenance.get(0).withDescription("Hierarchical clustering method")
                .withInputWsObjects(Arrays.asList(params.getInputData()));
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(params.getOutWorkspace())
                .withObjects(Arrays.asList(new ObjectSaveData()
                .withType("KBaseFeatureValues.FeatureClusters").withName(params.getOutClustersetId())
                .withData(new UObject(toSave)).withProvenance(provenance))));
    }

    public void clustersFromDendrogram(ClustersFromDendrogramParams params,
            List<ProvenanceAction> provenance) throws Exception {
        ObjectData objData = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(params.getInputData()))))
                .getData().get(0);
        FeatureClusters input = objData.getData().asClassInstance(FeatureClusters.class);
        // We don't actually load FeatureClusters object referred by params.getInputData() 
        // reference, we load matrix object listed in withObjRefPath() instead!
        ObjectData objData2 = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(params.getInputData())
                        .withObjRefPath(Arrays.asList(input.getOriginalData()))))).getData().get(0);
        BioMatrix matrix = objData2.getData().asClassInstance(BioMatrix.class);
        ClusterServiceLocalClient mathClient = getMathClient();
        ClusterResults res = mathClient.clustersFromDendrogram(matrix.getData(), 
                input.getFeatureDendrogram(), params.getFeatureHeightCutoff());
        FeatureClusters toSave = new FeatureClusters().withOriginalData(input.getOriginalData())
                .withFeatureClusters(clustersFromLabels(matrix.getData(), res))
                .withFeatureDendrogram(res.getDendrogram());
        provenance.get(0).withDescription("Clusters from dendrogram")
                .withInputWsObjects(Arrays.asList(params.getInputData()));
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(params.getOutWorkspace())
                .withObjects(Arrays.asList(new ObjectSaveData()
                .withType("KBaseFeatureValues.FeatureClusters").withName(params.getOutClustersetId())
                .withData(new UObject(toSave)).withProvenance(provenance))));
    }

    public void evaluateClustersetQuality(EvaluateClustersetQualityParams params, 
            List<ProvenanceAction> provenance) throws Exception {
        throw new IllegalStateException("Not yet implemented");
    }

    public void validateMatrix(ValidateMatrixParams params, 
            List<ProvenanceAction> provenance) throws Exception {
        throw new IllegalStateException("Not yet implemented");
    }

    public void correctMatrix(CorrectMatrixParams params, 
            List<ProvenanceAction> provenance) throws Exception {
        ObjectData objData = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(params.getInputData()))))
                .getData().get(0);
        String inputType = objData.getInfo().getE3();
        BioMatrix matrix = objData.getData().asClassInstance(BioMatrix.class);
        String transType = params.getTransformType();
        if (transType == null || !transType.equals("missing"))
            throw new IllegalStateException("Unsupported transformation type: " + transType);
        MatrixUtil.fillMissingValues(matrix.getData());
        String outMatrixId = params.getOutMatrixId();
        if (outMatrixId == null)
            outMatrixId = objData.getInfo().getE2();
        provenance.get(0).withDescription("Correcting matrix values")
                .withInputWsObjects(Arrays.asList(params.getInputData()));
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(params.getOutWorkspace())
                .withObjects(Arrays.asList(new ObjectSaveData()
                .withType(inputType).withName(outMatrixId)
                .withData(new UObject(matrix)).withProvenance(provenance))));
    }

    public void reconnectMatrixToGenome(ReconnectMatrixToGenomeParams params,
            List<ProvenanceAction> provenance) throws Exception {
        ObjectData objData = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(params.getInputData()))))
                .getData().get(0);
        String inputType = objData.getInfo().getE3();
        BioMatrix matrix = objData.getData().asClassInstance(BioMatrix.class);
        Genome genome = MatrixUtil.loadGenomeFeatures(token, null, params.getGenomeRef());
        matrix.setFeatureMapping(MatrixUtil.constructFeatureMapping(matrix.getData(), genome));
        matrix.setGenomeRef(params.getGenomeRef());
        String outMatrixId = params.getOutMatrixId();
        if (outMatrixId == null)
            outMatrixId = objData.getInfo().getE2();
        provenance.get(0).withDescription("Reconnection of matrix rows to genome features")
                .withInputWsObjects(Arrays.asList(params.getInputData()));
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(params.getOutWorkspace())
                .withObjects(Arrays.asList(new ObjectSaveData()
                .withType(inputType).withName(outMatrixId)
                .withData(new UObject(matrix)).withProvenance(provenance))));
    }

    @SuppressWarnings("unchecked")
    public void buildFeatureSet(BuildFeatureSetParams params,
            List<ProvenanceAction> provenance) throws Exception {
        /*
            Here is definition of KBaseCollections.FeatureSet type:
            typedef structure {
                string description;
                mapping<feature_id, list<genome_ref>> elements;
            } FeatureSet; 
        */
        Map<String, List<String>> elements = new LinkedHashMap<String, List<String>>();
        if (params.getBaseFeatureSet() != null) {
            Map<String, Object> baseMap = getWsClient().getObjects2(new GetObjects2Params().withObjects(
                    Arrays.asList(new ObjectSpecification().withRef(params.getBaseFeatureSet()))))
                    .getData().get(0).getData().asClassInstance(Map.class);
            Map<String, List<String>> baseElements = (Map<String, List<String>>)baseMap.get("elements");           
            if (baseElements != null)
                elements.putAll(baseElements);
        }
        GenomeDataV1 genomeObj = MatrixUtil.loadGenome(token, null, params.getGenome(), 
                Collections.<String>emptyList(), Arrays.asList("id"));
        Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, Map<String,String>> info = 
                genomeObj.getInfo();
        String genomeRef = info.getE7() + "/" + info.getE1() + "/" + info.getE5();
        Genome genome = genomeObj.getData();
        List<Feature> featureList = genome.getFeatures();
        Set<String> featureIdSet = new HashSet<String>();
        for (Feature feature : featureList) {
            String featureId = feature.getId();
            featureIdSet.add(featureId);
        }
        String featureIdsText = params.getFeatureIds();
        if (featureIdsText == null) {
            featureIdsText = "";
        } else {
            featureIdsText = featureIdsText.trim();
        }
        if (params.getFeatureIdsCustom() != null) {
            String featureIdsText2 = params.getFeatureIdsCustom().trim();
            if (featureIdsText2.length() > 0) {
                if (featureIdsText.length() > 0)
                    featureIdsText += "\n";
                featureIdsText += featureIdsText2;
            }
        }
        List<String> lostFeatureIds = new ArrayList<String>();
        BufferedReader br = new BufferedReader(new StringReader(featureIdsText));
        while (true) {
            String l = br.readLine();
            if (l == null)
                break;
            String[] parts = l.split(Pattern.quote(","));
            for (String part : parts) {
                String featureId = part.trim();
                if (featureId != null) {
                    if (!featureIdSet.contains(featureId)) {
                        lostFeatureIds.add(featureId);
                    } else {
                        List<String> genomes = elements.get(featureId);
                        if (genomes == null) {
                            genomes = new ArrayList<String>();
                            elements.put(featureId, genomes);
                        }
                        addToListOnce(genomes, genomeRef);
                    }
                }
            }
        }
        br.close();
        if (lostFeatureIds.size() > 0)
            throw new IllegalStateException("Some features are not found: " + lostFeatureIds);
        List<String> provRefs = new ArrayList<String>();
        for (List<String> genomeRefs : elements.values())
            for (String aGenomeRef : genomeRefs)
                addToListOnce(provRefs, aGenomeRef);
        Map<String, Object> featureSet = new LinkedHashMap<String, Object>();
        featureSet.put("description", params.getDescription());
        featureSet.put("elements", elements);
        if (params.getBaseFeatureSet() != null)
            provRefs.add(params.getBaseFeatureSet());
        provenance.get(0).withDescription("Reconnection of matrix rows to genome features")
                .withInputWsObjects(provRefs);
        getWsClient().saveObjects(new SaveObjectsParams().withWorkspace(params.getOutWorkspace())
                .withObjects(Arrays.asList(new ObjectSaveData()
                .withType("KBaseCollections.FeatureSet").withName(params.getOutputFeatureSet())
                .withData(new UObject(featureSet)).withProvenance(provenance))));
    }
    
    private static void addToListOnce(List<String> list, String item) {
        if (!list.contains(item))
            list.add(item);
    }
    
    public MatrixDescriptor getMatrixDescriptor(GetMatrixDescriptorParams params) throws Exception {
        WorkspaceClient wsCl = getWsClient();
        ObjectData obj = wsCl.getObjects2(new GetObjects2Params().withObjects(Arrays.asList(
                new ObjectSpecification().withRef(params.getInputData()).withIncluded(
                        Arrays.asList("data/col_ids", "data/row_ids", "genome_ref", "scale", 
                                "type", "row_normalization", "col_normalization")))))
                                .getData().get(0);
        BioMatrix matrix = obj.getData().asClassInstance(BioMatrix.class);
        String matrixId = obj.getInfo().getE2();
        String matrixName = obj.getInfo().getE2();
        String matrixDescription = obj.getInfo().getE2();
        String genomeId = null;
        String genomeName = null;
        int rowCount = -1;
        int colCount = -1;
        String scale = (String)matrix.getAdditionalProperties().get("scale");
        String type = (String)matrix.getAdditionalProperties().get("type");
        String rowNormalization = (String)matrix.getAdditionalProperties().get("row_normalization");
        String colNormalization = (String)matrix.getAdditionalProperties().get("col_normalization");
        FloatMatrix2D data = matrix.getData();
        if (data != null) {
            List<String> rowIds = data.getRowIds();
            rowCount = rowIds.size();
            List<String> colIds = data.getColIds();
            colCount = colIds.size();
        } else {
            Map<String, String> meta = obj.getInfo().getE11();
            if (meta.containsKey("feature_count"))
                rowCount = Integer.parseInt(meta.get("feature_count"));
            if (meta.containsKey("condition_count"))
                colCount = Integer.parseInt(meta.get("condition_count"));
        }
        String genomeRef = (String)matrix.getGenomeRef();
        if (genomeRef != null) {
            GenomeDataV1 genomeRet = loadGenomeDynamic(token, params.getInputData(), genomeRef, 
                    Arrays.asList("scientific_name"), null);
            genomeId = genomeRet.getInfo().getE2();
            genomeName = genomeRet.getData().getScientificName();
        }
        return new MatrixDescriptor().withMatrixId(matrixId).withMatrixName(matrixName)
                .withMatrixDescription(matrixDescription).withGenomeId(genomeId)
                .withGenomeName(genomeName).withRowsCount((long)rowCount)
                .withColumnsCount((long)colCount).withScale(scale).withType(type)
                .withRowNormalization(rowNormalization).withColNormalization(colNormalization);
    }
    
    
    public GenomeDataV1 loadGenomeDynamic(AuthToken token, String matrixRef, 
            String genomeRef, List<String> includedFields, 
            List<String> includedFeatureFields) throws Exception {
        URL swUrl = new URL(getSrvWizUrl());
        GenomeAnnotationAPIServiceClient gaapi = 
                new GenomeAnnotationAPIServiceClient(swUrl, token);
        gaapi.setIsInsecureHttpConnectionAllowed(true);
        List<String> refPathToGenome = matrixRef == null ? null : Arrays.asList(matrixRef);
        List<GenomeSelectorV1> genomes = Arrays.asList(new GenomeSelectorV1().withRef(genomeRef)
                .withRefPathToGenome(refPathToGenome));
        return gaapi.getGenomeV1(new GetGenomeParamsV1().withGenomes(genomes)
                .withIncludedFeatureFields(includedFeatureFields)
                .withIncludedFields(includedFields)).getGenomes().get(0);
    }

    
	public MatrixStat getMatrixStat(GetMatrixStatParams params) throws Exception {

		MatrixStat matrixStat = new MatrixStat();
		
		// Load matrix and genome data
		MatrixGenomeLoader mgl = new MatrixGenomeLoader();
		mgl.load(params.getInputData());

		// Build matrix descriptor		
        matrixStat.setMtxDescriptor(buildMatrixDescriptor(mgl));
        
		int[] rowIndeces = buildIndeces(null, null, mgl.matrix.getData().getRowIds());
		int[] colIndeces = buildIndeces(null, null, mgl.matrix.getData().getColIds());
        
        // Build row and descriptors        
        matrixStat.setRowDescriptors(buildRowDescriptors(mgl, rowIndeces ));
        matrixStat.setColumnDescriptors(buildColumnDescriptors(mgl, colIndeces));        
        
        // Collect statistics
        matrixStat.setRowStats(FloatMatrix2DUtil.getRowsStat(mgl.matrix.getData(), null, null, false));
        matrixStat.setColumnStats(FloatMatrix2DUtil.getColumnsStat(mgl.matrix.getData(), null, null, false));

		return matrixStat;
	}    
    

	public SubmatrixStat getSubmatrixStat(GetSubmatrixStatParams params) throws Exception {
		SubmatrixStat submatrixStat = new SubmatrixStat();

		// Load matrix and genome data
		MatrixGenomeLoader mgl = new MatrixGenomeLoader();
		mgl.load(params.getInputData());

		// Build matrix descriptor		
		submatrixStat.setMtxDescriptor(buildMatrixDescriptor(mgl));
        
		int[] rowIndeces = buildIndeces(params.getRowIndeces(), params.getRowIds(), mgl.matrix.getData().getRowIds());
		int[] colIndeces = buildIndeces(params.getColumnIndeces(), params.getColumnIds(), mgl.matrix.getData().getColIds());
		
        // Build row and descriptors        
		submatrixStat.setRowDescriptors(buildRowDescriptors(mgl, rowIndeces));
		submatrixStat.setColumnDescriptors(buildColumnDescriptors(mgl, colIndeces));        
		
		
        // Stub for parameters		
		GetMatrixSetStatParams matrixSetStatParams = new GetMatrixSetStatParams()
			.withFlAvgs(1L)
			.withFlMaxs(1L)
			.withFlMins(1L)
			.withFlMaxs(1L)
			.withFlStds(1L)
			.withFlMissingValues(1L);
		
        // row and column set stats		
		if( toBoolean(params.getFlRowSetStats()) ) {
			matrixSetStatParams
				.withItemIndecesFor(toListLong(rowIndeces))
				.withItemIndecesOn(toListLong(colIndeces));			
			submatrixStat.setRowSetStats(FloatMatrix2DUtil.getRowsSetStat(mgl.matrix.getData(), matrixSetStatParams));	
		}
		
		if( toBoolean(params.getFlColumnSetStat()) ) {
			matrixSetStatParams
				.withItemIndecesFor(toListLong(colIndeces))
				.withItemIndecesOn(toListLong(rowIndeces));			
			submatrixStat.setColumnSetStat(FloatMatrix2DUtil.getColumnsSetStat(mgl.matrix.getData(), matrixSetStatParams));
		}
		
        // mtx row and column set stats		
		if( toBoolean(params.getFlMtxRowSetStat()) ) {
			int[] mtxColIndeces = buildIndeces(null, null, mgl.matrix.getData().getColIds());
			matrixSetStatParams
				.withItemIndecesFor( toListLong(rowIndeces) )
				.withItemIndecesOn( toListLong(mtxColIndeces));			
			submatrixStat.setMtxRowSetStat(FloatMatrix2DUtil.getRowsSetStat(mgl.matrix.getData(), matrixSetStatParams));				
		}
		if( toBoolean(params.getFlMtxColumnSetStat()) ) {
			int[] mtxRowIndeces = buildIndeces(null, null, mgl.matrix.getData().getRowIds());
			matrixSetStatParams
				.withItemIndecesFor( toListLong(colIndeces))
				.withItemIndecesOn( toListLong(mtxRowIndeces));			
			submatrixStat.setMtxColumnSetStat(FloatMatrix2DUtil.getColumnsSetStat(mgl.matrix.getData(), matrixSetStatParams));				
		}
				
		// Pairwise comparison
		if( toBoolean(params.getFlRowPairwiseCorrelation()) ){
			int[] mtxColIndeces = buildIndeces(null, null, mgl.matrix.getData().getColIds());
			submatrixStat.setRowPairwiseCorrelation(FloatMatrix2DUtil.geRowstPairwiseComparison(mgl.matrix.getData(), rowIndeces, mtxColIndeces));
		}
		
		
        // values		
		if( toBoolean(params.getFlValues()) ) {
			submatrixStat.setValues(FloatMatrix2DUtil.getSubmatrixValues(mgl.matrix.getData(), rowIndeces, colIndeces ));
		}
		
		return submatrixStat;  	
	}

	private boolean toBoolean(Long value) {
		return value != null && value == 1;
	}


	private MatrixDescriptor buildMatrixDescriptor(MatrixGenomeLoader mgl){
        
        return new MatrixDescriptor()
        	.withColNormalization(mgl.matrix.getColNormalization())
        	.withColumnsCount((long) mgl.matrix.getData().getColIds().size())
        	.withGenomeId(mgl.genomeId)
        	.withGenomeName(mgl.genomeName)
        	.withMatrixDescription(mgl.matrix.getDescription())
        	.withMatrixId(mgl.matrixData.getInfo().getE2())
        	.withMatrixName(mgl.matrixData.getInfo().getE2())
        	.withRowNormalization(mgl.matrix.getRowNormalization())
        	.withRowsCount((long) mgl.matrix.getData().getRowIds().size())
        	.withScale(mgl.matrix.getScale())
        	.withType(mgl.matrix.getType());    
	}
	
    private Hashtable<String, Feature> buildFeatureId2FeatureHash(
			List<Feature> features) {
    	Hashtable<String, Feature> featureId2Feature = new Hashtable<String, Feature>();
    	for(Feature f: features){
    		featureId2Feature.put(f.getId(), f);    		
    	}    	
		return featureId2Feature;
	}

	private List<ItemDescriptor> buildColumnDescriptors(MatrixGenomeLoader mgl, int[] colIndeces) {
    	List<ItemDescriptor> descriptors = new ArrayList<ItemDescriptor>();
    	
		List<String> mtxColIds = mgl.matrix.getData().getColIds();
    	
    	
    	// We do not have condition mapping now, so we will use just colIds...

    	for(int ci = 0 ; ci < colIndeces.length; ci++){
    		int cIndex = colIndeces[ci];
    		String cId = mtxColIds.get(cIndex);
    		String name = cId;
    		ItemDescriptor desc = new ItemDescriptor()
    			.withDescription("")
    			.withId(cId)
    			.withIndex((long)cIndex)
    			.withName(name);
    		descriptors.add(desc);
    	}
    	
		return descriptors;
	}

	private List<ItemDescriptor> buildRowDescriptors(MatrixGenomeLoader mgl, int[] rowIndeces) {
    	List<ItemDescriptor> descriptors = new ArrayList<ItemDescriptor>();
		
		List<String> mtxRowIds = mgl.matrix.getData().getRowIds();    	
    	for(int ri = 0 ; ri < rowIndeces.length; ri++){
    		int rIndex = rowIndeces[ri];
    		String rId = mtxRowIds.get(rIndex);
    		
    		String function = "";
    		String name = "";
            Hashtable<String,String> props = new Hashtable<String,String>();
    		
    		//TODO implement general approach to extract required properties. For now just function
    		if (mgl.featureId2Feature != null) {
    		    String featureId = null;
    		    if (mgl.matrix.getFeatureMapping() != null) 
    		        featureId = mgl.matrix.getFeatureMapping().get(rId);
    		    if (featureId == null)
    		        featureId = rId;
    		    Feature feature = mgl.featureId2Feature.get(featureId);

    		    if(feature != null){
    		        function = feature.getFunction();
    		        props.put("function", function != null ? function : "");
    		        name = feature.getAliases() != null? StringUtils.join(feature.getAliases(), "; ") : "";
    		    }
    		}    		
    		
    		ItemDescriptor desc = new ItemDescriptor()
    			.withDescription("")
    			.withId(rId)
    			.withIndex((long)rIndex)
    			.withName(name)
    			.withProperties(props);
    		descriptors.add(desc);    		
    	}
    	return descriptors;
	}

	private List<Long> toListLong(int[] values){
		List<Long> ll = new ArrayList<Long>(values.length);
		for(int val: values){
			ll.add((long) val);
		}
		return ll;
	}
	
	private int[] buildIndeces(List<Long> rowIndeces, List<String> rowIds, List<String> mtxRowIds) {		
		int[] indeces = null;
		if(rowIndeces != null && rowIndeces.size() > 0){
			indeces = new int[rowIndeces.size()];
			for(int i = 0; i < rowIndeces.size(); i++){
				indeces[i] = rowIndeces.get(i).intValue();
			}
		} else if(rowIds != null && rowIds.size() > 0){
			Hashtable<String,Integer> id2index = new Hashtable<String,Integer>();
			for(int i = 0 ; i < mtxRowIds.size(); i++){
				id2index.put(mtxRowIds.get(i), i);
			}
			indeces = new int[rowIds.size()];
			for(int i = 0; i < rowIds.size(); i++){
				indeces[i] = id2index.get(rowIds.get(i)).intValue();
			}
		} else{
			indeces = new int[mtxRowIds.size()];
			for(int i = 0 ; i < indeces.length; i++){
				indeces[i] = i;
			}
		}
		
		
		return indeces;
	}

	public  List<ItemStat> getMatrixRowsStat(GetMatrixItemsStatParams params) throws Exception {
        //TODO can be further optimized by getting subobjects
		System.out.println("params: " + params);
        ExpressionMatrix matrix = getExpressionMatrix(params.getInputData());
		return FloatMatrix2DUtil.getRowsStat(matrix.getData(), params.getItemIndecesFor() , params.getItemIndecesOn(), params.getFlIndecesOn() == 1);
	}	
	
	public  List<ItemStat> getMatrixColumnsStat(GetMatrixItemsStatParams params) throws Exception {
        //TODO can be further optimized by getting subobjects
		
		System.out.println("params: " + params);
        ExpressionMatrix matrix = getExpressionMatrix(params.getInputData());
		return FloatMatrix2DUtil.getColumnsStat(matrix.getData(), params.getItemIndecesFor() , params.getItemIndecesOn(), params.getFlIndecesOn() == 1);
	}	
	
	public List<ItemSetStat> getMatrixRowSetsStat(GetMatrixSetsStatParams params) throws Exception {
		List<ItemSetStat> setStats = new ArrayList<ItemSetStat>();
		
		ExpressionMatrix matrix;
		String matrixRef = "";
		for(GetMatrixSetStatParams setStatParam: params.getParams()){
			if(!matrixRef.equals(setStatParam.getInputData())){
				matrixRef = setStatParam.getInputData();
				matrix = getExpressionMatrix(matrixRef);
				
				ItemSetStat setStat = FloatMatrix2DUtil.getRowsSetStat(matrix.getData(), setStatParam);
				setStats.add(setStat);
			}
		}		
		return setStats;
	}
	
	public List<ItemSetStat> getMatrixColumnSetsStat(GetMatrixSetsStatParams params) throws Exception {
		List<ItemSetStat> setStats = new ArrayList<ItemSetStat>();
		
		ExpressionMatrix matrix;
		String matrixRef = "";
		for(GetMatrixSetStatParams setStatParam: params.getParams()){
			if(!matrixRef.equals(setStatParam.getInputData())){
				matrixRef = setStatParam.getInputData();
				matrix = getExpressionMatrix(matrixRef);
				
				ItemSetStat setStat = FloatMatrix2DUtil.getColumnsSetStat(matrix.getData(), setStatParam);
				setStats.add(setStat);
			}
		}		
		return setStats;
	}

	private ExpressionMatrix getExpressionMatrix(String mtxRef) throws Exception{
		ObjectData matrixData = getExpressionMatrixObject(mtxRef);
        	
		ExpressionMatrix matrix = (ExpressionMatrix)  matrixData
			.getData()
			.asClassInstance(ExpressionMatrix.class);
		return matrix;
	}
	
	private ObjectData getExpressionMatrixObject(String mtxRef) throws Exception{
        WorkspaceClient wsClient = getWsClient();
		return wsClient.getObjects2(new GetObjects2Params().withObjects(
                Arrays.asList(new ObjectSpecification().withRef(mtxRef)))).getData().get(0);
	}	
	
	private File getScratchDir() {
	    File scratchDir = new File(config.get(KBaseFeatureValuesServer.CONFIG_PARAM_SCRATCH));
	    if (!scratchDir.exists())
	        scratchDir.mkdirs();
	    return scratchDir;
	}

	public TsvFileToMatrixOutput tsvFileToMatrix(TsvFileToMatrixParams params) throws Exception {
	    File tmpDir = Files.createTempDirectory(getScratchDir().toPath(), "FromShock").toFile();
	    try {
	        URL callbackUrl = new URL(System.getenv("SDK_CALLBACK_URL"));
	        DataFileUtilClient dataFileUtil = new DataFileUtilClient(callbackUrl, token);
	        dataFileUtil.setIsInsecureHttpConnectionAllowed(true);
            File inputFile;
	        if (params.getInputShockId() != null) {
	            String fileName = dataFileUtil.shockToFile(
	                    new ShockToFileParams().withShockId(params.getInputShockId())
	                    .withFilePath(tmpDir.getCanonicalPath())).getNodeFileName();
	            inputFile = new File(tmpDir, fileName);
	        } else if (params.getInputFilePath() != null) {
	            inputFile = new File(params.getInputFilePath());
	        } else {
	            throw new IllegalStateException("One of input_file_path or input_shock_id " +
	            		"parameters should be defined");
	        }
	        boolean fillMissingValues = params.getFillMissingValues() != null &&
	                params.getFillMissingValues() == 1L;
	        String dataType = params.getDataType();
	        if (dataType == null)
	            dataType = "unknown";
	        String dataScale = params.getDataScale();
	        if (dataScale == null)
	            dataScale = "1.0";
	        ExpressionMatrix matrix = ExpressionUploader.parse(params.getGenomeRef(), inputFile,
	                ExpressionUploader.FORMAT_TYPE_SIMPLE, fillMissingValues, dataType, 
	                dataScale, token);
	        File outputFile = File.createTempFile("matrix_", ".json", tmpDir);
	        UObject.getMapper().writeValue(outputFile, matrix);
	        Long wsId = dataFileUtil.wsNameToId(params.getOutputWsName());
	        Tuple11<Long, String, String, String, Long, String, Long, String, String, Long, 
	                Map<String,String>> info = dataFileUtil.saveObjects(
	                        new datafileutil.SaveObjectsParams().withId(wsId)
	                        .withObjects(Arrays.asList(new datafileutil.ObjectSaveData()
	                        .withType("KBaseFeatureValues.ExpressionMatrix")
	                        .withName(params.getOutputObjName())
	                        .withData(new UObject(matrix))))).get(0);
	        return new TsvFileToMatrixOutput().withOutputMatrixRef(
	                info.getE7() + "/" + info.getE1() + "/" + info.getE5());
	    } finally {
	        FileUtils.deleteQuietly(tmpDir);
	    }
	}
	
    public MatrixToTsvFileOutput matrixToTsvFile(MatrixToTsvFileParams params) throws Exception {
        File tmpDir = Files.createTempDirectory(getScratchDir().toPath(), "FromShock").toFile();
        try {
            MatrixToTsvFileOutput ret = new MatrixToTsvFileOutput();
            File matrixFile = new File(tmpDir, "matrix.tsv");
            try (PrintWriter pw = new PrintWriter(matrixFile)) {
                ExpressionDownloader.generate(getWsUrl(), params.getInputRef(), token, pw);
            }
            if (params.getToShock() != null && params.getToShock() == 1L) {
                URL callbackUrl = new URL(System.getenv("SDK_CALLBACK_URL"));
                DataFileUtilClient dataFileUtil = new DataFileUtilClient(callbackUrl, token);
                dataFileUtil.setIsInsecureHttpConnectionAllowed(true);
                String shockId = dataFileUtil.packageForDownload(
                        new PackageForDownloadParams().withFilePath(matrixFile.getCanonicalPath())
                        .withWsRefs(Arrays.asList(params.getInputRef()))).getShockId();
                ret.withShockId(shockId);
            } else {
                File target = new File(params.getFilePath());
                if (target.exists() && target.isDirectory())
                    target = new File(target, matrixFile.getName());
                FileUtils.copyFile(matrixFile, target);
                ret.withFilePath(target.getCanonicalPath());
            }
            return ret;
        } finally {
            FileUtils.deleteQuietly(tmpDir);
        }
    }
    
    public ExportMatrixOutput exportMatrix(ExportMatrixParams params) throws Exception {
        return new ExportMatrixOutput().withShockId(matrixToTsvFile(
                new MatrixToTsvFileParams().withInputRef(params.getInputRef())
                .withToShock(1L)).getShockId());
    }

    public ClustersToFileOutput clustersToFile(ClustersToFileParams params) 
            throws Exception {
        File tmpDir = Files.createTempDirectory(getScratchDir().toPath(), "FromShock").toFile();
        try {
            ClustersToFileOutput ret = new ClustersToFileOutput();
            String ext = params.getFormat() == null ? "tsv" : params.getFormat().toLowerCase();
            File clustFile = new File(tmpDir, "clusters." + ext);
            try (PrintWriter pw = new PrintWriter(clustFile)) {
                FeatureClustersDownloader.generate(getWsUrl(), params.getInputRef(), 
                        params.getFormat(), token, pw);
            }
            if (params.getToShock() != null && params.getToShock() == 1L) {
                URL callbackUrl = new URL(System.getenv("SDK_CALLBACK_URL"));
                DataFileUtilClient dataFileUtil = new DataFileUtilClient(callbackUrl, token);
                dataFileUtil.setIsInsecureHttpConnectionAllowed(true);
                String shockId = dataFileUtil.packageForDownload(
                        new PackageForDownloadParams().withFilePath(clustFile.getCanonicalPath())
                        .withWsRefs(Arrays.asList(params.getInputRef()))).getShockId();
                ret.withShockId(shockId);
            } else {
                File target = new File(params.getFilePath());
                if (target.exists() && target.isDirectory())
                    target = new File(target, clustFile.getName());
                FileUtils.copyFile(clustFile, target);
                ret.withFilePath(target.getCanonicalPath());
            }
            return ret;
        } finally {
            FileUtils.deleteQuietly(tmpDir);
        }
    }
    
    public ExportClustersTsvOutput exportClustersTsv(ExportClustersTsvParams params) throws Exception {
        return new ExportClustersTsvOutput().withShockId(clustersToFile(
                new ClustersToFileParams().withInputRef(params.getInputRef())
                .withToShock(1L).withFormat("TSV")).getShockId());
    }

    public ExportClustersSifOutput exportClustersSif(ExportClustersSifParams params) throws Exception {
        return new ExportClustersSifOutput().withShockId(clustersToFile(
                new ClustersToFileParams().withInputRef(params.getInputRef())
                .withToShock(1L).withFormat("SIF")).getShockId());
    }

    class MatrixGenomeLoader{
        ObjectData matrixData;
        ExpressionMatrix matrix;
        String genomeId = null;
        String genomeName = null;
        Hashtable<String,Feature> featureId2Feature = null;
        
        
        public void load(String mtxRef) throws Exception{
            //WorkspaceClient wsClient = getWsClient();
            // We should go through dynamic service.

            // Get expression matrix
            matrixData = getExpressionMatrixObject(mtxRef);
            matrix = (ExpressionMatrix)  matrixData
                .getData()
                .asClassInstance(ExpressionMatrix.class);
                                        
            if (matrix.getGenomeRef() != null) {
                GenomeDataV1 genomeRet = loadGenomeDynamic(token, mtxRef, matrix.getGenomeRef(), 
                        Arrays.asList("id", "scientific_name"), Arrays.asList("id", "function", 
                                "aliases"));
                Genome genome = genomeRet.getData();
                genomeId = genome.getId();
                genomeName = genome.getScientificName();  
                List<Feature> features = genome.getFeatures(); 
//                  Gives: java.lang.TypeNotPresentException: Type us.kbase.common.service.Tuple3 not present            
//                List<Feature> features = new ArrayList<Feature>();  
                featureId2Feature = buildFeatureId2FeatureHash(features);            
            }           
        }

    }

	@JsonInclude(JsonInclude.Include.NON_NULL)
    public static class BioMatrix {
        @JsonProperty("genome_ref")
        private java.lang.String genomeRef;
        @JsonProperty("feature_mapping")
        private Map<String, String> featureMapping;
        @JsonProperty("data")
        private FloatMatrix2D data;
        private Map<java.lang.String, Object> additionalProperties = new HashMap<java.lang.String, Object>();
        
        @JsonProperty("genome_ref")
        public java.lang.String getGenomeRef() {
            return genomeRef;
        }

        @JsonProperty("genome_ref")
        public void setGenomeRef(java.lang.String genomeRef) {
            this.genomeRef = genomeRef;
        }

        @JsonProperty("feature_mapping")
        public Map<String, String> getFeatureMapping() {
            return featureMapping;
        }

        @JsonProperty("feature_mapping")
        public void setFeatureMapping(Map<String, String> featureMapping) {
            this.featureMapping = featureMapping;
        }

        @JsonProperty("data")
        public FloatMatrix2D getData() {
            return data;
        }

        @JsonProperty("data")
        public void setData(FloatMatrix2D data) {
            this.data = data;
        }

        @JsonAnyGetter
        public Map<java.lang.String, Object> getAdditionalProperties() {
            return this.additionalProperties;
        }

        @JsonAnySetter
        public void setAdditionalProperties(java.lang.String name, Object value) {
            this.additionalProperties.put(name, value);
        }

    }

}
