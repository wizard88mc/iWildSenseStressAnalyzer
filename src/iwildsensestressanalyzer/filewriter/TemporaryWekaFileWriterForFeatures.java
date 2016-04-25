package iwildsensestressanalyzer.filewriter;

import iwildsensestressanalyzer.weka.WekaFeaturesForApplicationUsed;
import iwildsensestressanalyzer.weka.WekaFeaturesForDaysAndWeather;
import iwildsensestressanalyzer.weka.WekaFeaturesForScreenEvents;
import iwildsensestressanalyzer.weka.WekaFeaturesForTouchesBuffered;
import iwildsensestressanalyzer.weka.WekaFeaturesForUserActivity;
import iwildsensestressanalyzer.weka.WekaFeaturesForUserPresenceLight;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Writes a temporary csv file to store calculated features before using SMOTHE
 * filter to create the final .arff file for weka
 * @author Matteo Ciman
 */
public class TemporaryWekaFileWriterForFeatures extends OutputFileWriter {
    
    private final String FILE_NAME = "temporary_weka.arff";
    private File outputFile = null;
    private BufferedWriter writer = null;
    
    public TemporaryWekaFileWriterForFeatures(String listClasses) {
        
        try {
            outputFile = new File(BASE_OUTPUT_FOLDER + "Classification" + 
                    File.separator + FILE_NAME);
            
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            }
            
            writer = new BufferedWriter(new FileWriter(outputFile));
            
            writer.write("@RELATION stress"); 
            writer.newLine();
                
            createPreamble();

            writer.write("@ATTRIBUTE class {" + listClasses + "}");
                
            writer.newLine();
            writer.write("@DATA");
            writer.newLine();
                
            writer.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in creating temporary file");
            exc.printStackTrace();
            outputFile = null;
        }
    }
    
    private void createPreamble() {
        addNumericAttributeToPreamble(WekaFeaturesForApplicationUsed.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForTouchesBuffered.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForUserActivity.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForScreenEvents.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForUserPresenceLight.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForDaysAndWeather.FEATURES_NAMES);
    }
    
    private void addNumericAttributeToPreamble(String[] features) {
        
        for (String element: features) {
            try {
                writer.write("@ATTRIBUTE " + element + " NUMERIC");
                writer.newLine();
            }
            catch(IOException exc) {
                System.out.println("IOException in writing attribute features"
                        + " name: " + element);
                exc.printStackTrace();
            }
        }
    }
    
    public void writeLine(ArrayList<String> features, String instanceClass) {
        
        if (outputFile != null && writer != null) {
            try {
                String line = "";
                for (String feature: features) {
                    line += feature + ",";
                }
                
                line += instanceClass;
                
                line = line.replace("null", "?");

                writer.write(line);
                writer.newLine();
                writer.flush();
            }
            catch(IOException exc) {
                System.out.println("IOexception in writing line in temporary file");
                exc.printStackTrace();
            }
        }
    }
    
    /**
     * Closes the output file
     */
    public void closeFile() {
        try {
            writer.flush();
            writer.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing temporary file");
            exc.printStackTrace();
        }
    }
    
    /**
     * Returns the output file
     * @return the output File
     */
    public File getFile() {
        return this.outputFile;
    }
    
    /**
     * Deletes the temporary file
     */
    public void deleteFile() {
        if (outputFile != null) {
            outputFile.delete();
        }
    }
}
