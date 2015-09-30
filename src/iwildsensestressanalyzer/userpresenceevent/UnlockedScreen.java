package iwildsensestressanalyzer.userpresenceevent;

import iwildsensestressanalyzer.touches.TouchesBufferedEvent;
import java.util.ArrayList;

/**
 *
 * This class represents an event where 
 * 
 * @author Matteo Ciman
 * @version 1.0
 */
public class UnlockedScreen extends ScreenOnOff {

    protected UserPresenceEvent present;
    protected ArrayList<UserPresenceEvent> userEventsDuringOnOff; // can be null
    
    protected TouchesBufferedEvent touchesBufferedEvent;
    
    public UnlockedScreen(UserPresenceEvent on, UserPresenceEvent off,
            UserPresenceEvent present,
            ArrayList<UserPresenceEvent> eventsDuringOnOff) {
        
        super(on, off); this.userEventsDuringOnOff = eventsDuringOnOff;
        this.present = present;
    }
    
    /**
     * Adds the TouchesBufferedEvent to the UnlockedScreen event
     * @param event the vent to add
     */
    public void addTouchesBufferedEvent(TouchesBufferedEvent event) {
        this.touchesBufferedEvent = event;
    }
    
    /**
     * Returns the time necessary to the user to unlock the screen
     * @return time to unlock the screen
     */
    public long getUnlockTime() {
        return this.present.getTimestamp() - this.onEvent.getTimestamp();
    }
    
    public TouchesBufferedEvent getTouchesBufferedEvent() {
        return this.touchesBufferedEvent;
    }
}
