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
            ArrayList<Participant> participants, boolean easyJob, 
            boolean useAllTogether) {
        
        printTitleMessage("*** ANALYZING TOUCHES BUFFERED FEATURES ***");
        
        if (!useAllTogether) {
            
            ArrayList<Double> tTestPassedForCounter = new ArrayList<Double>(), 
                    tTestPassedForMinInterval = new ArrayList<Double>(), 
                    tTestPassedForMaxInterval = new ArrayList<Double>(),
                    tTestPassedForRange = new ArrayList<Double>(),
                    tTestPassedForMean = new ArrayList<Double>(),
                    tTestPassedForMedian = new ArrayList<Double>(), 
                    tTestPassedForVariance = new ArrayList<Double>(), 
                    tTestPassedForStandardDeviation = new ArrayList<Double>(),
                    tTestPassedForSessionDuration = new ArrayList<Double>();
            
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

                ArrayList<Boolean> returnedResults = workWithCounter(wrappers, 
                        easyJob);
                addTTestResultsToFinalContainer(tTestPassedForCounter, 
                        returnedResults);
                
                returnedResults = workWithMinInterval(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForMinInterval, 
                        returnedResults);
                
                returnedResults = workWithMaxInterval(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForMaxInterval, 
                        returnedResults);
                
                returnedResults = workWithRange(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForRange, 
                        returnedResults);
                
                returnedResults = workWithMean(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForMean, 
                        returnedResults);
                
                returnedResults = workWithMedian(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForMedian, 
                        returnedResults);
                
                returnedResults = workWithVariance(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForVariance, 
                        returnedResults);
                
                returnedResults = workWithStandardDeviation(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForStandardDeviation, 
                        returnedResults);
                
                returnedResults = workWithSessionDuration(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForSessionDuration, 
                        returnedResults);
            }
            
            /**
             * Analyzing percentage of success of the counter feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForCounter, 
                    participants.size(), easyJob, "*** Percentage of success of "
                            + "number of touches during usage session ***");
            
            /**
             * Analyzing percentage of success of the min_interval feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForMinInterval, 
                    participants.size(), easyJob, "*** Percentage of success of "
                            + "minimum touch interval between two consecutive"
                            + " touches ***");
            
            /**
             * Analyzing percentage of success of the max_interval feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForMaxInterval, 
                    participants.size(), easyJob, "*** Percentage of success of "
                            + "maximum touch interval between two consecutive"
                            + " touches ***");
            
            /**
             * Analyzing percentage of success of the range feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForRange, 
                    participants.size(), easyJob, "*** Percentage of success of"
                            + " range of touch intervals ***");
            
            /**
             * Analyzing percentage of success of mean feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForMean, 
                    participants.size(), easyJob, "*** Percentage of success of"
                            + " mean of touch intervals");
            
            /**
             * Analyzing percentage of success of median feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForMedian, 
                    participants.size(), easyJob, "*** Percentage of success of"
                            + " median of touch intervals");
            
            /**
             * Analyzing percentage of success of variance feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForVariance, 
                    participants.size(), easyJob, "*** Percentage of success of"
                            + " variance of touch intervals");
            
            /**
             * Analyzing percentage of success of standard_deviation feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForStandardDeviation, 
                    participants.size(), easyJob, "*** Percentage of success "
                            + "standard deviation of touch intervals ***");

            /**
             * Analyzing percentage of success of session_duration feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForSessionDuration, 
                    participants.size(), easyJob, "*** Percentage of success of"
                            + " duration of the usage session ***");

        }
        else {
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithCounterValuesOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithMinIntervalValuesOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithMaxIntervalValuesOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithRangeValuesOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithMedianValuesOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithVarianceValuesOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithStandardDeviationValuesOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithSessionDurationValuesOfAllParticipants(allSurveyDataWrappers, easyJob);
        }
    }
    
    /**
     * Works with the number of touches during the usage session to calculate
     * the p-value using t test
     * @param wrappers the surveys wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithCounter(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Number of touches during usage session ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor()
                    .getAllCounters());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Analyzes data of counter values for all participants
     * @param listWrappers list of wrappers for all participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithCounterValuesOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Number of touches during usage" + 
                " session ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValues = new ArrayList<Double>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsCounterValues();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            
            listValues.add(singleValues);
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the minimum touch interval (time between two consecutive 
     * touches) 
     * @param wrappers the surveys wrappers
     * @param easyJob ture if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithMinInterval(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Minimum touch interval between two consecutive" + 
                " touches ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMinIntervals());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Analyzes data of minimum touch interval combining together data of all 
     * the participants
     * @param listWrappers list of wrappers for all participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithMinIntervalValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Minimum touch interval between" + 
                " two consecutive touches");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsMinIntervalValues();
                
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the maximum touch interval (time between two consecutive 
     * touches)
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithMaxInterval(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Maximum touch interval between two consecutive" + 
                " touches ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMaxIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        return printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Analyzes data of maximum touch interval combining together data of all
     * the participants
     * @param listWrappers list of wrappers for all participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithMaxIntervalValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Maximum touch interval " + 
                "between two consecutive touches ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValue = new ArrayList<Double>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsMaxIntervalValues();
                
                if (values != null && values[0] != -1) {
                    singleValue.add(values[0]);
                }
            }
            listValues.add(singleValue);
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the range of touch intervals (max - min interval)
     * @param wrappers the survey wrappers
     * @param easyJob true if it the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithRange(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Range of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllRanges());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        return printTTestResults(normalizedValues, easyJob);
    }
    
    /**
     * Analyzes data about range of touch intervals combining together data
     * of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param easyJob if it is the easy test, false otherwise
     */
    private static void workWithRangeValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Range values of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listvalues = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsRangeValues();
                
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listvalues.add(singleValues);
        }
        printTTestResults(listvalues, easyJob);
    }
    
    /**
     * Works with the mean of touch intervals
     * @param wrappers the survey wrappers
     * @param easyJob true if it the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithMean(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Mean of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor()
                    .getAllMeansOfTouchIntervals());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Analyzes data about mean of touch intervals combining together data of 
     * all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param easyJob if it is the easy test, false otherwise
     */
    private static void workWithMeanValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Mean of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsMeanValues();
                
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the median of touch intervals
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithMedian(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Median of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMediansOfTouchIntervals());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Analyzes data about median of touch intervals combining together data
     * of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithMedianValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Median of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsMedianValues();
                
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the variance of touch intervals
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithVariance(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Variance of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllVariancesOfTouchIntervals());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Analyzes data about the variance of touch intervals combining together
     * data of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithVarianceValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Variance of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double [] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsVarianceValues();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the standard deviation of touch intervals
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithStandardDeviation(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Standard deviation of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor()
                    .getAllStandardDeviationsOfTouchIntervals());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Analyzes data about the standard deviation of touch intervals combining
     * together data of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithStandardDeviationValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Standard deviation of touch " + 
                "intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsStandardDeviationValues();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the duration of the usage session
     * @param wrappers the survey wrappers
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithSessionDuration(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        printTitleMessage("*** Duration of the usage session ***");
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllSessionDurationsOfTouchIntervals());
        }
     
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Analyzes data about the session duration of usage session combining
     * together data of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param easyJob true if it is the easy test, false otherwise
     */
    private static void workWithSessionDurationValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Duration of the usage session ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsSessionDurationValues();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, easyJob);
    }
}
