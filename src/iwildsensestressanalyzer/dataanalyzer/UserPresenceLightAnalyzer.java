package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceLightAnalyzer extends EventsAnalyzer {
    
    public static void analyzeUserPresenceLightDataForEachParticipant(
            ArrayList<Participant> participants, boolean easyJob, 
            boolean useAllTogether) {
        
        ArrayList<String> labels = EventsAnalyzer.labels5LabelsTaks;
        if (easyJob) {
            labels = EventsAnalyzer.labels3LabelsTask;
        }
        
        if (!useAllTogether) {
            
            ArrayList<Integer> tTestPassedForLightValuesOfScreenOnOffEvents = 
                        new ArrayList<>(),
                    tTestPassedForLightValuesOfUnlockedScreenEvents = 
                        new ArrayList<>(),
                    tTestPassedForLightValuesForAllScreenEvents = 
                        new ArrayList<>();
            
            ArrayList<Integer> validTTestsForLightValuesOfScreenOnOffEvents = 
                        new ArrayList<>(),
                    validTTestsForLightValuesOfUnlockedScreenEvents = 
                        new ArrayList<>(),
                    validTTestsForLightValuesForAllScreenEvents = 
                        new ArrayList<>();
            
            for (Participant participant: participants) {
                
                printTitleMessage("*** Participant " + participant.getIMEI() + 
                        " ***");

                SurveyDataWrapper[] wrappers;
                if (!easyJob) {
                    wrappers = participant.getSurveyDataWrappers();
                }
                else {
                    wrappers = participant.getEasySurveyDataWrappers();
                }

                ArrayList<Boolean> returnedResults = 
                        workWithLightValuesOfScreenOnOffEvents(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForLightValuesOfScreenOnOffEvents, 
                        returnedResults, 
                        validTTestsForLightValuesOfScreenOnOffEvents);
                
                returnedResults = 
                        workWithLightValuesOfUnlockedScreenEvents(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForLightValuesOfUnlockedScreenEvents,
                        returnedResults, 
                        validTTestsForLightValuesOfUnlockedScreenEvents);
                
                returnedResults = 
                        workWithLightValuesForAllScreenEvents(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForLightValuesForAllScreenEvents, 
                        returnedResults, 
                        validTTestsForLightValuesForAllScreenEvents);
            }
            
            /**
             * Analyzing percentage of success of Light value for ScreenOnOff 
             * events feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForLightValuesForAllScreenEvents, 
                    validTTestsForLightValuesOfScreenOnOffEvents, labels, 
                    "*** Percentage of success of Light value for ScreenOnOff"
                            + " events feature ***");
            
            /**
             * Analyzing percentage of success of Light value for UnlockedScreen
             * events feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForLightValuesOfUnlockedScreenEvents, 
                    validTTestsForLightValuesOfUnlockedScreenEvents, labels, 
                    "*** Percentage of success of Light value for "
                            + "UnlockedScreen events feature ***");
            
            /**
             * Analyzing percentage of success of Light value for ALL screen 
             * events feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForLightValuesForAllScreenEvents, 
                    validTTestsForLightValuesForAllScreenEvents, labels, 
                    "*** Percentage of success of Light value for ALL screen"
                            + " events feature ***");
            
        }
        else {
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithLightValuesOfScreenOnOffEventsForAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithLightValuesOfUnlockedScreenEventsForAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithLightValuesOfAllScreenEventsForAllParticipants(allSurveyDataWrappers, 
                    labels);
        }
    }
    
    /**
     * Works with light values of ScreenOnOff events to calculate p-value and 
     * perform t-test
     * @param wrappers all the surveys and the related data to use
     * @param labels list of labels for the output table
     * @return a list of passed/not passed t-tests
     */
    private static ArrayList<Boolean> workWithLightValuesOfScreenOnOffEvents(
            SurveyDataWrapper[] wrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Light value for ScreenOnOff events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor().
                    getAllLightValuesForScreenOnOffEvents());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(values, 0.0, 1.0);
        
        return printTTestResults(normalizedValues, labels);
    }
    
    /**
     * Perform t-test and calculate p-value using data coming from all the 
     * participants for ScreenOnOff events
     * @param listWrappers a list with all the wrappers and all the answers 
     * provided by all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithLightValuesOfScreenOnOffEventsForAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Light values for ScreenOnOff" + 
                " events");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                
                Double[] values = wrapper.getUserPresenceLightFeaturesExtractor()
                        .calculateStatisticsLightValuesForScreenOnOffEvents();
                
                if (values != null) {
                    singleValues.add(values[0]);
                }
            }
            
            listValues.add(singleValues);
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, labels);
    }
    
    /**
     * Works with light values of UnlockedScreen events to calculate p-value and 
     * perform t-test
     * @param wrappers all the surveys and the related data to use
     * @param labels list of labels for the output table
     * @return a list of passed/not passed t-tests
     */
    private static ArrayList<Boolean> workWithLightValuesOfUnlockedScreenEvents(
            SurveyDataWrapper[] wrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Light value for UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor()
                    .getAllLightValuesForUnlockedScreenEvents());
        }
        
        return printTTestResults(MathUtils.normalizeSetOfDoubleData(values, 
                0.0, 1.0), labels);
    }
    
    /**
     * Perform t-test and calculate p-value using data coming from all the 
     * participants with light values for UnlockedScreen events
     * @param listWrappers a list with all the wrappers and all the answers 
     * provided by all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithLightValuesOfUnlockedScreenEventsForAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Light values for UnlockedScreen" + 
                " events");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                
                Double[] values = wrapper.getUserPresenceLightFeaturesExtractor()
                        .calculateStatisticsLightValuesForUnlockedScreenEvents();
                
                if (values != null) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, labels);
    }
    
    /**
     * Works with light values of all the screen events to perform t-test
     * and calculate p-value
     * @param wrappers all the surveys and the related data to use
     * @param labels list of labels for the output table
     * @return a list of passed/not passed t-Test results
     */
    private static ArrayList<Boolean> workWithLightValuesForAllScreenEvents(
            SurveyDataWrapper[] wrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Light value for ALL screen events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor()
                    .getAllLightValuesForAllScreenEvents());
        }
        
        return printTTestResults(MathUtils.normalizeSetOfDoubleData(values, 
                0.0, 1.0), labels);
    }
    
    /**
     * Perform t-test and calculate p-value using data coming from all the 
     * participants using data for all the screen events
     * @param listWrappers a list with all the wrappers and all the answers 
     * provided by all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithLightValuesOfAllScreenEventsForAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Light values for ALL Screen" + 
                " events");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                
                Double[] values = wrapper.getUserPresenceLightFeaturesExtractor()
                        .calculateStatisticsLightValuesForAllScreenEvents();
                
                if (values != null) {
                    singleValues.add(values[0]);
                }
            }    
            listValues.add(singleValues);
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, labels);
    }
}
