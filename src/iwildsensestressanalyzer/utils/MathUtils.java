package iwildsensestressanalyzer.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Locale;

/**
 *
 * Class used to define some methods that could be used to calculate statistical 
 * information like mean, variance and standard deviation of a set of time data
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class MathUtils {
    
    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00000", 
            new DecimalFormatSymbols(Locale.US));
    public static final DecimalFormat FORMAT_SUCCESS = new DecimalFormat("00");
    
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
        if (values.size() > 1) {
            variance /= values.size();
        }
        else {
            variance = 0;
        }
        
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
    public static ArrayList<Double> normalizeData(ArrayList<Double> values, 
            Double newMinValue, Double newMaxValue) {
        
        if (values != null && !values.isEmpty()) {
            Double minValue = Double.MAX_VALUE, maxValue = Double.MIN_VALUE;

            for (Double value: values) {
                if (value != null) {
                    if (minValue > value) {
                        minValue = value;
                    }
                    if (maxValue < value) {
                        maxValue = value;
                    }
                }
            }

            /**
             * Creating the new list of values with normalized data in double 
             * precision
             */
            ArrayList<Double> norm = new ArrayList<Double>();
            for (Double value: values) {
                
                if (value != null) {

                    norm.add(((value - minValue) / (maxValue - minValue)) 
                            * (newMaxValue - newMinValue) + newMinValue);
                }
                else {
                    norm.add(null);
                }
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
    public static ArrayList<ArrayList<Double>> normalizeSetOfLongData(ArrayList<ArrayList<Long>> values, 
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
                    if (value != null) {
                        if (value <= minValue) {
                            minValue = value;
                        }
                        if (value >= maxValue) {
                            maxValue = value;
                        }
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
        if (minValue == Double.MAX_VALUE) {
            minValue = 0.0;
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
                    
                    if (value != null) {
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
                    else {
                        sublist.add(null);
                    }
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
    
    /**
     * Takes a list of features as Double values and converts to a list of 
     * string for weka files, replacing null values with ? 
     * @param features the set of features
     * @return a set of string for weka file
     */
    public static ArrayList<String> convertFeaturesToASetOfString(
            ArrayList<Double> features) {
        
        ArrayList<String> stringFeatures = new ArrayList<String>();
        
        for (Double feature: features) {
            
            if (feature != null) {
                stringFeatures.add(DECIMAL_FORMAT.format(feature));
            }
            else {
                stringFeatures.add("?");
            }
        }
        
        return stringFeatures;
    }
}
