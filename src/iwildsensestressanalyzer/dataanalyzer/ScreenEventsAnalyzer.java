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
        
        ArrayList<String> labels = EventsAnalyzer.labels5LabelsTaks;
        if (easyJob) {
            labels = EventsAnalyzer.labels3LabelsTask;
        }
        
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
                        workWithOnOffDurationForOnOffScreenEvents(surveyDataWrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForOnOffDurationForOnOffScreenEvents, 
                        returnedResults, validTTestsForOnOffDurationForOnOffScreenEvents);
                
                returnedResults = workWithOnOffDurationForUnlockedScreenEvents(surveyDataWrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForOnOffDurationForUnlockedScreenEvents, 
                        returnedResults, validTTestsForOnOffDurationForUnlockedScreenEvents);
                
                workWithUnlockTimeForUnlockedScreenEvents(surveyDataWrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForUnlockTimeForUnlockedScreenEvents, 
                        returnedResults, validTTestsForUnlockTimeForUnlockedScreenEvents);
            }
            
            /**
             * Analyzing percentage of success of On-Off duration for 
             * ScreenOnOff events feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForOnOffDurationForOnOffScreenEvents, 
                    validTTestsForOnOffDurationForOnOffScreenEvents, labels, 
                    "*** Percentage of success for On-Off Duration for "
                            + "ScreenOnOff events ***");
            
            /**
             * Analyzing percentage of success of On-Off duration for 
             * UnlockedScreen events feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForOnOffDurationForUnlockedScreenEvents, 
                    validTTestsForOnOffDurationForUnlockedScreenEvents, labels, 
                    "*** Percentage of success for On-Off Duration for "
                            + "UnlockedScreen events ***");
           
           /**
            * Analyzing percentage of success of Unlock Time for 
            * UnlockedScreen events feature
            */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForUnlockTimeForUnlockedScreenEvents, 
                    validTTestsForUnlockTimeForUnlockedScreenEvents, labels, 
                    "*** Percentage of success for Unlock Time for "
                            + "UnlockedScreen events ***");
        }
        
        if (useAllTogether) {
            
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithOnOffDurationForOnOffScreenEventsOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithOnOffDurationForUnlockedScreenEventsOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithUnlockTimeForUnlockedScreenEventsOfAllParticipants(allSurveyDataWrappers, 
                    labels);
        }
    }
    
    /**
     * Works with On-Off duration of OnOffScreen events to calculate p-value
     * using t test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithOnOffDurationForOnOffScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** On-Off Duration for ScreenOnOff Events ***");
    
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllOnOffDurationForOnOffEvents());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with On-Off duration of OnOff Screen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithOnOffDurationForOnOffScreenEventsOfAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
                    ArrayList<String> labels) {
                
        printTitleMessage("*** GLOBAL ANALYSIS: On Off Duration for OnOffScreen "
                + "Events ***");
                
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
            
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleListValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor()
                        .calculateStatisticOnOffDurationForScreenOnOffEvents();
                if (singleValue != null && singleValue[0] != -1) {
                    singleListValues.add(singleValue[0]);
                }
            }
            listValues.add(singleListValues);
        }
            
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with On-Off duration of UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers the survey and the related data to use
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithOnOffDurationForUnlockedScreenEvents(SurveyDataWrapper[] 
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** On Off duration for UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllOnOffDurationForUnlockScreenEvents());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with On-Off duration of UnlockedScreen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithOnOffDurationForUnlockedScreenEventsOfAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
                    ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: On Off Duration of " + 
                "UnlockScreen Events ***");
                
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleListValues = new ArrayList<>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor()
                        .calculateStatisticOnOffDurationForUnlockScreenEvents();
                if (singleValue != null && singleValue[0] != -1) {
                    singleListValues.add(singleValue[0]);
                }
            }

            listValues.add(singleListValues);
        }

        return printTTestResults(listValues, labels);
    }
    
    /**
     * Work with UnlockTime duration for UnlockedScreen events to calculate p-values
     * using t-test between all the answers of the surveys
     * @param surveyDataWrappers 
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithUnlockTimeForUnlockedScreenEvents(SurveyDataWrapper[]
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Unlock Time duration of UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> listvalues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listvalues.add(wrapper.getScreenEventsFeaturesExtractor()
                    .getAllUnlockTimeForUnlockedScreenEvents());
        }
        
        return printTTestResults(listvalues, labels);
    }
    
    /**
     * Works with Unlock time of UnlockedScreen events using data from all the
     * participants 
     * @param listWrappers a list of all the wrappers for all the participants
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithUnlockTimeForUnlockedScreenEventsOfAllParticipants
            (ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
                    ArrayList<String> labels) {
                
        printTitleMessage("*** GLOBAL ANALYSIS: Unlock Time of " + 
        "UnlockScreen Events ***");
                
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleListValues = new ArrayList<>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] singleValue = wrapper.getScreenEventsFeaturesExtractor()
                        .calculateStatisticUnlockTimeForUnlockedScreenEvents();
                if (singleValue != null && singleValue[0] != -1) {
                    singleListValues.add(singleValue[0]);
                }
            }

            listValues.add(singleListValues);
        }

        return printTTestResults(listValues, labels);
    }   
}
