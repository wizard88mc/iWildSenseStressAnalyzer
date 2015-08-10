package iwildsensestressanalyzer.filereader;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * It is responsible to read the ActivityServService.csv file and to return
 * all the lines that corresponds to the services information collected
 * every 60 seconds
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class ActivityServServiceReader extends AllFilesReader {
    
    private static final String ACTIVITY_SERV_SERVICE_FILE_NAME = 
            "ActivityServService.csv";
    
    /**
     * Returns all the lines of the ActivityServService file
     * @param participant the participant we want to retrieve data
     * @return a list of all the lines of the ActivityServService events
     */
    public static ArrayList<String> getAllActivityServServiceEventsLines(Participant participant) {
        
        return retrieveAllLines(ACTIVITY_SERV_SERVICE_FILE_NAME, participant);
    }
    
}
