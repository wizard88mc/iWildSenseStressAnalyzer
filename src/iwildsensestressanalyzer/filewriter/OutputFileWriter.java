package iwildsensestressanalyzer.filewriter;

import java.io.File;
import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Base class for output file writer
 * @author Matteo Ciman
 */
public class OutputFileWriter {
    
    protected static String BASE_OUTPUT_FOLDER = "data" + 
            File.separator + "output" + File.separator;
    
    static {
        
        GregorianCalendar calendar = new GregorianCalendar();
        
        String secondPart = String.valueOf(calendar.get(Calendar.YEAR)) + "_" + 
                calendar.get(Calendar.MONTH) + "_" + 
                calendar.get(Calendar.DAY_OF_MONTH) + "_" +
                calendar.get(Calendar.HOUR_OF_DAY) + "_" +
                calendar.get(Calendar.MINUTE) + "_" +
                calendar.get(Calendar.SECOND);
        
        BASE_OUTPUT_FOLDER += secondPart + File.separator;
        
    }
    
}
