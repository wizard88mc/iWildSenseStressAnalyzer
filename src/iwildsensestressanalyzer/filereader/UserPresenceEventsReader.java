package iwildsensestressanalyzer.filereader;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * It is responsible to read the UserPresenceEvents.csv file and to return
 * all the lines that correspond to an event of the screen 
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceEventsReader extends AllFilesReader {
    
    private static final String USER_PRESENCE_EVENTS_FILE_NAME = "UserPresenceEvents.csv";
    
    /**
     * Returns all the lines of the UserPersenceEvents file
     * @param participant the participant we want to retrieve data
     * @return a list of all the lines of the user presence events
     */
    public static ArrayList<String> getAllUserPresenceEventsLines(Participant participant) {
        
        return retrieveAllLines(USER_PRESENCE_EVENTS_FILE_NAME, participant);
    }
    
}
