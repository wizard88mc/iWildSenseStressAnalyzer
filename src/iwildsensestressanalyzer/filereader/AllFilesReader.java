package iwildsensestressanalyzer.filereader;

import static iwildsensestressanalyzer.filereader.BasicFileReader.MONTHS;
import iwildsensestressanalyzer.participant.Participant;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * Basic class for all classes that have to read all the files for all the 
 * days of the experiment
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class AllFilesReader extends BasicFileReader {
    
    public static final String LAST_LINE_MARKER = ",LAST_OF_DAY";
    
    /**
     * Retrieves all the lines of a specific output file for a participant
     * for all the days of the experiment
     * 
     * @param fileToUse the file to extract lines
     * @param participant the participant we want data
     * @return a list of lines from all the files
     */
    protected static ArrayList<String> retrieveAllLines(String fileToUse, 
            Participant participant) {
        
        ArrayList<String> linesEvents = new ArrayList<String>();
        
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
                            "_" + fileToUse);
                    
                    /**
                     * Checking if file exists to avoid to search for not 
                     * existing folders (especially for the day in a month)
                     */
                    if (possibleFile.exists()) {
                        
                        reader = new BufferedReader(new FileReader(possibleFile));
                        
                        String line;
                        
                        while ((line = reader.readLine()) != null) {
                            
                            linesEvents.add(line);
                        }   
                    }
                }
                if (!linesEvents.isEmpty()) {
                    String lastLine = linesEvents.get(linesEvents.size() - 1);
                    lastLine += LAST_LINE_MARKER;
                    linesEvents.set(linesEvents.size() - 1, lastLine);
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
        
        return linesEvents;
    }
    
    
    
}
