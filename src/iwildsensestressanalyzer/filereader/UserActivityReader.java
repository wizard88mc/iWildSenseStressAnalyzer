package iwildsensestressanalyzer.filereader;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * This class is responsible to read the UserActivity.csv file and to return
 * all the lines that correspond to the detected activity of a participant
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserActivityReader extends AllFilesReader {
    
    private static final String USER_ACTIVITY_FILE_NAME = "UserActivity.csv";
    
    /**
     * Returns all the lines related to the user activity for all the days
     * of the experiment
     * 
     * @param participant the participant we are collecting data
     * @return UserActivity lines
     */
    public static ArrayList<String> getAllUserActivityEventsLines(Participant participant) {
        
        return retrieveAllLines(USER_ACTIVITY_FILE_NAME, participant);
    }
    
}
