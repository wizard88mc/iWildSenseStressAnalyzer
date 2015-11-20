/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.weka;

import java.util.ArrayList;

/**
 *
 * @author Matteo
 */
public class WekaFeaturesCreator {
 
    /**
     * Adds average and standard deviation of the calcualted statistics 
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
