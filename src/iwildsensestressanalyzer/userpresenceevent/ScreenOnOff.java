package iwildsensestressanalyzer.userpresenceevent;

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
    
}
