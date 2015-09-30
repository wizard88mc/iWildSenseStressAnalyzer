package iwildsensestressanalyzer.dataanalyzer;

import java.util.ArrayList;

/**
 *
 * @author Matteo
 */
public class FeaturesExtractor {
 
    /**
     * Calculates the average of a set of values
     * @param values the list of values
     * @return the average of the values if the list is not empty, null otherwise
     */
    protected Double calculateAverageValue(ArrayList<Double> values) {
        
        if (values.isEmpty()) {
            return null;
        }
        else {
            double average = 0.0;
            for (Double value: values) {
                average += value;
            }
            return average / (double) values.size();
        }
        
    }
}
