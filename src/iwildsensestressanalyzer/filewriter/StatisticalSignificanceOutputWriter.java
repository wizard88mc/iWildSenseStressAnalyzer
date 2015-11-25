package iwildsensestressanalyzer.filewriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This class is responsible to write the output of the analysis on the correct
 * file (statistical significance, classification, etc.)
 * @author Matteo Ciman
 * @version 0.1
 */
public class StatisticalSignificanceOutputWriter extends OutputFileWriter {
    
    private static final String fileNameStatisticalSignificance = outputFolder +
            "Statistical Significance" + File.separator + 
            "statistical_significance.txt";
    
    private File outputFileStatisticalSignificance;
    private BufferedWriter writerOutputFileStatisticalSignificance = null;
 
    public StatisticalSignificanceOutputWriter() {
        
        outputFileStatisticalSignificance = 
                new File(fileNameStatisticalSignificance);
        outputFileStatisticalSignificance.getParentFile().mkdirs();
        
        try {
            writerOutputFileStatisticalSignificance = 
                new BufferedWriter(new FileWriter(outputFileStatisticalSignificance));
        
        }
        catch(FileNotFoundException exc) {
            System.out.println("Unable to create file at OutputWriter");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            System.out.println("IOException in OutputWriter");
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes a new line in the output file of the statistical significance 
     * analysis
     * @param line the line to add, can be null 
     */
    public void writeOutputStatisticalSignificance(String line) {
        
        try {
            if (line != null) {
                writerOutputFileStatisticalSignificance.write(line);
                writerOutputFileStatisticalSignificance.newLine();
            }
            else {
                writerOutputFileStatisticalSignificance.newLine();
            }
            
            writerOutputFileStatisticalSignificance.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing StatisticalSignificance"
                    + " file");
            exc.printStackTrace();
        }
    }
    
    public void closeFile() {
        
        try {
            writerOutputFileStatisticalSignificance.flush();
            writerOutputFileStatisticalSignificance.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing file Statistical "
                    + "Significance");
            exc.printStackTrace();
        }
    }
}
