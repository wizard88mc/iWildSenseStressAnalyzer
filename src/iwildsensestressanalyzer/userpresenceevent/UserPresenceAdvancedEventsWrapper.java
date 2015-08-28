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
public class UserPresenceAdvancedEventsWrapper {
    
    private final ArrayList<ScreenOnOff> screenOnOffEvents;
    private final ArrayList<UnlockedScreen> unlockedScreenEvents;
    
    public UserPresenceAdvancedEventsWrapper(ArrayList<UserPresenceEvent> 
            userPresenceEventsList) {
        
        screenOnOffEvents = new ArrayList<ScreenOnOff>();
        unlockedScreenEvents = new ArrayList<UnlockedScreen>();
        
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
                    
                    /**
                     * Moving the index of two position to start searching for 
                     * a new event
                     */
                    index += 2;
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
                    while (userPresenceEventsList.get(index).isROTATION_0() || 
                            userPresenceEventsList.get(index).isROTATION_90() || 
                            userPresenceEventsList.get(index).isROTATION_180() || 
                            userPresenceEventsList.get(index).isROTATION_270()) {
                        
                        index++;
                    }
                    /**
                     * At the end of the while, index is where we have the OFF 
                     * event, between the PRESENT and the OFF event there are 
                     * all the rotation event
                     */
                    ArrayList<UserPresenceEvent> rotationEvents = null;
                    
                    if (index != startIndexForSomethingElse) {
                        rotationEvents = (ArrayList<UserPresenceEvent>)
                            userPresenceEventsList.subList(startIndexForSomethingElse, index);
                    }
                    
                    UserPresenceEvent offEvent = null;
                    if (userPresenceEventsList.get(index).isOFF()) {
                        offEvent = userPresenceEventsList.get(index);
                    }
                    
                    UnlockedScreen unlockedScreen = new UnlockedScreen(onEvent, 
                            offEvent, presentEvent, rotationEvents);
                    
                    unlockedScreenEvents.add(unlockedScreen);
                    
                    index++;
                }
            }
            /**
             * This happens when the first event is for example a PRESENT event, 
             * usually when the participant unlocks the screen to answer to
             * a phone call or something similar
             */
            else {
                index++;
            }
        }
    }
    
    /**
     * Returns the list of ScreenOnOff events 
     * @return the List of ScreenOnOff events
     */
    public ArrayList<ScreenOnOff> getScreenOnOffEvents() {
        return this.screenOnOffEvents;
    }
    
    /**
     * Returns the list of UnlockedScreen events
     * @return the List of UnlockedScreen events
     */
    public ArrayList<UnlockedScreen> getUnlockedScreenEvents() {
        return this.unlockedScreenEvents;
    }
}
