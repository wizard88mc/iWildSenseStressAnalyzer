package iwildsensestressanalyzer.filereader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 *
 * @author Matteo Ciman
 */
public class WeatherMappingReader extends BasicFileReader {
    
    private static final String FILE_NAME = "weather_mapping.csv";
    
    private static final HashMap<String, String> WEATHER_MAP = new HashMap();
    
    static {
        
        try {
            BufferedReader reader = 
                    new BufferedReader(new FileReader(FOLDER_DATA + 
                            FILE_SEPARATOR + FOLDER_SETTINGS + FILE_SEPARATOR + 
                            FILE_NAME));
            
            String line;
            while ((line = reader.readLine()) != null) {
                String[] elements = line.split(",");
                WEATHER_MAP.put(elements[0], elements[1]);
            }
        }
        catch(FileNotFoundException exc) {
            System.out.println("FileNotFound in WeatherMappingReader");
        }
        catch(IOException exc) {
            System.out.println("IOException in reader WeatherMapping file");
        }
    }
    
    /**
     * Returns the categorization for the weather value
     * @param key the initial weather description
     * @return the weather category
     */
    public static String getWeatherValue(String key) {
        if (WEATHER_MAP.containsKey(key)){
            return WEATHER_MAP.get(key);
        }
        else {
            System.out.println("Category not found for: " + key);
            return null;
        }
    }
}
