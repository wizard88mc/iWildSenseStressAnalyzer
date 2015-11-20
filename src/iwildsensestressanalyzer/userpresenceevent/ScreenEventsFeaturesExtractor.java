package iwildsensestressanalyzer.userpresenceevent;

import iwildsensestressanalyzer.dataanalyzer.FeaturesExtractor;
import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * This class is used by a StressSurvey to store the ScreenEvents associated with
 * the Survey and to provide statistical information about the screen events
 * 
 * FEATURES calculated:
 * - Average time ON/OFF (both for ON/OFF events and UnlockScreen events)
 * - Average time to unlock the screen
 * - Number of ON/OFF among the three hours
 * - Number of Unlock Screen among the three hours
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class ScreenEventsFeaturesExtractor extends FeaturesExtractor {
    
    private final ArrayList<ScreenOnOff> screenOnOffEvents;
    private final ArrayList<UnlockedScreen> unlockedScreenEvents;
    
    public ScreenEventsFeaturesExtractor(ArrayList<ScreenOnOff> screenOnOffEvents, 
            ArrayList<UnlockedScreen> unlockedScreenEvents) {
        
        this.screenOnOffEvents = screenOnOffEvents; 
        this.unlockedScreenEvents = unlockedScreenEvents;
    }
    
    /**
     * Returns a list of all the on/off duration for the OnOffScreen events
     * @return a list of on-off duration if there is at least one ScreenOnOff event,
     * null otherwise
     */
    public ArrayList<Double> getAllOnOffDurationForOnOffEvents() {
        
        if (!screenOnOffEvents.isEmpty()) {
            
            ArrayList<Double> values = new ArrayList<Double>();
            
            for (ScreenOnOff event: screenOnOffEvents) {
                values.add((double) event.getOnOffDuration());
            }
            
            return values;
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculate statistic information about the time of on/off when the user
     * turns the screen on and off without unlocking it
     * @return [average, variance, standard deviation] if more than zero values, 
     * null otherwise
     */
    public Double[] calculateStatisticOnOffDurationForScreenOnOffEvents() {
        
        ArrayList<Double> values = getAllOnOffDurationForOnOffEvents();
        
        if (values != null) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list of all the duration of the UnlockedScreen events
     * @return a list of on-off duration if there is at least one UnlockScreen
     * event, null otherwise
     */
    public ArrayList<Double> getAllOnOffDurationForUnlockScreenEvents() {
        
        if (!unlockedScreenEvents.isEmpty()) {
            
            ArrayList<Double> values = new ArrayList<Double>();
            
            for (UnlockedScreen event: unlockedScreenEvents) {
                values.add((double) event.getOnOffDuration());
            }
            
            return values;
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculates statistic information about the time of on/off when the user
     * uses the phone, e.g. usage session duration
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise
     */
    public Double[] calculateStatisticOnOffDurationForUnlockScreenEvents() {
        
        ArrayList<Double> values = getAllOnOffDurationForUnlockScreenEvents();
        
        if (values != null) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list of all the unlock time of the UnlockedScreen events
     * @return a list of unlock time if there is at least one UnlockedScreen event,
     * null otherwise
     */
    public ArrayList<Double> getAllUnlockTimeForUnlockedScreenEvents() {
        
        if (!unlockedScreenEvents.isEmpty()) {
            
            ArrayList<Double> values = new ArrayList<Double>();
            
            for (UnlockedScreen event: unlockedScreenEvents) {
                values.add((double) event.getUnlockTime());
            }
            
            return values;
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculates statistic information about the unlock time when the user 
     * unlocks the phone
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise
     */
    public Double[] calculateStatisticUnlockTimeForUnlockedScreenEvents() {
        
        ArrayList<Double> values = getAllUnlockTimeForUnlockedScreenEvents();
        
        if (values != null) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns the number of ON/OFF screen events 
     * @return the number of ON/OFF screen events
     */
    public int getNumberOfScreenOnOff() {
        return this.screenOnOffEvents.size();
    }
    
    /**
     * Returns the number of UnlockedScreen events
     * @return the number of UnlockedScreen events
     */
    public int getNumberOfUnlockedScreen() {
        return this.unlockedScreenEvents.size();
    }
    
    /**
     * Returns the ScreenOnOff events
     * @return the ScreenOnOff events
     */
    public ArrayList<ScreenOnOff> getScreenOnOffEvents() {
        return this.screenOnOffEvents;
    }
    
    /**
     * Returns the UnlockedScreen events
     * @return the UnlockedScreen events
     */
    public ArrayList<UnlockedScreen> getUnlockedScreenEvents() {
        return this.unlockedScreenEvents;
    }
}
