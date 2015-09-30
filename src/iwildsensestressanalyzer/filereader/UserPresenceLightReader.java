/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.filereader;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * @author Matteo
 */
public class UserPresenceLightReader extends AllFilesReader {
    
    private static final String USER_PRESENCE_LIGHT_FILE_NAME = 
            "UserPresenceLight.csv";
    
    /**
     * Retrieves all the lines from the corresponding file related to the 
     * UserPresenceLight events
     * @param participant the participant we are considering
     * @return a list of String representing UserPresenceLight events
     */
    public static ArrayList<String> getALlUserPresenceLightEventsLines(Participant participant) {
        
        return retrieveAllLines(USER_PRESENCE_LIGHT_FILE_NAME, participant);
    }
}
