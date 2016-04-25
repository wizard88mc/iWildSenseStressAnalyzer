package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.touches.TouchesBufferedFeaturesExtractor;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class WekaFeaturesForTouchesBuffered extends WekaFeaturesCreator {
    
    /**
     * CALCULATED FEATURES
     * - Counter (# of touches)
     *     - average
     *     - standard deviation
     * - Min interval (between two consecutive touches)
     *     - average
     *     - standard deviation
     * - Max interval (between two consecutive touches)
     *     - average
     *     - standard deviation
     * - Range (min - max interval)
     *     - average
     *     - standard deviation
     * - Mean 
     *     - average
     *     - standard deviation
     * - Median
     *     - average
     *     - standard deviation
     * - Variance
     *     - average
     *     - standard deviation
     * - Standard Deviation
     *     - average
     *     - standard deviation
     * - Session Duration
     *     - average
     *     - standard deviation
     */
    public static final String[] FEATURES_NAMES = {
        "TouchesBuffered_AVG_Counter", "TouchesBuffered_STD_Counter", 
        "TouchesBuffered_AVG_MinInterval", "TouchesBuffered_STD_MinInterval", 
        "TouchesBuffered_AVG_MaxInterval", "TouchesBuffered_STD_MaxInterval", 
        "TouchesBuffered_AVG_Range", "TouchesBuffered_STD_Range", 
        "TouchesBuffered_AVG_Mean", "TouchesBuffered_STD_Mean", 
        "TouchesBuffered_AVG_Median", "TouchesBuffered_STD_Median", 
        "TouchesBuffered_AVG_Variance", "TouchesBuffered_STD_Variance", 
        "TouchesBuffered_AVG_StandardDeviation", "TouchesBuffered_STD_StandardDeviation", 
        "TouchesBuffered_AVG_SessionDuration", "TouchesBuffered_STD_SessionDuration"};
    
    public static ArrayList<Double> getFeaturesForTouchesBufferedEvents(
            StressSurvey survey) {
        
        ArrayList<Double> features = new ArrayList<>();
        
        TouchesBufferedFeaturesExtractor featuresExtractor = 
                new TouchesBufferedFeaturesExtractor(survey.
                        getUserPresenceAdvancedEventsWrapper().
                        getUnlockedScreenEvents());
        
        Double[] statisticsCounterValues = featuresExtractor.calculateStatisticsCounterValues(),
                statisticsMinIntervalValues = featuresExtractor.calculateStatisticsMinIntervalValues(),
                statisticsMaxIntervalValues = featuresExtractor.calculateStatisticsMaxIntervalValues(),
                statisticsRangeValues = featuresExtractor.calculateStatisticsRangeValues(),
                statisticsMeanValues = featuresExtractor.calculateStatisticsMeanValues(), 
                statisticsMedianValues = featuresExtractor.calculateStatisticsMedianValues(), 
                statisticsVarianceValues = featuresExtractor.calculateStatisticsVarianceValues(), 
                statisticsStandardDeviationValues = featuresExtractor.calculateStatisticsStandardDeviationValues(), 
                statisticsSessionDurationValues = featuresExtractor.calculateStatisticsSessionDurationValues();
        
        addCalculatedFeatures(features, statisticsCounterValues);
        addCalculatedFeatures(features, statisticsMinIntervalValues);
        addCalculatedFeatures(features, statisticsMaxIntervalValues);
        addCalculatedFeatures(features, statisticsRangeValues);
        addCalculatedFeatures(features, statisticsMeanValues);
        addCalculatedFeatures(features, statisticsMedianValues);
        addCalculatedFeatures(features, statisticsVarianceValues);
        addCalculatedFeatures(features, statisticsStandardDeviationValues);
        addCalculatedFeatures(features, statisticsSessionDurationValues);
        
        return features;
    }
}
