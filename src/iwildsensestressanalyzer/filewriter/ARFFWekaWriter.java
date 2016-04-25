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
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class ARFFWekaWriter extends OutputFileWriter {
    
    private static final String STARTING_OUTPUT_FOLDER = BASE_OUTPUT_FOLDER + 
            "Classification" + File.separator;
    
    private static final String OUTPUT_FILE_NAME = "StressClassification";
    
    private File outputFile = null;
    
    private BufferedWriter outputWriter = null;
    
    public ARFFWekaWriter(String participantIMEI, String subfolder, 
            String classMappingName) {
        
        String finalOutputFile = STARTING_OUTPUT_FOLDER + subfolder + 
                File.separator + participantIMEI + File.separator + 
                OUTPUT_FILE_NAME + participantIMEI + classMappingName + ".arff";
        
        try {
            outputFile = new File(finalOutputFile);
            outputFile.getParentFile().mkdirs();
            
            outputWriter = new BufferedWriter(new FileWriter(outputFile));
        }
        catch(IOException exc) {
            System.out.println("IOException in creating WekaOutputFile");
            exc.printStackTrace();
            outputWriter = null;
        }
    }
    
    /**
     * Writes the WEKA preamble into the output file
     * @param listClasses the list of the possible labels
     */
    public void writePreambleWekaFile(String listClasses) {
        
        if (outputWriter != null) {
            
            try {
                outputWriter.write("@RELATION stress"); 
                outputWriter.newLine();
                
                createFeaturesPreamble();

                outputWriter.write("@ATTRIBUTE class {" + listClasses + "}");
                
                outputWriter.newLine();
                outputWriter.write("@DATA");
                outputWriter.newLine();
                
                outputWriter.flush();
            }
            catch(IOException exc) {
                System.out.println("Exception in writing preamble weka file");
                exc.printStackTrace();
            }
        }
    }
    
    /**
     * Creates the preamble for the features name
     * @param writer the output file writer
     */
    private void createFeaturesPreamble() {
        
        addNumericAttributeToPreamble(WekaFeaturesForApplicationUsed.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForTouchesBuffered.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForUserActivity.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForScreenEvents.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForUserPresenceLight.FEATURES_NAMES);
        addNumericAttributeToPreamble(WekaFeaturesForDaysAndWeather.FEATURES_NAMES);
    }
    
    /**
     * Adds a numeric feature (attribute) to the weka file
     * @param features an array of features names
     * @param writer the output file writer
     */
    private void addNumericAttributeToPreamble(String[] features) {
        
        for (String element: features) {
            try {
                outputWriter.write("@ATTRIBUTE " + element + " NUMERIC");
                outputWriter.newLine();
            }
            catch(IOException exc) {
                System.out.println("IOException in writing attribute features"
                        + " name: " + element);
                exc.printStackTrace();
            }
        }
    }
    
    /**
     * Writes a line of features and stress value on the two output weka files
     * @param features a list of String features to write
     */
    public void writeCalculatedFeaturesOnOutputFiles(ArrayList<String> features) {
        
        String outputString = "";
        for (String feature: features) {
            if (feature == null) {
                feature = "?";
            }
            
            outputString += feature + ",";
        }
        
        try {
            outputWriter.write(outputString);
        }
        catch(IOException exc) {
            System.out.println("IOException in writing calculated features for"
                    + " difficult file");
            exc.printStackTrace();
        }
    }
    
    /**
     * Write an instance on the output file
     * @param instance the string representation of the instance
     */
    public void writeInstance(String instance) {
        
        try {
            outputWriter.write(instance);
            outputWriter.flush();
            outputWriter.newLine();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing instance");
            exc.printStackTrace();
        }
    }
   
    /**
     * Closes the output files
     */
    public void closeFile() {
        
        try {
            outputWriter.flush(); outputWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closeFiles in ARFFWekaWriter");
            exc.printStackTrace();
        }   
    }
    
    /**
     * Return the two output files
     * @return a list with the easy and difficult files
     */
    public File getOutputFiles() {
         
       return outputFile;
    }
}
