package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.light.UserPresenceLightFeaturesExtractor;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class WekaFeaturesForUserPresenceLight extends WekaFeaturesCreator {
    
    /**
     * CALCULATED FEATURES
     * - Light values for ScreenOnOff events
     *    - average
     *    - standard deviation
     * - Light values for UnlockedScreen events
     *    - average
     *    - standard deviation
     * - Light values for all screen events
     *    - average
     *    - standard deviation
     */
    
    public static final String[] featuresName = {"AVG_LightValuesScreenOnOff", 
        "STD_LightValuesScreenOnOff", "AVG_LightValuesUnlockedScreen", 
        "STD_LightValuesUnlockedScreen", "AVG_LightValuesAllScreenEvents", 
        "STD_LightValuesAllScreenEvents"};
    
    /*
     * Creates a list of features with light values of screen events
     * @param survey the survey to consider
     * @return a list of features for the Light events
     */
    public ArrayList<Double> getFeaturesForUserPresenceLightEvent(
            StressSurvey survey) {
        
        ArrayList<Double> features = new ArrayList<Double>();
        
        UserPresenceLightFeaturesExtractor featuresExtractor = 
                new UserPresenceLightFeaturesExtractor(survey.
                        getUserPresenceAdvancedEventsWrapper().
                        getScreenOnOffEvents(), survey.
                                getUserPresenceAdvancedEventsWrapper().
                                getUnlockedScreenEvents());
        
        Double[] statisticsLightValuesForScreenOnOffEvents = 
                featuresExtractor.calculateStatisticsLightValuesForScreenOnOffEvents(), 
                statisticsLightValuesForUnlockedScreenEvents = 
                featuresExtractor.calculateStatisticsLightValuesForUnlockedScreenEvents(), 
                statisticsLightValuesForAllScreenEvents = 
                featuresExtractor.calculateStatisticsLightValuesForAllScreenEvents();
        
        addCalculatedFeatures(features, statisticsLightValuesForScreenOnOffEvents);
        addCalculatedFeatures(features, statisticsLightValuesForUnlockedScreenEvents);
        addCalculatedFeatures(features, statisticsLightValuesForAllScreenEvents);
        
        return MathUtils.normalizeData(features, 0.0, 1.0);
    }
}
