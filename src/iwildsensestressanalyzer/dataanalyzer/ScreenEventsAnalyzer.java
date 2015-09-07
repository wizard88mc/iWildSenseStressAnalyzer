package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class ScreenEventsAnalyzer {
    
    public static void analyzeScreenDataForEachParticipant(ArrayList<Participant> 
            participants) {
        
        /**
         * Iterating over all the features provided by the 
         * ScreenEventsFeaturesExtractor, confusion matrix of the p-value 
         * of the perceived stress from users
         */
        
        for (Participant participant: participants) {
            
            SurveyDataWrapper[] surveyDataWrappers = 
                    participant.getSurveyDataWrappers();
            
            workWithOnOffDurationForOnOffScreenEvents(surveyDataWrappers);
            workWithOnOffDurationForUnlockedScreenEvents(surveyDataWrappers);
            workWithUnlockTimeForUnlockedScreenEvents(surveyDataWrappers);
        }
        
    }
    
    /**
     * Works with On-Off duration of OnOffScreen events to calculate p-value
     * using t test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     */
    private static void workWithOnOffDurationForOnOffScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers) {
    
        ArrayList<ArrayList<Long>> listValues = new ArrayList<ArrayList<Long>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor().getAllOnOffDurationForOnOffEvents());
        }
        
        /**
         * Normalize data and after statistical analysis with t-test and print
         * p-value in a confusion matrix between all the values
         */
        
        ArrayList<ArrayList<Double>> normalizedDoubleValues = 
                MathUtils.normalizeSetData(listValues, 0L, 1L);
        
        System.out.println("*** On Off Duration for OnOffScreen Events ***");
        
        /**
         * Converting data to an array of double for the TTest library
         */
        ArrayList<double[]> valuesForTTest = new ArrayList<double[]>();
        for (ArrayList<Double> values: normalizedDoubleValues) {
            valuesForTTest.add(MathUtils.convertToArrayDouble(values));
        }
        
        printTTestResults(valuesForTTest);
        
    }
    
    /**
     * Works with On-Off duration of UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     */
    private static void workWithOnOffDurationForUnlockedScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers) {
        
        ArrayList<ArrayList<Long>> listValues = new ArrayList<ArrayList<Long>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor().getAllOnOffDurationForUnlockScreenEvents());
        }
        
        /**
         * Normalizing data and after statistical analysis with t-test and print
         * p-value in a confusion matrix between all the values
         */
        ArrayList<ArrayList<Double>> normalizedDoubleValues = 
                MathUtils.normalizeSetData(listValues, 0L, 1L);
        
        System.out.println();
        System.out.println("*** On Off duration for UnlockedScreen events ***");
        
        /**
         * Converting data to an array of double for the TTest library
         */
        ArrayList<double[]> valuesForTTest = new ArrayList<double[]>();
        for (ArrayList<Double> values: normalizedDoubleValues) {
            valuesForTTest.add(MathUtils.convertToArrayDouble(values));
        }
        
        printTTestResults(valuesForTTest);
    }
    
    /**
     * Work with UnlockTime duration for UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers 
     */
    private static void workWithUnlockTimeForUnlockedScreenEvents(SurveyDataWrapper[]
            surveyDataWrappers) {
        
        ArrayList<ArrayList<Long>> listvalues = new ArrayList<ArrayList<Long>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listvalues.add(wrapper.getScreenEventsFeaturesExtractor().getAllUnlockTimeForUnlockedScreenEvents());
        }
        
        /**
         * Normalizing data and after statistical analysis with t-test and print
         * p-value in a confusion matrix between all the values
         */
        ArrayList<ArrayList<Double>> normalizedDoubleValues = 
                MathUtils.normalizeSetData(listvalues, 0L, 1L);
        
        System.out.println();
        System.out.println("*** Unlock Time duration for UnlockedScreen events ***");
        
        /**
         * Converting data to an array of double for the TTest library
         */
        ArrayList<double[]> valuesForTTest = new ArrayList<double[]>();
        for (ArrayList<Double> values: normalizedDoubleValues) {
            valuesForTTest.add(MathUtils.convertToArrayDouble(values));
        }
        
        printTTestResults(valuesForTTest);
    }
    
    /**
     * Prints the confusion matrix between all the stress states 
     * @param valuesForTTest a list of array of double with the values for the
     * t-test
     */
    private static void printTTestResults(ArrayList<double[]> valuesForTTest) {
        
        System.out.println("   |   1   |   2   |   3   |   4   |   5   |");
        System.out.println("--------------------------------------------");
        for (int i = 0; i < valuesForTTest.size(); i++) {
            
            String outputString = "";
            
            for (int j = 0; j < valuesForTTest.size(); j++) {
             
                if (j == 0) {
                    outputString += " " + (i + 1) + " |" + "  NaN  |"; 
                }
                else {
                    if (j <= i) {
                        outputString += "  NaN  |";
                    }
                    else {
                        /**
                         * This is the only case we have to perform TTest
                         */
                        
                        /**
                         * First check if both data list has at least one value, 
                         * otherwise no test can be performed
                         */
                        if (valuesForTTest.get(i) != null && 
                                valuesForTTest.get(j) != null &&
                                valuesForTTest.get(i).length != 0 && 
                                valuesForTTest.get(j).length != 0) {
                            
                            try {
                                double tTest = new TTest().tTest(valuesForTTest.get(i), 
                                    valuesForTTest.get(j));
                            
                                outputString += MathUtils.decimalFormat.format(tTest) + "|";
                            }
                            catch(Exception exc) {
                                outputString += "  ---  |";
                            }
                        }
                        else {
                            outputString += "  ---  |";
                        }
                    }
                }
            }
            System.out.println(outputString);
        }
    }
    
}
