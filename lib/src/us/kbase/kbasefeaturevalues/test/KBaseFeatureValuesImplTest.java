package us.kbase.kbasefeaturevalues.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

import us.kbase.clusterservice.ClusterResults;
import us.kbase.kbasefeaturevalues.FloatMatrix2D;
import us.kbase.kbasefeaturevalues.KBaseFeatureValuesImpl;
import us.kbase.kbasefeaturevalues.LabeledCluster;

public class KBaseFeatureValuesImplTest {
    
    @Test
    public void test() throws Exception {
        FloatMatrix2D matrixData = getSampleMatrix();
        ClusterResults res = new ClusterResults().withClusterLabels(
                Arrays.asList(1L, -1L, -1L, -1L, 2L, 2L, 2L))
                .withMeancor(Arrays.asList(Double.NaN, 0.9999))
                .withMsecs(Arrays.asList(Double.NaN, 0.0062));
        List<LabeledCluster> clusters = KBaseFeatureValuesImpl.clustersFromLabels(matrixData, res);
        Assert.assertEquals(2, clusters.size());
    }

    private static FloatMatrix2D getSampleMatrix() {
        List<List<Double>> values = new ArrayList<List<Double>>();
        values.add(Arrays.asList(13.0, 2.0, 3.0));
        values.add(Arrays.asList(10.9, 1.95, 2.9));
        values.add(Arrays.asList(2.45, 13.4, 4.4));
        values.add(Arrays.asList(2.5, 11.5, 3.55));
        values.add(Arrays.asList(-1.05, -2.0, -14.0));
        values.add(Arrays.asList(-1.2, -2.25, -13.2));
        values.add(Arrays.asList(-1.1, -2.1, -15.15));
        return new FloatMatrix2D().withValues(values)
                .withRowIds(Arrays.asList("g1", "g2", "g3", "g4", "g5", "g6", "g7"))
                .withColIds(Arrays.asList("c1", "c2", "c3"));
    }
}
