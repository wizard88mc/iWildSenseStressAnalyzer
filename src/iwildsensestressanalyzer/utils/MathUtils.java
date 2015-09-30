package iwildsensestressanalyzer.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * Class used to define some methods that could be used to calculate statistical 
 * information like mean, variance and standard deviation of a set of time data
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class MathUtils {
    
    public static final DecimalFormat decimalFormat = new DecimalFormat("0.00000");
    
    /**
     * Calculates average, variance and standard deviation of a set of Long values
     * @param values the set of values
     * @return [0]: average, [1]: variance, [2]: standard deviation
     */
    public static Double[] calculateStatisticInformation(ArrayList<Double> values) {
        
        double average = 0, variance = 0, standardDeviation = 0;
        
        for (Double value: values) {
            average += value;
        }
        average /= values.size();
        
        for (Double value: values) {
            variance += Math.pow(average - value, 2);
        }
        variance /= values.size();
        
        standardDeviation = Math.sqrt(variance);
        
        Double[] results = {average, variance, standardDeviation};
        return results;
    }
    
    /**
     * Normalizes data of and ArrayList of Long values in the range 
     * [newMinValue, newMaxValue]
     * @param values a list of Long values to normalize
     * @param newMinValue the new min value possible for all values
     * @param newMaxValue the new max value possible for all value
     * @return a list of normalized data in double precision if there is at 
     * least one value in the original list, otherwise an empty list or null 
     * (depending on the original set of data)
     */
    public static ArrayList<Double> normalizeData(ArrayList<Long> values, Long newMinValue,
            Long newMaxValue) {
        
        if (values != null && !values.isEmpty()) {
            Long minValue = Long.MAX_VALUE, maxValue = Long.MIN_VALUE;

            for (Long value: values) {
                if (minValue > value) {
                    minValue = value;
                }
                if (maxValue < value) {
                    maxValue = value;
                }
            }

            /**
             * Creating the new list of values with normalized data in double 
             * precision
             */
            ArrayList<Double> norm = new ArrayList<Double>();
            for (Long value: values) {

                norm.add(((double)(value - minValue) / (double)(maxValue - minValue)) 
                        * (double) (newMaxValue - newMinValue) + (double)newMinValue);
            }
            
            return norm;
        }
        else {
            if (values == null) {
                return null;
            }
            else {
                return new ArrayList<Double>();
            }
        }
    }
    
    /**
     * Normalizes an ArrayList of a list of Long values with new values 
     * in the new range [newMinValue, newMaxValue]
     * @param values the set of values to normalize
     * @param newMinValue the new min value that the values can have
     * @param newMaxValue the new max value the values can have
     * @return set of normalized data in double precision
     */
    public static ArrayList<ArrayList<Double>> normalizeSetData(ArrayList<ArrayList<Long>> values, 
            Long newMinValue, Long newMaxValue) {
        
        Long minValue = Long.MAX_VALUE, maxValue = Long.MIN_VALUE;

        for (ArrayList<Long> subValues: values) {

            if (subValues != null && !subValues.isEmpty()) {
                for (Long value: subValues) {
                    if (value <= minValue) {
                        minValue = value;
                    }
                    if (value >= maxValue) {
                        maxValue = value;
                    }
                }
            }
        }
        
        if (maxValue == Long.MIN_VALUE) {
            maxValue = 0L;
        }

        /**
         * Normalizing data between newMinValue and newMaxValue
         */
        ArrayList<ArrayList<Double>> norm = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<Long> subValues: values) {
            
            /**
             * Some list of values can be null depending on the answers provided
             * by the participant
             */
            if (subValues != null && !subValues.isEmpty()) {
                
                ArrayList<Double> sublist = new ArrayList<Double>();
                
                for (int i = 0; i < subValues.size(); i++) {

                    Long value = subValues.get(i);
                    sublist.add(((double) (value - minValue) / (double) (maxValue - minValue)) 
                            * (double) (newMaxValue - newMinValue) + newMinValue);
                }
                norm.add(sublist);
            }
            else {
                norm.add(null);
            }
        }
        
        return norm;
    }
    
    /**
     * Normalize a set of data formatted with double precision
     * @param values the set of double values 
     * @param newMinValue
     * @param newMaxValue
     * @return 
     */
    public static ArrayList<ArrayList<Double>> normalizeSetOfDoubleData(ArrayList<ArrayList<Double>> values, 
            Double newMinValue, Double newMaxValue) {
        
        ArrayList<ArrayList<Double>> norm = new ArrayList<ArrayList<Double>>();
        Double minValue = Double.MAX_VALUE, maxValue = Double.MIN_VALUE;
        
        for (ArrayList<Double> subValues: values) {

            if (subValues != null && !subValues.isEmpty()) {
                for (Double value: subValues) {
                    if (value <= minValue) {
                        minValue = value;
                    }
                    if (value >= maxValue) {
                        maxValue = value;
                    }
                }
            }
        }

        /**
         * Normalizing data between newMinValue and newMaxValue
         */
        
        if (maxValue == Double.MIN_VALUE) {
            maxValue = 0.0;
        }
        
        for (ArrayList<Double> subValues: values) {
            
            /**
             * Some list of values can be null depending on the answers provided
             * by the participant
             */
            if (subValues != null && !subValues.isEmpty()) {
                
                ArrayList<Double> sublist = new ArrayList<Double>();
                
                for (int i = 0; i < subValues.size(); i++) {

                    Double value = subValues.get(i);
                    
                    double newValue = ((double) (value - minValue) / (double) (maxValue - minValue)) 
                            * (double) (newMaxValue - newMinValue) + newMinValue;
                    
                    if (Double.isNaN(newValue)) {
                        newValue = 0.0;
                    }
                    if (Double.isInfinite(newValue)) {
                        newValue = 1.0;
                    }
                    
                    sublist.add(newValue);
                }
                norm.add(sublist);
            }
            else {
                norm.add(null);
            }
        }
        
        return norm;
    }
    
    /**
     * Converts an ArrayList of Long values into an array of double values. 
     * Used to create the input for the 
     * @param values the list of Long values to convert
     * @return an array of double values
     */
    public static double[] convertArrayLongToArrayDouble(ArrayList<Long> values) {
        
        if (values != null) {
            double[] converted = new double[values.size()];
            for (int i = 0; i < values.size(); i++) {   
                converted[i] = Double.valueOf(values.get(i));
            }

            return converted;
        }
        else {
            return null;
        }
    }
    
    /**
     * Converts an ArrayList of double to an array of double
     * @param values a list of Double values to convert
     * @return 
     */
    public static double[] convertToArrayDouble(ArrayList<Double> values) {
        
        if (values != null) {
            double[] converted = new double[values.size()];
            for (int i = 0; i < values.size(); i++) {
                converted[i] = values.get(i);
            }
            return converted;
        }
        else {
            return null;
        }
        
    }
}
