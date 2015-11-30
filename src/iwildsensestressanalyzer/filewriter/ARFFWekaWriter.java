/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.filewriter;

import iwildsensestressanalyzer.weka.WekaFeaturesForApplicationUsed;
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
    
    private static final String outpufFileName = "StressClassification";
    
    private File outputFileEasy = null, outputFileDifficult = null;
    
    private BufferedWriter outputFileWriterEasy = null, 
                outputFileWriterDifficult = null;
    
    public ARFFWekaWriter(String participantIMEI, String subfolder) {
        
        String finalOutputFile = STARTING_OUTPUT_FOLDER + subfolder + File.separator 
                + participantIMEI + File.separator + outpufFileName + participantIMEI;
        
        try {
            outputFileEasy = new File(finalOutputFile + "EASY.arff");
            outputFileEasy.getParentFile().mkdirs();
            
            outputFileWriterEasy = new BufferedWriter(new FileWriter(outputFileEasy));
            
            outputFileDifficult = new File(finalOutputFile + "DIFFICULT.arff");
            outputFileDifficult.getParentFile().mkdirs();
            
            outputFileWriterDifficult = new BufferedWriter(new FileWriter(outputFileDifficult));
            
            writePreambleWekaFile(outputFileWriterEasy, true); 
            writePreambleWekaFile(outputFileWriterDifficult, false);
        }
        catch(IOException exc) {
            System.out.println("IOException in creting WekaOutputFile");
            exc.printStackTrace();
            outputFileWriterEasy = null; outputFileWriterDifficult = null;
        }
    }
    
    /**
     * Writes the WEKA preamble into the output file
     * @param writer the output writer
     * @param easyTask true if it is easy classification (three classes), 
     * false otherwise
     */
    private void writePreambleWekaFile(BufferedWriter writer, boolean easyTask) {
        
        if (writer != null) {
            
            try {
                writer.write("@RELATION stress"); 
                writer.newLine();
                
                createFeaturesPreamble(writer);

                if (easyTask) {
                    writer.write("@ATTRIBUTE class {1,2,3}");
                }
                else {
                    writer.write("@ATTRIBUTE class {1,2,3,4,5}");
                }
                writer.newLine();
                writer.write("@DATA");
                writer.newLine();
                
                writer.flush();
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
    private void createFeaturesPreamble(BufferedWriter writer) {
        
        addNumericAttributeToPreamble(WekaFeaturesForApplicationUsed.featuresName, writer);
        addNumericAttributeToPreamble(WekaFeaturesForTouchesBuffered.featuresName, writer);
        addNumericAttributeToPreamble(WekaFeaturesForUserActivity.featuresName, writer);
        addNumericAttributeToPreamble(WekaFeaturesForUserPresenceLight.featuresName, writer);
    }
    
    /**
     * Adds a numeric feature (attribute) to the weka file
     * @param features an array of features names
     * @param writer the output file writer
     */
    private void addNumericAttributeToPreamble(String[] features, 
            BufferedWriter writer) {
        
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
    
    /**
     * Writes a line of features and stress value on the two output weka files
     * @param features a list of String features to write
     * @param stressValue the stress value associated with the features
     */
    public void writeCalculatedFeaturesOnOutputFiles(ArrayList<String> features, 
            Integer stressValue) {
        
        String outputString = "";
        for (String feature: features) {
            outputString += feature + ",";
        }
        
        try {
            outputFileWriterDifficult.write(outputString + stressValue);
            outputFileWriterDifficult.newLine();
            outputFileWriterDifficult.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing calculated features for"
                    + " difficult file");
            exc.printStackTrace();
        }
        
        try {
            if (stressValue == 1 || stressValue == 2) {
                outputFileWriterEasy.write(outputString + "1");
            }
            else if (stressValue == 3) {
                outputFileWriterEasy.write(outputString + "2");
            } 
            else if (stressValue == 4 || stressValue == 5) {
                outputFileWriterEasy.write(outputString + "3");
            }
            outputFileWriterEasy.newLine();
            outputFileWriterEasy.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in writing calculated features for"
                    + " easy file");
            exc.printStackTrace();
        }
    }
   
    /**
     * Closes the output files
     */
    public void closeFiles() {
        
        try {
            outputFileWriterDifficult.flush(); outputFileWriterDifficult.close();
            outputFileWriterEasy.flush();outputFileWriterEasy.close();
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
    public ArrayList<File> getOutputFiles() {
        
        ArrayList<File> files = new ArrayList<File>();
        files.add(outputFileEasy); files.add(outputFileDifficult);
         
       return files;
    }
}
