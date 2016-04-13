package iwildsensestressanalyzer.filereader;

import java.io.File;

/**
 *
 * Base Class used for folders names and static values
 * 
 * @author Matteo Ciman
 * @version 1.0
 */
public class BasicFileReader {
    
    protected static final String FOLDER_DATA = "data";
    protected static final String FOLDER_PING_DATA = "ping_data";
    protected static final String FOLDER_SURVEY_DATA = "survey_data";
    protected static final String FOLDER_SETTINGS = "settings";
    protected static final String FILE_SEPARATOR = File.separator;
    protected static final String FILE_SURVEY = "_survey_events.csv";
    protected static final String FILE_WEATHER = "_weather_info.csv";
    
    protected static final String[] MONTHS = {"04", "05"};
    protected static final String[] DAYS = {"01", "02", "03", "04", "05", "06", 
        "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", 
        "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30",
        "31"};
    
}
