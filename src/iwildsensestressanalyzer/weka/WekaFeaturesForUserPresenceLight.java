package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.light.UserPresenceLightFeaturesExtractor;
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
    
    public static final String[] FEATURES_NAMES = {
        "UserPresenceLight_AVG_LightValuesScreenOnOff", 
        "UserPresenceLight_STD_LightValuesScreenOnOff", 
        "UserPresenceLight_AVG_LightValuesUnlockedScreen", 
        "UserPresenceLight_STD_LightValuesUnlockedScreen", 
        "UserPresenceLight_AVG_LightValuesAllScreenEvents", 
        "UserPresenceLight_STD_LightValuesAllScreenEvents"};
    
    /*
     * Creates a list of features with light values of screen events
     * @param survey the survey to consider
     * @return a list of features for the Light events
     */
    public static ArrayList<Double> getFeaturesForUserPresenceLightEvent(
            StressSurvey survey) {
        
        ArrayList<Double> features = new ArrayList<>();
        
        UserPresenceLightFeaturesExtractor featuresExtractor = 
                new UserPresenceLightFeaturesExtractor(survey.
                    getUserPresenceAdvancedEventsWrapper().getScreenOnOffEvents(), 
                    survey.getUserPresenceAdvancedEventsWrapper().
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
        
        return features;
    }
}
