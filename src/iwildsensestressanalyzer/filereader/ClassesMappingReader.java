package iwildsensestressanalyzer.filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Matteo
 */
public class ClassesMappingReader extends BasicFileReader {
    
    private static final String FILE_NAME = "classes_mapping.csv";
    
    /**
     * Retrieves all the mapping to be used to create the final weka files
     * @return A list of maps, one of each has a mapping between the initial 
     * class value and the target one
     */
    public static ArrayList<HashMap<String, String>> getAllClassMapping() {
        
        ArrayList<HashMap<String, String>> maps = new ArrayList<>();
        
        BufferedReader reader;
        
        try {
            String line;
            reader = new BufferedReader(new FileReader(FOLDER_DATA + 
                    FILE_SEPARATOR + FOLDER_SETTINGS + FILE_SEPARATOR + FILE_NAME));
            
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                
                HashMap<String, String> mapping = new HashMap<>();
                
                /**
                 * Creating the mapping between the initial label and the final
                 * class
                 */
                for (int i = 0; i < elements.length - 1; i++) {
                    String[] map = elements[i].split("=>");
                    String targetClass;
                    if (map.length == 1) {
                        targetClass = "";
                    }
                    else {
                        targetClass = map[1];
                    }
                    mapping.put(map[0], targetClass);
                }
                
                /**
                 * The last element of the mapping is the name to be used to
                 * recognize the mapping
                 */
                mapping.put("map_name", elements[elements.length - 1]);
                
                maps.add(mapping);
            }
        }
        catch(FileNotFoundException exc) {
            System.out.println("File not found in ClassesMappingReader");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println("IOException in reading ClassessMapping file");
            exc.printStackTrace();
        }
        return maps;
    }
}
