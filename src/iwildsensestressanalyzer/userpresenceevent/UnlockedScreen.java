package iwildsensestressanalyzer.userpresenceevent;

import iwildsensestressanalyzer.touches.TouchesBufferedEvent;
import java.util.ArrayList;
import java.util.Calendar;

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
}
