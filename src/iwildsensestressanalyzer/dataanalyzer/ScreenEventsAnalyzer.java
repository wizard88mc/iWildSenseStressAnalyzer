package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class ScreenEventsAnalyzer extends EventsAnalyzer {
    
    public static void analyzeScreenDataForEachParticipant(ArrayList<Participant> 
            participants, boolean easyJob, boolean useAllTogether) {
        
        /**
         * Iterating over all the features provided by the 
         * ScreenEventsFeaturesExtractor, confusion matrix of the p-value 
         * of the perceived stress from users
         */
        
        for (Participant participant: participants) {
            
            SurveyDataWrapper[] surveyDataWrappers; 
            if (!easyJob) { 
                surveyDataWrappers = participant.getSurveyDataWrappers();
            }
            else {
                surveyDataWrappers = participant.getEasySurveyDataWrappers();
            }
            
            workWithOnOffDurationForOnOffScreenEvents(surveyDataWrappers, easyJob);
            workWithOnOffDurationForUnlockedScreenEvents(surveyDataWrappers, easyJob);
            workWithUnlockTimeForUnlockedScreenEvents(surveyDataWrappers, easyJob);
        }
        
        /**
         * TODO
         * put together all the data from all the participants and try to work
         * with that
         */
        /*if (useAllTogether) {
            SurveyDataWrapper[] surveyDataWrappers = null;
            if (easyJob) {
                surveyDataWrappers
            }
            else {
                
            }
        }*/
        
    }
    
    /**
     * Works with On-Off duration of OnOffScreen events to calculate p-value
     * using t test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     */
    private static void workWithOnOffDurationForOnOffScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
    
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
        
        System.out.println();
        System.out.println("*** On Off Duration for OnOffScreen Events ***");
        
        printTTestResults(normalizedDoubleValues, easyJob);
    }
    
    /**
     * Works with On-Off duration of UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     */
    private static void workWithOnOffDurationForUnlockedScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
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
        
        printTTestResults(normalizedDoubleValues, easyJob);
    }
    
    /**
     * Work with UnlockTime duration for UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers 
     */
    private static void workWithUnlockTimeForUnlockedScreenEvents(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
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
        
        printTTestResults(normalizedDoubleValues, easyJob);
    }
}
