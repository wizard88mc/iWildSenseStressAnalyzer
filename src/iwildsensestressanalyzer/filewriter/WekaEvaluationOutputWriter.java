package iwildsensestressanalyzer.filewriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * It is responsible to write on the output file with the result of the 
 * classification task
 * @author Matteo Ciman
 * @version 0.1
 */
public class WekaEvaluationOutputWriter extends OutputFileWriter {
    
    private static final String OUTPUT_FOLDER = BASE_OUTPUT_FOLDER + "Classification" + 
            File.separator;
    private static final String OUTPUT_FILE_NAME = "output_classification.txt";
    
    private File outputFile = null;
    
    private BufferedWriter outputWriter = null;
    
    public WekaEvaluationOutputWriter(String subfolder) {
        
        try {
            
            outputFile = new File(OUTPUT_FOLDER + subfolder + File.separator + 
                    OUTPUT_FILE_NAME);
            outputFile.getParentFile().mkdirs();
            
            outputWriter = new BufferedWriter(new FileWriter(outputFile));
            
        }
        catch(IOException exc) {
            System.out.println("IOException in creating WekaEvaluationOutputWriter");
            exc.printStackTrace();
        }   
    }
    
    /**
     * Writes on the output file the output string
     * @param outputString what to write on the output file
     */
    public void writeOnOutputFile(String outputString) {
        try {
            outputWriter.write(outputString);
            outputWriter.newLine();
            
            outputWriter.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing output for"
                    + " WekaEvaluationOutputWriter");
            exc.printStackTrace();
        }
    }
    
    /**
     * Closes the BufferedWriter
     */
    public void closeFile() {
        try {
            outputWriter.flush();
            outputWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closeFile in "
                    + "WekaEvaluationOutputWriter");
            exc.printStackTrace();
        } 
    }
}
