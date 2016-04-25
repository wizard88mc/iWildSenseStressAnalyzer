package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.userpresenceevent.ScreenEventsFeaturesExtractor;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class WekaFeaturesForScreenEvents extends WekaFeaturesCreator {
    
    /**
     * CALCULATED FEATURES
     * - On-Off duration for ScreenOnOff events
     *    - average 
     *    - standard deviation
     * - On-Off duration for UnlockedScreen events
     *    - average 
     *    - standard deviation
     * - Unlock time for UnlockedScreen events
     *    - average
     *    - standard deviation
     */
    public static final String[] FEATURES_NAMES = {
        "ScreenEvents_AVG_OnOffForScreenOnOff", 
        "ScreenEvents_STD_OnOffForScreenOnOff", 
        "ScreenEvents_AVG_OnOffForUnlockedScreen", 
        "ScreenEvents_STD_OnOffForUnlockedScreen", 
        "ScreenEvents_AVG_UnlockTimeForUnlockedScreen", 
        "ScreenEvents_STD_UnlockTimeForUnlockedScreen",
        "ScreenEvents_AVG_TimeDistanceScreenEvents",
        "ScreenEvents_STD_TimeDistanceScreenEvents",
        "ScreenEvents_AVG_TimeDistanceScreenOnOff",
        "ScreenEvents_STD_TimeDistanceScreenOnOff",
        "ScreenEvents_AVG_TimeDistanceUnlockedScreen",
        "ScreenEvents_STD_TimeDistanceUnlockedScreen",
        "ScreenEvents_OnOffScreen_Over_UnlockedScreen"};
    
    /**
     * Creates a list of features for the ScreenEvent objects
     * @param survey the survey to consider
     * @return a list of features for the ScreenEvent objects
     */
    public static ArrayList<Double> getFeaturesForScreenEvents(StressSurvey survey) {
        
        ArrayList<Double> features = new ArrayList<>();
        
        ScreenEventsFeaturesExtractor featuresExtractor = 
                new ScreenEventsFeaturesExtractor(survey.
                    getUserPresenceAdvancedEventsWrapper().getScreenOnOffEvents(), 
                    survey.getUserPresenceAdvancedEventsWrapper().
                        getUnlockedScreenEvents());
        
        Double[] statisticsOnOffDurationScreenOnOffEvents = 
                    featuresExtractor.calculateStatisticOnOffDurationForScreenOnOffEvents(),
                statisticsOnOffDurationUnlockedScreenEvents = 
                    featuresExtractor.calculateStatisticOnOffDurationForUnlockScreenEvents(),
                statisticsUnlockTimeForUnlockedScreenEvents = 
                    featuresExtractor.calculateStatisticUnlockTimeForUnlockedScreenEvents(), 
                statisticsDistanceBetweenTwoScreenEvents = 
                    featuresExtractor.calculateStatisticDistanceBetweenTwoScreenEvents(), 
                statisticsDistanceBetweenTwoScreenOnOffEvents = 
                    featuresExtractor.calculateStatisticDistanceBetweenTwoScreenOnOffEvents(), 
                statisticsDistanceBetweenTwoUnlockedScreenEvents = 
                    featuresExtractor.calcualteStatisticDistancesBetweenTwoUnlockedScreenEvents();
        
        addCalculatedFeatures(features, statisticsOnOffDurationScreenOnOffEvents);
        addCalculatedFeatures(features, statisticsOnOffDurationUnlockedScreenEvents);
        addCalculatedFeatures(features, statisticsUnlockTimeForUnlockedScreenEvents);
        addCalculatedFeatures(features, statisticsDistanceBetweenTwoScreenEvents);
        addCalculatedFeatures(features, statisticsDistanceBetweenTwoScreenOnOffEvents);
        addCalculatedFeatures(features, statisticsDistanceBetweenTwoUnlockedScreenEvents);
        features.add((double)featuresExtractor.getNumberOfScreenOnOff() / 
                (double) featuresExtractor.getNumberOfUnlockedScreen());

        return features;
    }
}
