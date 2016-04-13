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
        
        ArrayList<String> labels = EventsAnalyzer.labels5LabelsTaks;
        if (easyJob) {
            labels = EventsAnalyzer.labels3LabelsTask;
        }
        
        printTitleMessage("*** ANALYZING TOUCHES BUFFERED FEATURES ***");
        
        if (!useAllTogether) {
            
            ArrayList<Integer> tTestPassedForCounter = new ArrayList<>(), 
                    tTestPassedForMinInterval = new ArrayList<>(), 
                    tTestPassedForMaxInterval = new ArrayList<>(),
                    tTestPassedForRange = new ArrayList<>(),
                    tTestPassedForMean = new ArrayList<>(),
                    tTestPassedForMedian = new ArrayList<>(), 
                    tTestPassedForVariance = new ArrayList<>(), 
                    tTestPassedForStandardDeviation = new ArrayList<>(),
                    tTestPassedForSessionDuration = new ArrayList<>();
            
            ArrayList<Integer> validTTestsForCounter = new ArrayList<>(), 
                    validTTestsForMinInterval = new ArrayList<>(), 
                    validTTestsForMaxInterval = new ArrayList<>(), 
                    validTTestsForRange = new ArrayList<>(), 
                    validTTestsForMean = new ArrayList<>(), 
                    validTTestsForMedian = new ArrayList<>(), 
                    validTTestsForVariance = new ArrayList<>(), 
                    validTTestsForStandardDeviation = new ArrayList<>(), 
                    validTTestsForSessionDuration = new ArrayList<>();
            
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
                        labels);
                addTTestResultsToFinalContainer(tTestPassedForCounter, 
                        returnedResults, validTTestsForCounter);
                
                returnedResults = workWithMinInterval(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForMinInterval, 
                        returnedResults, validTTestsForMinInterval);
                
                returnedResults = workWithMaxInterval(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForMaxInterval, 
                        returnedResults, validTTestsForMaxInterval);
                
                returnedResults = workWithRange(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForRange, 
                        returnedResults, validTTestsForRange);
                
                returnedResults = workWithMean(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForMean, 
                        returnedResults, validTTestsForMean);
                
                returnedResults = workWithMedian(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForMedian, 
                        returnedResults, validTTestsForMedian);
                
                returnedResults = workWithVariance(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForVariance, 
                        returnedResults, validTTestsForVariance);
                
                returnedResults = workWithStandardDeviation(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForStandardDeviation, 
                        returnedResults, validTTestsForStandardDeviation);
                
                returnedResults = workWithSessionDuration(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForSessionDuration, 
                        returnedResults, validTTestsForSessionDuration);
            }
            
            /**
             * Analyzing percentage of success of the counter feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForCounter, 
                    validTTestsForCounter, labels, "*** Percentage of success "
                            + "of number of touches during usage session ***");
            
            /**
             * Analyzing percentage of success of the min_interval feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForMinInterval, 
                    validTTestsForMinInterval, labels, "*** Percentage of "
                            + "success of minimum touch interval between two "
                            + "consecutive touches ***");
            
            /**
             * Analyzing percentage of success of the max_interval feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForMaxInterval, 
                    validTTestsForMaxInterval, labels, "*** Percentage of "
                            + "success of maximum touch interval between two "
                            + "consecutive touches ***");
            
            /**
             * Analyzing percentage of success of the range feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForRange, 
                    validTTestsForRange, labels, "*** Percentage of success of"
                            + " range of touch intervals ***");
            
            /**
             * Analyzing percentage of success of mean feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForMean, 
                    validTTestsForMean, labels, "*** Percentage of success of"
                            + " mean of touch intervals");
            
            /**
             * Analyzing percentage of success of median feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForMedian, 
                    validTTestsForMedian, labels, "*** Percentage of success "
                            + "of median of touch intervals");
            
            /**
             * Analyzing percentage of success of variance feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForVariance, 
                    validTTestsForVariance, labels, "*** Percentage of success"
                            + " of variance of touch intervals");
            
            /**
             * Analyzing percentage of success of standard_deviation feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForStandardDeviation, 
                    validTTestsForStandardDeviation, labels, "*** Percentage "
                            + "of success standard deviation of touch intervals"
                            + " ***");

            /**
             * Analyzing percentage of success of session_duration feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForSessionDuration, 
                    validTTestsForSessionDuration, labels, "*** Percentage of "
                            + "success of duration of the usage session ***");

        }
        else {
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithCounterValuesOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithMinIntervalValuesOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithMaxIntervalValuesOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithRangeValuesOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithMedianValuesOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithVarianceValuesOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithStandardDeviationValuesOfAllParticipants(allSurveyDataWrappers, 
                    labels);
            workWithSessionDurationValuesOfAllParticipants(allSurveyDataWrappers, 
                    labels);
        }
    }
    
    /**
     * Works with the number of touches during the usage session to calculate
     * the p-value using t test
     * @param wrappers the surveys wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithCounter(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Number of touches during usage session ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor()
                    .getAllCounters());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Analyzes data of counter values for all participants
     * @param listWrappers list of wrappers for all participants
     * @param labels list of labels for the output table
     */
    private static void workWithCounterValuesOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Number of touches during usage" + 
                " session ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsCounterValues();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            
            listValues.add(singleValues);
        }
        
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the minimum touch interval (time between two consecutive 
     * touches) 
     * @param wrappers the surveys wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithMinInterval(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Minimum touch interval between two consecutive" + 
                " touches ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMinIntervals());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Analyzes data of minimum touch interval combining together data of all 
     * the participants
     * @param listWrappers list of wrappers for all participants
     * @param labels list of labels for the output table
     */
    private static void workWithMinIntervalValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Minimum touch interval between" + 
                " two consecutive touches");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsMinIntervalValues();
                
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the maximum touch interval (time between two consecutive 
     * touches)
     * @param wrappers the survey wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithMaxInterval(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Maximum touch interval between two consecutive" + 
                " touches ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMaxIntervals());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        return printTTestResults(normalizedValues, labels);
    }
    
    /**
     * Analyzes data of maximum touch interval combining together data of all
     * the participants
     * @param listWrappers list of wrappers for all participants
     * @param labels list of labels for the output table
     */
    private static void workWithMaxIntervalValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Maximum touch interval " + 
                "between two consecutive touches ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValue = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsMaxIntervalValues();
                
                if (values != null && values[0] != -1) {
                    singleValue.add(values[0]);
                }
            }
            listValues.add(singleValue);
        }
        
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the range of touch intervals (max - min interval)
     * @param wrappers the survey wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithRange(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Range of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllRanges());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        return printTTestResults(normalizedValues, labels);
    }
    
    /**
     * Analyzes data about range of touch intervals combining together data
     * of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithRangeValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Range values of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listvalues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsRangeValues();
                
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listvalues.add(singleValues);
        }
        printTTestResults(listvalues, labels);
    }
    
    /**
     * Works with the mean of touch intervals
     * @param wrappers the survey wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithMean(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Mean of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor()
                    .getAllMeansOfTouchIntervals());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Analyzes data about mean of touch intervals combining together data of 
     * all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithMeanValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Mean of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsMeanValues();
                
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the median of touch intervals
     * @param wrappers the survey wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithMedian(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Median of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor().getAllMediansOfTouchIntervals());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Analyzes data about median of touch intervals combining together data
     * of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithMedianValuesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Median of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsMedianValues();
                
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the variance of touch intervals
     * @param wrappers the survey wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithVariance(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Variance of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor()
                    .getAllVariancesOfTouchIntervals());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Analyzes data about the variance of touch intervals combining together
     * data of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithVarianceValuesOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Variance of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double [] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsVarianceValues();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the standard deviation of touch intervals
     * @param wrappers the survey wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithStandardDeviation(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Standard deviation of touch intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor()
                    .getAllStandardDeviationsOfTouchIntervals());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Analyzes data about the standard deviation of touch intervals combining
     * together data of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithStandardDeviationValuesOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Standard deviation of touch " + 
                "intervals ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsStandardDeviationValues();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the duration of the usage session
     * @param wrappers the survey wrappers
     * @param labels list of labels for the output table
     */
    private static ArrayList<Boolean> workWithSessionDuration(SurveyDataWrapper[] wrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Duration of the usage session ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getTouchesBufferedFeaturesExtractor()
                    .getAllSessionDurationsOfTouchIntervals());
        }
     
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Analyzes data about the session duration of usage session combining
     * together data of all the participants
     * @param listWrappers list of wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithSessionDurationValuesOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Duration of the usage session ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getTouchesBufferedFeaturesExtractor()
                        .calculateStatisticsSessionDurationValues();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
}
