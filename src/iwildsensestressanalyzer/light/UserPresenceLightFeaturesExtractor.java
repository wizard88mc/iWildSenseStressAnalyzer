package iwildsensestressanalyzer.light;

import iwildsensestressanalyzer.userpresenceevent.ScreenOnOff;
import iwildsensestressanalyzer.userpresenceevent.UnlockedScreen;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceLightFeaturesExtractor {
    
    private final ArrayList<ScreenOnOff> screenOnOffEvents;
    private final ArrayList<UnlockedScreen> unlockedScreenEvents;
    
    public UserPresenceLightFeaturesExtractor(ArrayList<ScreenOnOff> screenOnOffEvents, 
            ArrayList<UnlockedScreen> unlockedScreenEvents) {
        
        this.screenOnOffEvents = screenOnOffEvents; 
        this.unlockedScreenEvents = unlockedScreenEvents;
    }
    
    /**
     * Retrieves all the light values for the ScreenOnOff events
     * @return a list of double values of light values of ScreenOnOff events
     */
    public ArrayList<Double> getAllLightValuesForScreenOnOffEvents() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (ScreenOnOff event: screenOnOffEvents) {
             
            if (event.getUserPresenceLightEvent() != null) {
                values.add(event.getUserPresenceLightEvent().getLightValue());
            }
        }
        
        return values;
    }
    
    /**
     * Returns statistics information about the light values of ScreenOnOff
     * events 
     * @return [average, variance, standard deviation] if more than zero values, 
     * null if no values available
     */
    public Double[] calculateStatisticsLightValuesForScreenOnOffEvents() {
        
        ArrayList<Double> values = getAllLightValuesForScreenOnOffEvents();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
        
    }
    
    /**
     * Retrieves all the light values for the UnlockedScreen events
     * @return a lit of double values of light values of UnlockedScreen events
     */
    public ArrayList<Double> getAllLightValuesForUnlockedScreenEvents() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getUserPresenceLightEvent() != null) {
                values.add(event.getUserPresenceLightEvent().getLightValue());
            }
        }
        
        return values;
    }
    
    /**
     * Returns statistic information about the light values of the UnlockedScreen
     * events
     * @return [average, variance, standard deviation] if more than zero values, 
     * null if no values available
     */
    public Double[] calculateStatisticsLightValuesForUnlockedScreenEvents() {
        
        ArrayList<Double> values = getAllLightValuesForUnlockedScreenEvents();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
        
    }
    
    /**
     * Retrieves all the light values both for the ScreenOnOff and the 
     * UnlockedScreen events
     * @return a list of double values with light values for all screen events
     */
    public ArrayList<Double> getAllLightValuesForAllScreenEvents() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        values.addAll(getAllLightValuesForScreenOnOffEvents());
        values.addAll(getAllLightValuesForUnlockedScreenEvents());
        
        return values;
    }
    
    /**
     * Returns statistic information about the light values of the ScreenOnOff 
     * and the UnlockedScreen events
     * @return [average, variance, standard deviation] if more than zero values, 
     * null otherwise
     */
    public Double[] calculateStatisticsLightValuesForAllScreenEvents() {
        
        ArrayList<Double> values = getAllLightValuesForAllScreenEvents();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
}
