package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

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
            
            
        }
        
    }
    
    /**
     * Works with On-Off duration of OnOffScreen events to calculate p-value
     * using t test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     */
    private static void workWithOnOffDurationForOnOffScreenEvents(SurveyDataWrapper[] surveyDataWrappers) {
    
        ArrayList<ArrayList<Long>> listValues = new ArrayList<ArrayList<Long>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsAnalyzer().getAllOnOffDurationForOnOffEvents());
        }
        
        /**
         * Normalize data and after statistical analysis with t-test and print
         * p-value in a confusion matrix between all the values
         */
        
        MathUtils.normalizeSetData(listValues, 0L, 1L);
        
        System.out.println("*** On Off Duration for OnOffScreen Events ***");
        
        /**
         * Converting data to an array of double for the TTest library
         */
        ArrayList<double[]> valuesForTTest = new ArrayList<double[]>();
        for (ArrayList<Long> values: listValues) {
            valuesForTTest.add(MathUtils.convertArrayLongToArrayDouble(values));
        }
        
        
        
    }
    
    /**
     * Prints the confusion matrix between all the stress states 
     * @param valuesForTTest 
     */
    private static void printTTestResults(ArrayList<double[]> valuesForTTest) {
        
        System.out.println("   |   1   |   2   |   3   |   4   |   5   |");
        for (int i = 0; i < valuesForTTest.size(); i++) {
            
            String outputString = "";
            
            for (int j = 0; j < valuesForTTest.size(); j++) {
             
                if (j == 0) {
                    outputString += " " + (j +  1) + " |"; 
                }
                else {
                    if (j <= i) {
                        outputString += "  ---  ";
                    }
                    else {
                        /**
                         * This is the only case we have to perform TTest
                         */
                    }
                }
            }
        }
        
    }
    
}
