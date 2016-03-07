package iwildsensestressanalyzer.filereader;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * Reads the ApplicationsUsed.csv file with information about events related 
 * to the applications used by the user
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class ApplicationsUsedReader extends AllFilesReader {
    
    protected static final String APPLICATIONS_USED_FILE_NAME = 
            "ApplicationsUsed.csv";
    
    /**
     * Retrieves all the lines of the ApplicationsUsed file for a particular 
     * participant
     * @param participant the participant
     * @return a list of lines representing ApplicationsUsed events
     */
    public static ArrayList<String> getAllApplicationsUsedEventsLines(Participant participant) {
        
        return retrieveAllLines(APPLICATIONS_USED_FILE_NAME, participant);
    }
    
}
