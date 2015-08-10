package iwildsensestressanalyzer.userpresenceevent;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * This class is used to create a list of ScreenOnOff or UnlockedScreen objects
 * depending on the list of UserPresenceEvents for a Participant
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceAnalyzer {
    
    private ArrayList<ScreenOnOff> screenOnOffEvents;
    private ArrayList<UnlockedScreen> unlockedScreenEvents;
    
    public UserPresenceAnalyzer(Participant participant) {
        
        screenOnOffEvents = new ArrayList<ScreenOnOff>();
        unlockedScreenEvents = new ArrayList<UnlockedScreen>();
        
        ArrayList<UserPresenceEvent> userPresenceEventsList = 
                participant.getUserPresenceEventsList();
        
        /**
         * Now there are three different possibilities while analyzing 
         * UserPresenceEvent
         * 1) ON - OFF
         * 2) ON - PRESENT [- something else] - OFF
         * 3) PRESENT - OFF (not interesting, I think comes from a call)
         */
        
        for (int index = 0; index < userPresenceEventsList.size() - 1; ) {
            
            if (userPresenceEventsList.get(index).isON()) {
                
                if (userPresenceEventsList.get(index + 1).isOFF()) {
                    
                    /**
                     * In this case is a ScreenOnOff event without any 
                     * unlock operation
                     */
                    screenOnOffEvents.add(new ScreenOnOff(userPresenceEventsList.get(index), 
                            userPresenceEventsList.get(index + 1)));
                }
                else if (userPresenceEventsList.get(index + 1).isPRESENT()) {
                    
                    /**
                     * In this case there is an ON, followed by a PRESENT
                     * (screen unlocked event) and after 0 or more 
                     * ROTATION_something events and finally an OFF event
                     */
                    UserPresenceEvent onEvent = userPresenceEventsList.get(index),
                            presentEvent = userPresenceEventsList.get(index + 1);
                    
                    int startIndexForSomethingElse = index + 2;
                    while (!userPresenceEventsList.get(index).isOFF()) {
                        index++;
                    }
                    /**
                     * At the end of the while, index is where we have the OFF 
                     * event, between the PRESENT and the OFF event there are 
                     * all the rotation event
                     */
                    ArrayList<UserPresenceEvent> rotationEvents = (ArrayList<UserPresenceEvent>)
                            userPresenceEventsList.subList(startIndexForSomethingElse, index - 1);
                }
            }
            
            
        }
        
    }
    
}
