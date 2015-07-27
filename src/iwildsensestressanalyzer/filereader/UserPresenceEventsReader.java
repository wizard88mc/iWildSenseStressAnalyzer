package iwildsensestressanalyzer.filereader;

import iwildsensestressanalyzer.participant.Participant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * It is responsible to read the UserPresenceEvents.csv file and to return
 * all the lines that correspond to an event of the screen 
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceEventsReader extends BasicFileReader {
    
    private static final String USER_PRESENCE_EVENTS_FILE_NAME = "UserPresenceEvents.csv";
    
    public static ArrayList<String> readAllFiles(Participant participant) {
        
        ArrayList<String> lines = new ArrayList<String>();
        
        BufferedReader reader;
        
        try {
            
            /**
             * Iterating over months and days to collect data from all the files
             */
            for (String month: MONTHS) {
                for (String day: DAYS) {
                    
                    File possibleFile = new File(FOLDER_DATA + FILE_SEPARATOR + 
                            FOLDER_PING_DATA + FILE_SEPARATOR + month + FILE_SEPARATOR 
                            + day + FILE_SEPARATOR + participant.getIMEI() + 
                            "_" + USER_PRESENCE_EVENTS_FILE_NAME);
                    
                    /**
                     * Checking if file exists to avoid to search for not 
                     * existing folders (especially for the day in a month)
                     */
                    if (possibleFile.exists()) {
                        
                        reader = new BufferedReader(new FileReader(possibleFile));
                        
                        String line;
                        
                        while ((line = reader.readLine()) != null) {
                            
                            lines.add(line);
                        }   
                    }
                }
            }
        }
        catch(FileNotFoundException exc) {
            System.out.println("FileNotFoundException in UserPresenceEventsReader");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println("IOException in UserPresenceEventsReader");
            exc.printStackTrace();
        }
        
        return lines;
    }
    
}
