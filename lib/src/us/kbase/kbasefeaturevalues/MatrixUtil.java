package us.kbase.kbasefeaturevalues;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import us.kbase.common.service.UObject;
import us.kbase.workspace.SubObjectIdentity;
import us.kbase.workspace.WorkspaceClient;

public class MatrixUtil {

    @SuppressWarnings("unchecked")
    public static Map<String, Object> loadGenomeFeatures(WorkspaceClient cl, 
            String genomeRef) throws Exception {
        //UObject genomeObj = cl.getObjects(Arrays.asList(
        //        new ObjectIdentity().withRef(genomeRef))).get(0).getData();
        UObject genomeObj = cl.getObjectSubset(Arrays.asList(
                new SubObjectIdentity().withRef(genomeRef).withIncluded(
                        Arrays.asList("features/[*]/id", 
                                "features/[*]/aliases")))).get(0).getData();
        return genomeObj.asClassInstance(Map.class);
    }
    
    @SuppressWarnings("unchecked")
    public static Map<String, String> constructFeatureMapping(FloatMatrix2D matrix, 
            Map<String, Object> genome) {
        List<String> rowIds = matrix.getRowIds();
        Map<String, String> featureMapping = null; // maps row ID to genome feature ID
        Set<String> rowIdSet = new HashSet<String>(rowIds);
        featureMapping = new LinkedHashMap<String, String>();
        List<Map<String, Object>> features = 
                (List<Map<String, Object>>)genome.get("features");
        for (Map<String, Object> feature: features) {
            String id = (String)feature.get("id");
            if (rowIdSet.contains(id)) {
                featureMapping.put(id, id);
                rowIdSet.remove(id);
            }
        }
        if (rowIdSet.size() > 0) {
            for (Map<String, Object> feature: features) {
                String id = (String)feature.get("id");
                if (feature.containsKey("aliases")) {
                    for (String alias : (List<String>)feature.get("aliases")) {
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
