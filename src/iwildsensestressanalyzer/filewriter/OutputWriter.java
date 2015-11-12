package iwildsensestressanalyzer.filewriter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * This class is responsible to write the output of the analysis on the correct
 * file (statistical significance, classification, etc.)
 * @author Matteo Ciman
 * @version 0.1
 */
public class OutputWriter {
    
    private static final String fileNameStatisticalSignificance = "output " 
            + File.separator + "statistical_significance.txt";
    
    private File outputFileStatisticalSignificance;
    private PrintWriter writerOutputFileStatisticalSignificance = null;
 
    public OutputWriter() {
        
        outputFileStatisticalSignificance = 
                new File(fileNameStatisticalSignificance);
        outputFileStatisticalSignificance.getParentFile().mkdirs();
        
        try {
            writerOutputFileStatisticalSignificance = 
                new PrintWriter(outputFileStatisticalSignificance);
        
        }
        catch(FileNotFoundException exc) {
            System.out.println("Unable to create file at OutputWriter");
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes a new line in the output file of the statistical significance 
     * analysis
     * @param line the line to add, can be null 
     */
    public void writeOutputStatisticalSignificance(String line) {
        
        writerOutputFileStatisticalSignificance.println(line);
    }
}
