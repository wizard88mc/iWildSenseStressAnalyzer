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
    protected UserPresenceEvent offEvent;
    
    public ScreenOnOff(UserPresenceEvent onEvent, UserPresenceEvent offEvent) {
        this.onEvent = onEvent; this.offEvent = offEvent;
    }
    
    /**
     * Returns the length of the ON-OFF screen event
     * @return 
     */
    public long getOnOffDuration() {
        return offEvent.getTimestamp() - onEvent.getTimestamp();
    }
    
}
