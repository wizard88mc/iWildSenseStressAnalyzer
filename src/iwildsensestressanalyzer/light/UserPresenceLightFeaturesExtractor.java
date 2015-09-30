package iwildsensestressanalyzer.light;

import iwildsensestressanalyzer.userpresenceevent.ScreenOnOff;
import iwildsensestressanalyzer.userpresenceevent.UnlockedScreen;
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
    
    public ArrayList<Double> getAllLightValuesForScreenOnOffEvents() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (ScreenOnOff event: screenOnOffEvents) {
             
            if (event.getUserPresenceLightEvent() != null) {
                values.add(event.getUserPresenceLightEvent().getLightValue());
            }
        }
        
        return values;
    }
    
    public ArrayList<Double> getAllLightValuesForUnlockedScreenEvents() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getUserPresenceLightEvent() != null) {
                values.add(event.getUserPresenceLightEvent().getLightValue());
            }
        }
        
        return values;
    }
    
    public ArrayList<Double> getAllLightValuesForAllScreenEvents() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        values.addAll(getAllLightValuesForScreenOnOffEvents());
        values.addAll(getAllLightValuesForUnlockedScreenEvents());
        
        return values;
    }
}
