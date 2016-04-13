package iwildsensestressanalyzer.filereader;

import iwildsensestressanalyzer.participant.Participant;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class WeatherInfoReader extends BasicFileReader {
    
    public static ArrayList<String> readFile(String IMEI) {
        
        ArrayList<String> lines = new ArrayList<>();
        BufferedReader reader;
        
        try {
            
            reader = new BufferedReader(new FileReader(FOLDER_DATA + 
                    FILE_SEPARATOR + FOLDER_SURVEY_DATA + FILE_SEPARATOR + 
                    IMEI + FILE_WEATHER));
            
            String line;
            
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        catch(FileNotFoundException exc) {
            System.out.println("FileNotFoundException in WeatherInfoReader");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println("IOException in reading weather file");
            exc.printStackTrace();
        }
        
        return lines;
    }
    
}
