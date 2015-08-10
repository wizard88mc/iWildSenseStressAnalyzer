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

    protected ArrayList<UserPresenceEvent> userEventsDuringOnOff;
    
    public UnlockedScreen(UserPresenceEvent on, UserPresenceEvent off, 
            ArrayList<UserPresenceEvent> eventsDuringOnOff) {
        
        super(on, off); this.userEventsDuringOnOff = eventsDuringOnOff;
    }
}
