package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.touches.TouchesBufferedFeaturesExtractor;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class WekaFeaturesForTouchesBuffered extends WekaFeaturesCreator {
    
    public static ArrayList<Double> getFeaturesForTouchesBufferedEvents(
            StressSurvey survey) {
        
        ArrayList<Double> features = new ArrayList<Double>();
        
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
        
        return MathUtils.normalizeData(features, 0.0, 1.0);
    }
}
