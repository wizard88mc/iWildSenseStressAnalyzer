package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.userpresenceevent.ScreenEventsFeaturesExtractor;
import iwildsensestressanalyzer.utils.MathUtils;
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
    public static final String[] featuresNames = {"AVG_OnOffForScreenOnOff", 
        "STD_OnOffForScreenOnOff", "AVG_OnOffForUnlockedScreen", 
        "STD_OnOffForUnlockedScreen", "AVG_UnlockTimeForUnlockedScreen", 
        "STD_UnlockTimeForUnlockedScreen"};
    
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

        return MathUtils.normalizeData(features, 0.0, 1.0);
    }
}
