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
            
            workWithOnOffDurationForOnOffScreenEventsOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithOnOffDurationForUnlockedScreenEventsOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithUnlockTimeForUnlockedScreenEventsOfAllParticipants(allSurveyDataWrappers, easyJob);
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
        
        printTitleMessage("*** On-Off Duration for ScreenOnOff Events ***");
    
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllOnOffDurationForOnOffEvents());
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with On-Off duration of OnOff Screen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithOnOffDurationForOnOffScreenEventsOfAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
                
                printTitleMessage("*** GLOBAL ANALYSIS: On Off Duration for OnOffScreen "
                    + "Events ***");
                
            ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
            
            for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

                ArrayList<Double> singleListValues = new ArrayList<Double>();
                
                for (SurveyDataWrapper wrapper: wrappers) {
                    Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor()
                            .calculateStatisticOnOffDurationForScreenOnOffEvents();
                    if (singleValue != null && singleValue[0] != -1) {
                        singleListValues.add(singleValue[0]);
                    }
                }
                listValues.add(singleListValues);
            }
            
            printTTestResults(listValues, easyJob);
        }
    
    /**
     * Works with On-Off duration of UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     */
    private static void workWithOnOffDurationForUnlockedScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** On Off duration for UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllOnOffDurationForUnlockScreenEvents());
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with On-Off duration of UnlockedScreen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithOnOffDurationForUnlockedScreenEventsOfAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: On Off Duration of " + 
                "UnlockScreen Events ***");
                
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleListValues = new ArrayList<Double>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor()
                        .calculateStatisticOnOffDurationForUnlockScreenEvents();
                if (singleValue != null && singleValue[0] != -1) {
                    singleListValues.add(singleValue[0]);
                }
            }

            listValues.add(singleListValues);
        }

        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Work with UnlockTime duration for UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers 
     */
    private static void workWithUnlockTimeForUnlockedScreenEvents(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Unlock Time duration of UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> listvalues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listvalues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllUnlockTimeForUnlockedScreenEvents());
        }
        
        printTTestResults(listvalues, easyJob);
    }
    
    /**
     * Works with Unlock time of UnlockedScreen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithUnlockTimeForUnlockedScreenEventsOfAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
                
        printTitleMessage("*** GLOBAL ANALYSIS: Unlock Time of " + 
        "UnlockScreen Events ***");
                
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleListValues = new ArrayList<Double>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor()
                        .calculateStatisticUnlockTimeForUnlockedScreenEvents();
                if (singleValue != null && singleValue[0] != -1) {
                    singleListValues.add(singleValue[0]);
                }
            }

            listValues.add(singleListValues);
        }

        printTTestResults(listValues, easyJob);
    }   
}
