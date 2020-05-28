package us.kbase.kbasefeaturevalues;

import genomeannotationapi.GenomeAnnotationAPIClient;
import genomeannotationapi.GenomeDataV1;
import genomeannotationapi.GenomeSelectorV1;
import genomeannotationapi.GetGenomeParamsV1;

import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.kbase.auth.AuthToken;

import kbasegenomes.Feature;
import kbasegenomes.Genome;

public class MatrixUtil {

    public static Genome loadGenomeFeatures(AuthToken token, String matrixRef, 
            String genomeRef) throws Exception {
        return loadGenome(token, matrixRef, genomeRef, Collections.<String>emptyList(),
                Arrays.asList("id", "aliases")).getData();
    }
    
    public static GenomeDataV1 loadGenome(AuthToken token, String matrixRef, String genomeRef,
            List<String> includedFields, List<String> includedFeatureFields) throws Exception {

        URL callbackUrl = new URL(System.getenv("SDK_CALLBACK_URL"));
        GenomeAnnotationAPIClient gaapi = new GenomeAnnotationAPIClient(callbackUrl, token);
        gaapi.setIsInsecureHttpConnectionAllowed(true);
        List<String> refPathToGenome = matrixRef == null ? null : Arrays.asList(matrixRef);
        List<GenomeSelectorV1> genomes = Arrays.asList(new GenomeSelectorV1().withRef(genomeRef)
                .withRefPathToGenome(refPathToGenome));
        return gaapi.getGenomeV1(new GetGenomeParamsV1().withGenomes(genomes)
                .withIncludedFeatureFields(includedFeatureFields)
                .withIncludedFields(includedFields)).getGenomes().get(0);
    }
    
    public static Map<String, String> constructFeatureMapping(FloatMatrix2D matrix, 
            Genome genome) {
        List<String> rowIds = matrix.getRowIds();
        Map<String, String> featureMapping = null; // maps row ID to genome feature ID
        Set<String> rowIdSet = new HashSet<String>(rowIds);
        featureMapping = new LinkedHashMap<String, String>();
        for (Feature feature : genome.getFeatures()) {
            String id = feature.getId();
            if (rowIdSet.contains(id)) {
                featureMapping.put(id, id);
                rowIdSet.remove(id);
            }
        }
        if (rowIdSet.size() > 0) {
            for (Feature feature: genome.getFeatures()) {
                String id = feature.getId();
                if (feature.getAliases() != null) {
                    for (String alias : feature.getAliases()) {
                        if (rowIdSet.contains(alias)) {
                            featureMapping.put(alias, id);
                            rowIdSet.remove(alias);
                        }
                    }
                }
            }
        }
        return featureMapping;
    }
    
    public static void fillMissingValues(FloatMatrix2D matrix) {
        List<List<Double>> values = matrix.getValues();
        double avg = 0;
        int count = 0;
        boolean thereAreMissingVals = false;
        for (List<Double> row : values) {
            for (Double value : row) {
                if (value == null) {
                    thereAreMissingVals = true;
                } else {
                    avg += value;
                    count++;
                }
            }
        }
        if (thereAreMissingVals) {
            if (count > 0)
                avg /= count;
            for (List<Double> row : values) {
                for (int pos = 0; pos < row.size(); pos++) {
                    if (row.get(pos) == null)
                        row.set(pos, avg);
                }
            }
        }
    }

}
