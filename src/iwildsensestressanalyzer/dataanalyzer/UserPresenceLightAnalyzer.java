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
        
        if (!useAllTogether) {
        for (Participant participant: participants) {
            
            SurveyDataWrapper[] wrappers;
            if (!easyJob) {
                wrappers = participant.getSurveyDataWrappers();
            }
            else {
                wrappers = participant.getEasySurveyDataWrappers();
            }
            
            workWithLightValuesOfScreenOnOffEvents(wrappers, easyJob);
            workWithLightValuesOfUnlockedScreenEvents(wrappers, easyJob);
            workWithLightValuesForAllScreenEvents(wrappers, easyJob);
        }
        }
        else {
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithLightValuesOfScreenOnOffEventsForAllParticipants(allSurveyDataWrappers, 
                    easyJob);
            workWithLightValuesOfUnlockedScreenEventsForAllParticipants(allSurveyDataWrappers, 
                    easyJob);
            workWithLightValuesOfAllScreenEventsForAllParticipants(allSurveyDataWrappers, 
                    easyJob);
        }
    }
    
    /**
     * Works with light values of ScreenOnOff events to calculate p-value and 
     * perform t-test
     * @param wrappers all the surveys and the related data to use
     * @param easyJob true if is the easy task, false otherwise
     */
    private static void workWithLightValuesOfScreenOnOffEvents(SurveyDataWrapper[] 
            wrappers, boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Light value for ScreenOnOff events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor().getAllLightValuesForScreenOnOffEvents());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(values, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Perform t-test and calculate p-value using data coming from all the 
     * participants for ScreenOnOff events
     * @param listWrappers a list with all the wrappers and all the answers 
     * provided by all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithLightValuesOfScreenOnOffEventsForAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                
                Double[] values = wrapper.getUserPresenceLightFeaturesExtractor()
                        .calculateStatisticsLightValuesForScreenOnOffEvents();
                
                if (values != null) {
                    singleValues.add(values[0]);
                }
            }
            
            listValues.add(singleValues);
        }
        
        System.out.println();
        System.out.println("*** GLOBAL ANALYSIS: Light values for ScreenOnOff" + 
                " events");
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with light values of UnlockedScreen events to calculate p-value and 
     * perform t-test
     * @param wrappers all the surveys and the related data to use
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithLightValuesOfUnlockedScreenEvents(SurveyDataWrapper[] 
            wrappers, boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Light value for UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor().getAllLightValuesForUnlockedScreenEvents());
        }
        
        printTTestResults(MathUtils.normalizeSetOfDoubleData(values, 0.0, 1.0), easyJob);
    }
    
    /**
     * Perform t-test and calculate p-value using data coming from all the 
     * participants with light values for UnlockedScreen events
     * @param listWrappers a list with all the wrappers and all the answers 
     * provided by all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithLightValuesOfUnlockedScreenEventsForAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                
                Double[] values = wrapper.getUserPresenceLightFeaturesExtractor()
                        .calculateStatisticsLightValuesForUnlockedScreenEvents();
                
                if (values != null) {
                    singleValues.add(values[0]);
                }
            }
            
            listValues.add(singleValues);
        }
        
        System.out.println();
        System.out.println("*** GLOBAL ANALYSIS: Light values for UnlockedScreen" + 
                " events");
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with light values of all the screen events to perform t-test
     * and calculate p-value
     * @param wrappers all the surveys and the related data to use
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithLightValuesForAllScreenEvents(SurveyDataWrapper[]
            wrappers, boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Light value for ALL screen events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor().getAllLightValuesForAllScreenEvents());
        }
        
        printTTestResults(MathUtils.normalizeSetOfDoubleData(values, 0.0, 1.0), easyJob);
    }
    
    /**
     * Perform t-test and calculate p-value using data coming from all the 
     * participants using data for all the screen events
     * @param listWrappers a list with all the wrappers and all the answers 
     * provided by all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithLightValuesOfAllScreenEventsForAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                
                Double[] values = wrapper.getUserPresenceLightFeaturesExtractor()
                        .calculateStatisticsLightValuesForAllScreenEvents();
                
                if (values != null) {
                    singleValues.add(values[0]);
                }
            }
            
            listValues.add(singleValues);
        }
        
        System.out.println();
        System.out.println("*** GLOBAL ANALYSIS: Light values for ALL Screen" + 
                " events");
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
}
