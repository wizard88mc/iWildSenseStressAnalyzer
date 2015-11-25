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
    public static final String[] featuresNames = {
        "ScreenEvents_AVG_OnOffForScreenOnOff", 
        "ScreenEvents_STD_OnOffForScreenOnOff", 
        "ScreenEvents_AVG_OnOffForUnlockedScreen", 
        "ScreenEvents_STD_OnOffForUnlockedScreen", 
        "ScreenEvents_AVG_UnlockTimeForUnlockedScreen", 
        "ScreenEvents_STD_UnlockTimeForUnlockedScreen"};
    
    /**
     * Creates a list of features for the ScreenEvent objects
     * @param survey the survey to consider
     * @return a list of features for the ScreenEvent objects
     */
    public static ArrayList<Double> getFeaturesForScreenEvents(StressSurvey survey) {
        
        ArrayList<Double> features = new ArrayList<Double>();
        
        ScreenEventsFeaturesExtractor featuresExtractor = 
                new ScreenEventsFeaturesExtractor(survey.
                        getUserPresenceAdvancedEventsWrapper().
                        getScreenOnOffEvents(), survey.
                                getUserPresenceAdvancedEventsWrapper().
                                getUnlockedScreenEvents());
        
        Double[] statisticsOnOffDurationScreenOnOffEvents = 
                    featuresExtractor.calculateStatisticOnOffDurationForScreenOnOffEvents(),
                statisticsOnOffDurationUnlockedScreenEvents = 
                    featuresExtractor.calculateStatisticOnOffDurationForUnlockScreenEvents(),
                statisticsUnlockTimeForUnlockedScreenEvents = 
                    featuresExtractor.calculateStatisticUnlockTimeForUnlockedScreenEvents();
        
        addCalculatedFeatures(features, statisticsOnOffDurationScreenOnOffEvents);
        addCalculatedFeatures(features, statisticsOnOffDurationUnlockedScreenEvents);
        addCalculatedFeatures(features, statisticsUnlockTimeForUnlockedScreenEvents);

        return features;
    }
}
