package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class ScreenEventsAnalyzer extends EventsAnalyzer {
    
    public static void analyzeScreenDataForEachParticipant(ArrayList<Participant> 
            participants, boolean easyJob, boolean useAllTogether) {
        
        printTitleMessage("*** ANALYZING SCREEN FEATURES ***");
        
        /**
         * Iterating over all the features provided by the 
         * ScreenEventsFeaturesExtractor, confusion matrix of the p-value 
         * of the perceived stress from users
         */
        if (!useAllTogether) {
            ArrayList<Integer> tTestPassedForOnOffDurationForOnOffScreenEvents = 
                    new ArrayList<>(),
                    tTestPassedForOnOffDurationForUnlockedScreenEvents = 
                    new ArrayList<>(),
                    tTestPassedForUnlockTimeForUnlockedScreenEvents = 
                    new ArrayList<>();
            
            ArrayList<Integer> validTTestsForOnOffDurationForOnOffScreenEvents = 
                    new ArrayList<>(),
                    validTTestsForOnOffDurationForUnlockedScreenEvents = 
                    new ArrayList<>(), 
                    validTTestsForUnlockTimeForUnlockedScreenEvents = 
                    new ArrayList<>();
            
            for (Participant participant: participants) {
                
                printTitleMessage("*** Participant " + participant.getIMEI() + 
                        " ***");

                SurveyDataWrapper[] surveyDataWrappers; 
                if (!easyJob) { 
                    surveyDataWrappers = participant.getSurveyDataWrappers();
                }
                else {
                    surveyDataWrappers = participant.getEasySurveyDataWrappers();
                }

                ArrayList<Boolean> returnedResults = 
                        workWithOnOffDurationForOnOffScreenEvents(surveyDataWrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForOnOffDurationForOnOffScreenEvents, 
                        returnedResults, validTTestsForOnOffDurationForOnOffScreenEvents);
                
                returnedResults = workWithOnOffDurationForUnlockedScreenEvents(surveyDataWrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForOnOffDurationForUnlockedScreenEvents, 
                        returnedResults, validTTestsForOnOffDurationForUnlockedScreenEvents);
                
                workWithUnlockTimeForUnlockedScreenEvents(surveyDataWrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForUnlockTimeForUnlockedScreenEvents, 
                        returnedResults, validTTestsForUnlockTimeForUnlockedScreenEvents);
            }
            
            /**
             * Analyzing percentage of success of On-Off duration for 
             * ScreenOnOff events feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForOnOffDurationForOnOffScreenEvents, 
                    validTTestsForOnOffDurationForOnOffScreenEvents, easyJob, 
                    "*** Percentage of success for On-Off Duration for "
                            + "ScreenOnOff events ***");
            
            /**
             * Analyzing percentage of success of On-Off duration for 
             * UnlockedScreen events feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForOnOffDurationForUnlockedScreenEvents, 
                    validTTestsForOnOffDurationForUnlockedScreenEvents, easyJob, 
                    "*** Percentage of success for On-Off Duration for "
                            + "UnlockedScreen events ***");
           
           /**
            * Analyzing percentage of success of Unlock Time for 
            * UnlockedScreen events feature
            */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForUnlockTimeForUnlockedScreenEvents, 
                    validTTestsForUnlockTimeForUnlockedScreenEvents, easyJob, 
                    "*** Percentage of success for Unlock Time for "
                            + "UnlockedScreen events ***");
        }
        
        if (useAllTogether) {
            
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithOnOffDurationForOnOffScreenEventsOfAllParticipants(allSurveyDataWrappers, 
                    easyJob);
            workWithOnOffDurationForUnlockedScreenEventsOfAllParticipants(allSurveyDataWrappers, 
                    easyJob);
            workWithUnlockTimeForUnlockedScreenEventsOfAllParticipants(allSurveyDataWrappers, 
                    easyJob);
        }
    }
    
    /**
     * Works with On-Off duration of OnOffScreen events to calculate p-value
     * using t test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     * @param easyJob true if we are working with the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithOnOffDurationForOnOffScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** On-Off Duration for ScreenOnOff Events ***");
    
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllOnOffDurationForOnOffEvents());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with On-Off duration of OnOff Screen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithOnOffDurationForOnOffScreenEventsOfAllParticipants
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
            
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with On-Off duration of UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     */
    private static ArrayList<Boolean> workWithOnOffDurationForUnlockedScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** On Off duration for UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllOnOffDurationForUnlockScreenEvents());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with On-Off duration of UnlockedScreen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithOnOffDurationForUnlockedScreenEventsOfAllParticipants
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

        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Work with UnlockTime duration for UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers 
     */
    private static ArrayList<Boolean> workWithUnlockTimeForUnlockedScreenEvents(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Unlock Time duration of UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> listvalues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listvalues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllUnlockTimeForUnlockedScreenEvents());
        }
        
        return printTTestResults(listvalues, easyJob);
    }
    
    /**
     * Works with Unlock time of UnlockedScreen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithUnlockTimeForUnlockedScreenEventsOfAllParticipants
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

        return printTTestResults(listValues, easyJob);
    }   
}
