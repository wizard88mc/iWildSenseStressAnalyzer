package iwildsensestressanalyzer.userpresenceevent;

import iwildsensestressanalyzer.dataanalyzer.FeaturesExtractor;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;
import java.util.Collections;

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
            
            ArrayList<Double> values = new ArrayList<>();
            
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
     * Returns a list of all the time distance between two ScreenOnOff events
     * @return a list of time distance if there is at least two valid ScreenOnOff events,
     * null otherwise
     */
    public ArrayList<Double> getAllDistancesBetweenTwoScreenOnOffEvents() {
        
        if (!screenOnOffEvents.isEmpty()) {

            ArrayList<Double> values = new ArrayList<>();
            for (int i = 1; i < screenOnOffEvents.size(); i++) {
                if (screenOnOffEvents.get(i).isValid() && 
                        screenOnOffEvents.get(i - 1).isValid()) {
                    
                    values.add((double) (screenOnOffEvents.get(i).getOnTimestamp() - 
                        screenOnOffEvents.get(i - 1).getOffTimestamp()));
                }
            }
            
            if (!values.isEmpty()) {
                return values;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculates statistic information about the time distance between two 
     * consecutive ScreenOnOff events
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise
     */
    public Double[] calculateStatisticDistanceBetweenTwoScreenOnOffEvents() {
        
        ArrayList<Double> values = getAllDistancesBetweenTwoScreenOnOffEvents();
        
        if (values != null) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list of all the time distance between two UnlockedScreen events
     * @return a list of time distance if there is at least two valid 
     * UnlockedScreen events, null otherwise
     */
    public ArrayList<Double> getAllDistancesBetweenTwoUnlockedScreenEvents() {
        
        if (!unlockedScreenEvents.isEmpty()) {
            
            ArrayList<Double> values = new ArrayList<>();
            
            for (int i = 1; i < unlockedScreenEvents.size(); i++) {
                if (unlockedScreenEvents.get(i).isValid() && 
                        unlockedScreenEvents.get(i - 1).isValid()) {
                    
                    values.add((double) (unlockedScreenEvents.get(i).getOnTimestamp() - 
                        unlockedScreenEvents.get(i - 1).getOffTimestamp()));
                }
            }
            if (!values.isEmpty()) {
                return values;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculates statistic information about the time distance between two 
     * consecutive UnlockedScreen events
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise
     */
    public Double[] calcualteStatisticDistancesBetweenTwoUnlockedScreenEvents() {
        
        ArrayList<Double> values = getAllDistancesBetweenTwoUnlockedScreenEvents();
        if (values != null) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns the time distance between two consecutive ScreenEvent, both 
     * ScreenOnOff or UnlockedScreen
     * @return a list of time distance between two consecutive screen events
     */
    public ArrayList<Double> getAllDistancesBetweenTwoScreenEvents() {
        
        ArrayList<Double> values = new ArrayList<>();
        ArrayList<ScreenOnOff> screenEvents  = new ArrayList<>();
        screenEvents.addAll(screenOnOffEvents);
        screenEvents.addAll(unlockedScreenEvents);
        
        if (!screenEvents.isEmpty()) {
            for (int i = 0; i < screenEvents.size();) {
                if (!screenEvents.get(i).isValid()) {
                    screenEvents.remove(i);
                }
                else {
                    i++;
                }
            }

            Collections.sort(screenEvents, new ScreenEventsComparator());

            for (int i = 1; i < screenEvents.size(); i++) {
                values.add((double)(screenEvents.get(i).getOnTimestamp() - 
                        screenEvents.get(i - 1).getOffTimestamp()));
            }
            if (!values.isEmpty()) {
                return values;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
    }
    
    /**
     * Calculates statistic information about the time distance between two
     * consecutive screen events
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise
     */
    public Double[] calculateStatisticDistanceBetweenTwoScreenEvents() {
        
        ArrayList<Double> values = getAllDistancesBetweenTwoScreenEvents();
        
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
