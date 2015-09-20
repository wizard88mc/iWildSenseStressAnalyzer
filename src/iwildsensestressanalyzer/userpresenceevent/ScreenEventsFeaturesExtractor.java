package iwildsensestressanalyzer.userpresenceevent;

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
public class ScreenEventsFeaturesExtractor {
    
    private final ArrayList<ScreenOnOff> screenOnOffEvents;
    private final ArrayList<UnlockedScreen> unlockedScreenEvents;
    
    public ScreenEventsFeaturesExtractor(ArrayList<ScreenOnOff> screenOnOffEvents, 
            ArrayList<UnlockedScreen> unlockedScreenEvents) {
        
        this.screenOnOffEvents = screenOnOffEvents; 
        this.unlockedScreenEvents = unlockedScreenEvents;
    }
    
    public ScreenEventsFeaturesExtractor(StressSurvey survey, 
            UserPresenceAdvancedEventsWrapper wrapper) {
        
        screenOnOffEvents = new ArrayList<ScreenOnOff>();
        unlockedScreenEvents = new ArrayList<UnlockedScreen>();
        
        /**
         * Add to the screenOnOffEvents list all the ScreenOnOff events that 
         * started inside the validity time of the survey
         */
        for (ScreenOnOff screenOnOff: wrapper.getScreenOnOffEvents()) {
            
            if (StressSurvey.isEventStartedInsideSurveyValidityTime(
                    survey.getTimestamp(), screenOnOff.getOnTimestamp())) {
                
                screenOnOffEvents.add(screenOnOff);
            }
        }
        
        /**
         * Add to the unlockedScreenEvents list all the UnlockedScreen events 
         * that started inside the validity time of the survey
         */
        for (UnlockedScreen unlockedScreen: wrapper.getUnlockedScreenEvents()) {
            
            if (StressSurvey.isEventStartedInsideSurveyValidityTime(
                    survey.getTimestamp(), unlockedScreen.getOnTimestamp())) {
                
                unlockedScreenEvents.add(unlockedScreen);
            }
        }
    }
    
    /**
     * Calculate statistic information about the time of on/off when the user
     * turns the screen on and off without unlocking it
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise
     */
    public Long[] calculateStatisticOnOffDurationForScreenOnOffEvents() {
        
        ArrayList<Long> values = getAllOnOffDurationForOnOffEvents();
        
        if (values != null) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list of all the on/off duration for the OnOffScreen events
     * @return a list of on-off duration if there is at least one ScreenOnOff event,
     * null otherwise
     */
    public ArrayList<Long> getAllOnOffDurationForOnOffEvents() {
        
        if (!screenOnOffEvents.isEmpty()) {
            
            ArrayList<Long> values = new ArrayList<Long>();
            
            for (ScreenOnOff event: screenOnOffEvents) {
                values.add(event.getOnOffDuration());
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
    public Long[] calculateStatisticOnOffDurationForUnlockScreenEvents() {
        
        ArrayList<Long> values = getAllOnOffDurationForUnlockScreenEvents();
        
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
    public ArrayList<Long> getAllOnOffDurationForUnlockScreenEvents() {
        
        if (!unlockedScreenEvents.isEmpty()) {
            
            ArrayList<Long> values = new ArrayList<Long>();
            
            for (UnlockedScreen event: unlockedScreenEvents) {
                values.add(event.getOnOffDuration());
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
    public Long[] calculateStatisticUnlockTimeForUnlockedScreenEvents() {
        
        ArrayList<Long> values = getAllUnlockTimeForUnlockedScreenEvents();
        
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
    public ArrayList<Long> getAllUnlockTimeForUnlockedScreenEvents() {
        
        if (!unlockedScreenEvents.isEmpty()) {
            
            ArrayList<Long> values = new ArrayList<Long>();
            
            for (UnlockedScreen event: unlockedScreenEvents) {
                values.add(event.getUnlockTime());
            }
            
            return values;
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
     * Returns the UnlockedScreen events
     * @return the UnlockedScreen events
     */
    public ArrayList<UnlockedScreen> getUnlockedScreenEvents() {
        return this.unlockedScreenEvents;
    }
}
