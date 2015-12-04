package iwildsensestressanalyzer.filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/*
 * This class is responsible to read the file that contains the list 
 * of the IMEIs of the participants of the study
 * 
 * @author Matteo Ciman
 * @version 0.1
 * 
 */

public class IMEIListReader extends BasicFileReader {

    private static final String FILE = "imeis copy.csv"; //FOR DEBUG
    //private static final String FILE = "imeis.csv";
    
    /**
     * Retrieves the list of IMEI of the participants of the study
     * @return the list of IMEI of the participants
     */
    public static ArrayList<String> getIMEIsList() {
        
        ArrayList<String> imeis = new ArrayList<>();
        
        BufferedReader reader = null;
        
        try {
            
            String line;
            
            reader = new BufferedReader(new FileReader(FOLDER_DATA + 
                    FILE_SEPARATOR + FILE));
            
            /**
             * Each line of the file is the IMEI of one participant
             */
            while ((line = reader.readLine()) != null) {
                
                if (!line.contains("//")) { // removing participant
                    imeis.add(line);
                }
            }
            
        }
        catch(FileNotFoundException exc) {
            System.out.println("FileNotFoundException in IMEIListReader");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println("IOExcetpion in IMEIListReader");
            exc.printStackTrace();
        }
        
        return imeis;
    }
}
