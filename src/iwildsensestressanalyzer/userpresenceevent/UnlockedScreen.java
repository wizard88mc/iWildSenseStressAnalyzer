package iwildsensestressanalyzer.userpresenceevent;

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
    
    public UnlockedScreen(UserPresenceEvent on, UserPresenceEvent off,
            UserPresenceEvent present,
            ArrayList<UserPresenceEvent> eventsDuringOnOff) {
        
        super(on, off); this.userEventsDuringOnOff = eventsDuringOnOff;
        this.present = present;
    }
    
    /**
     * Returns the time necessary to the user to unlock the screen
     * @return time to unlock the screen
     */
    public long getUnlockTime() {
        return this.present.getTimestamp() - this.onEvent.getTimestamp();
    }
}
