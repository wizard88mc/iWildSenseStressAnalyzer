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
        
        if (!useAllTogether) {
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
        }
        
        if (useAllTogether) {
            
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithOnOffDurationForOnOffScreenEventsForAllParticipants(allSurveyDataWrappers, easyJob);
            workWithOnOffDurationForUnlockedScreenEventsForAllParticipants(allSurveyDataWrappers, easyJob);
            workWithUnlockTimeForUnlockedScreenEventsForAllParticipants(allSurveyDataWrappers, easyJob);
        }
    }
    
    /**
     * Works with On-Off duration of OnOffScreen events to calculate p-value
     * using t test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     * @param easyJob true if we are working with the easy test, false otherwise
     */
    private static void workWithOnOffDurationForOnOffScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
    
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor().getAllOnOffDurationForOnOffEvents());
        }
        
        /**
         * Normalize data and after statistical analysis with t-test and print
         * p-value in a confusion matrix between all the values
         */
        
        ArrayList<ArrayList<Double>> normalizedDoubleValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        System.out.println();
        System.out.println("*** On Off Duration for OnOffScreen Events ***");
        
        printTTestResults(normalizedDoubleValues, easyJob);
    }
    
    /**
     * Works with On-Off duration of OnOff Screen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithOnOffDurationForOnOffScreenEventsForAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
                
            ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
            
            for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

                ArrayList<Double> singleListValues = new ArrayList<Double>();
                
                for (SurveyDataWrapper wrapper: wrappers) {
                    Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor().calculateStatisticOnOffDurationForScreenOnOffEvents();
                    if (singleValue != null && singleValue[0] != -1) {
                        singleListValues.add(singleValue[0]);
                    }
                }
                
                listValues.add(singleListValues);
            }
                
            System.out.println();
            System.out.println("*** GLOBAL ANALYSIS: On Off Duration for OnOffScreen Events ***");
            
            ArrayList<ArrayList<Double>> normalizedValues = 
                    MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
            
            printTTestResults(normalizedValues, easyJob);
        }
    
    /**
     * Works with On-Off duration of UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     */
    private static void workWithOnOffDurationForUnlockedScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor().getAllOnOffDurationForUnlockScreenEvents());
        }
        
        /**
         * Normalizing data and after statistical analysis with t-test and print
         * p-value in a confusion matrix between all the values
         */
        ArrayList<ArrayList<Double>> normalizedDoubleValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        System.out.println();
        System.out.println("*** On Off duration for UnlockedScreen events ***");
        
        printTTestResults(normalizedDoubleValues, easyJob);
    }
    
    /**
     * Works with On-Off duration of UnlockedScreen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithOnOffDurationForUnlockedScreenEventsForAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
                
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleListValues = new ArrayList<Double>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor().calculateStatisticOnOffDurationForUnlockScreenEvents();
                if (singleValue != null && singleValue[0] != -1) {
                    singleListValues.add(singleValue[0]);
                }
            }

            listValues.add(singleListValues);
        }

        System.out.println();
        System.out.println("*** GLOBAL ANALYSIS: On Off Duration for " + 
                "UnlockScreen Events ***");

        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);

        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Work with UnlockTime duration for UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers 
     */
    private static void workWithUnlockTimeForUnlockedScreenEvents(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        ArrayList<ArrayList<Double>> listvalues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listvalues.add(wrapper.getScreenEventsFeaturesExtractor().getAllUnlockTimeForUnlockedScreenEvents());
        }
        
        /**
         * Normalizing data and after statistical analysis with t-test and print
         * p-value in a confusion matrix between all the values
         */
        ArrayList<ArrayList<Double>> normalizedDoubleValues = 
                MathUtils.normalizeSetOfDoubleData(listvalues, 0.0, 1.0);
        
        System.out.println();
        System.out.println("*** Unlock Time duration for UnlockedScreen events ***");
        
        printTTestResults(normalizedDoubleValues, easyJob);
    }
    
    /**
     * Works with Unlock time of UnlockedScreen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithUnlockTimeForUnlockedScreenEventsForAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
                
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleListValues = new ArrayList<Double>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor().calculateStatisticUnlockTimeForUnlockedScreenEvents();
                if (singleValue != null && singleValue[0] != -1) {
                    singleListValues.add(singleValue[0]);
                }
            }

            listValues.add(singleListValues);
        }

        System.out.println();
        System.out.println("*** GLOBAL ANALYSIS: Unlock Time for " + 
                "UnlockScreen Events ***");

        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);

        printTTestResults(normalizedValues, easyJob);
    }   
}
