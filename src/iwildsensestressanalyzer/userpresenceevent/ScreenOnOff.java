package iwildsensestressanalyzer.userpresenceevent;

import iwildsensestressanalyzer.light.UserPresenceLightEvent;
import java.util.Calendar;

/**
 *
 * This class represent an event when the screen is turned on and off (but not 
 * necessarily unlocked). It contains an event related to the ON event an OFF
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class ScreenOnOff {
   
    protected UserPresenceEvent onEvent;
    protected UserPresenceEvent offEvent; // can be null in case of not complete data
    protected UserPresenceLightEvent userPresenceLightEvent = null;
    
    public ScreenOnOff(UserPresenceEvent onEvent, UserPresenceEvent offEvent) {
        this.onEvent = onEvent; this.offEvent = offEvent;
    }
    
    /**
     * Returns the start time of the Screen event, that is the timestamp of the 
     * ON Event
     * @return the start time of the Screen event
     */
    public long getOnTimestamp() {
        return onEvent.getTimestamp();
    }
    
    public long getOffTimestamp() {
        return offEvent.getTimestamp();
    }
    
    /**
     * Returns the length of the ON-OFF screen event
     * @return the duration of the ON-OFF event if there is an OFF event, 
     * otherwise -1
     */
    public long getOnOffDuration() {
        
        if (offEvent != null) {
            return offEvent.getTimestamp() - onEvent.getTimestamp();
        }
        else {
            return -1;
        }
    }
    
    /**
     * Sets the UserPresenceLightEvent
     * @param event the event related to the ScreenOnOff event
     */
    public void setUserPresenceLightEvent(UserPresenceLightEvent event) {
        this.userPresenceLightEvent = event;
    }
    
    /**
     * Returns the UserPresenceLightEvent with light information
     * @return the UserPresence
     */
    public UserPresenceLightEvent getUserPresenceLightEvent() {
        return this.userPresenceLightEvent;
    }
    
    /**
     * Checks if an event is inside the on and off timing of the screen event
     * @param timestamp the timestamp of the event to check
     * @return true if the event is inside the on/off timing of the screen event, 
     * false otherwise or if the off event = null
     */
    public boolean isOtherEventInsideScreenEvent(long timestamp) {
        
        if (onEvent == null || offEvent == null) {
            return false;
        }
        Calendar startTimeScreenEvent = Calendar.getInstance(),
                endTimeEventScreenEvent = Calendar.getInstance(),
                otherEvent = Calendar.getInstance();
        
        startTimeScreenEvent.setTimeInMillis(onEvent.getTimestamp());
        endTimeEventScreenEvent.setTimeInMillis(offEvent.getTimestamp());
        otherEvent.setTimeInMillis(timestamp);
        
        return (otherEvent.after(startTimeScreenEvent) && 
                otherEvent.before(endTimeEventScreenEvent));
    }
    
    public boolean isValid() {
        return onEvent != null && offEvent != null;
    }
}
