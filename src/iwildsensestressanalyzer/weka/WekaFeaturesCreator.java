package iwildsensestressanalyzer.weka;

import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class WekaFeaturesCreator {
 
    /**
     * Adds average and standard deviation of the calculated statistics 
     * to the set of features, or two null values if it was not possible 
     * to calculate statistics
     * @param features the set of features
     * @param statistics the calculated statistics
     */
    protected static void addCalculatedFeatures(ArrayList<Double> features, 
            Double[] statistics) {
        
        if (statistics != null) {
            features.add(statistics[0]); features.add(statistics[2]);
        }
        else {
            features.add(null); features.add(null);
        }
    }
}
