package iwildsensestressanalyzer.preference;

import iwildsensestressanalyzer.filewriter.OutputFileWriter;
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
import java.util.Arrays;

/**
 * Creates a preference file
 * @author Matteo Ciman
 */
public class PreferenceManager extends OutputFileWriter {
    
    private static final String STARTING_OUTPUT_FOLDER = BASE_OUTPUT_FOLDER + 
            "Preference" + File.separator;
    private static final String OUTPUT_FILE_NAME = "StressPreference";
    
    private File outputFile;
    private BufferedWriter writer;
    
    public PreferenceManager(String participantIMEI, String subfolder, 
            String classMappingName) {
        
        String finalFile = STARTING_OUTPUT_FOLDER + subfolder + 
                File.separator + participantIMEI + File.separator + 
                OUTPUT_FILE_NAME + participantIMEI + classMappingName + ".arff";
        
        try {
            
            outputFile = new File(finalFile);
            if (!outputFile.exists()) {
                outputFile.getParentFile().mkdirs();
            }
            
            writer = new BufferedWriter(new FileWriter(outputFile));
        }
        catch(IOException exc) {
            System.out.println("IOException in PreferenceManager");
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes the first line on the CSV file with the list of features 
     */
    public void writePreamble() {
        
        ArrayList<String> featuresNames = new ArrayList<>();
        featuresNames.addAll(Arrays.asList(WekaFeaturesForApplicationUsed.FEATURES_NAMES));
        featuresNames.addAll(Arrays.asList(WekaFeaturesForTouchesBuffered.FEATURES_NAMES));
        featuresNames.addAll(Arrays.asList(WekaFeaturesForUserActivity.FEATURES_NAMES));
        featuresNames.addAll(Arrays.asList(WekaFeaturesForScreenEvents.FEATURES_NAMES));
        featuresNames.addAll(Arrays.asList(WekaFeaturesForUserPresenceLight.FEATURES_NAMES));
        featuresNames.addAll(Arrays.asList(WekaFeaturesForDaysAndWeather.FEATURES_NAMES));
        
        String stringFeatures = "";
        for (String feature: featuresNames) {
            stringFeatures += feature + ",";
        }
        
        stringFeatures += "stress";
        
        try {
            writer.write(stringFeatures);
            writer.newLine();
            writer.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing preamble of preference file");
        }
    }
    
    /**
     * Writes a new instance on the output file
     * @param instance the string of the new instance
     */
    public void writeInstance(String instance) {
        
        try {
            writer.write(instance);
            writer.newLine();
            writer.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing instance on preference file");
            exc.printStackTrace();
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
            System.out.println("IOException in closing preference file");
            exc.printStackTrace();
        }
    }
}
