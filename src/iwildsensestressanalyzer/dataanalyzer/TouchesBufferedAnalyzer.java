/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 * Static class responsible to analyze data from the TouchesBuffered events
 * @author Matteo Ciman
 * @version 0.1
 */
public class TouchesBufferedAnalyzer extends EventsAnalyzer {
    
    public static void analyzeTouchesBufferedDataForEachParticipant(
            ArrayList<Participant> participants, boolean easyJob) {
        
        for (Participant participant: participants) {
            
            SurveyDataWrapper[] wrappers;
            if (!easyJob) {
                wrappers = participant.getSurveyDataWrappers();
            } 
            else {
                wrappers = participant.getEasySurveyDataWrappers();
            }
            
            workWithCounter(wrappers, easyJob);
            workWithMinInterval(wrappers, easyJob);
            workWithMaxInterval(wrappers, easyJob);
            workWithRange(wrappers, easyJob);
            workWithMedian(wrappers, easyJob);
            workWithVariance(wrappers, easyJob);
            workWithStandardDeviation(wrappers, easyJob);
            workWithSessionDuration(wrappers, easyJob);
            
        }
    }
    
    /**
     * Works with the number of touches during the usage session to calculate
     * the p-value using t test
     * @param wrappers the surveys wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    public static void workWithCounter(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Number of touches during usage session ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllCounters());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with the minimum touch interval (time between two consecutive 
     * touches) 
     * @param wrappers the surveys wrappers
     * @param easyJob ture if it is the easy test, false otherwise
     */
    public static void workWithMinInterval(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Minimum touch interval between two consecutive" + 
                " touches ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMinIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with the maximum touch interval (time between two consecutive 
     * touches)
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    public static void workWithMaxInterval(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Maximum touch interval between two consecutive" + 
                " touches ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMaxIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with the range of touch intervals (max - min interval)
     * @param wrappers the survey wrappers
     * @param easyJob true if it the easy test, false otherwise
     */
    public static void workWithRange(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Range of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllRanges());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with the mean of touch intervals
     * @param wrappers the survey wrappers
     * @param easyJob true if it the easy test, false otherwise
     */
    public static void workWithMean(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Mean of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMeansOfTouchIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }    
    
    /**
     * Works with the median of touch intervals
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    public static void workWithMedian(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Median of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMediansOfTouchIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with the variance of touch intervals
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    public static void workWithVariance(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Variance of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllVariancesOfTouchIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with the standard deviation of touch intervals
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    public static void workWithStandardDeviation(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Standard deviation of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllStandardDeviationsOfTouchIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Works with the duration of the usage session
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    public static void workWithSessionDuration(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Duration of the usage session ***");
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllSessionDurationsOfTouchIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
     
        printTTestResults(normalizedValues, easyJob);
    }
}
