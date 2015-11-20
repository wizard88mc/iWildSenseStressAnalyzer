/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.filewriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Matteo
 */
public class ARFFWekaWriter extends OutputWriter {
    
    private static final String outputFolder = "data" + File.separator + 
            "output" + File.separator + "classification" + File.separator;
    private static final String outpufFileName = "StressClassification";
    private File outputFile = null;
    private BufferedWriter outputFileWriter = null;
    
    private final boolean easyTask;
    
    public ARFFWekaWriter(String participantIMEI, 
            boolean easyTask) {
        
        this.easyTask = easyTask;
        
        String finalOutputFile = outputFolder + participantIMEI + File.separator
                + outpufFileName + participantIMEI;
        
        if (easyTask) {
            finalOutputFile += "EASY.csv";
        }
        else {
            finalOutputFile += "DIFFICULT.csv";
        }
        
        try {
            outputFile = new File(finalOutputFile);
            outputFile.getParentFile().mkdirs();
            
            outputFileWriter = new BufferedWriter(new FileWriter(outputFile));
            
            writePreambleWekaFile();
        }
        catch(IOException exc) {
            System.out.println("IOException in creting WekaOutputFile");
            exc.printStackTrace();
            outputFileWriter = null;
        }
    }
    
    private void writePreambleWekaFile() {
        
        if (outputFileWriter != null) {
            
            try {
                outputFileWriter.write("@RELATION stress"); 
                outputFileWriter.newLine();

                if (easyTask) {
                    outputFileWriter.write("@ATTRIBUTE class {1,2,3}");
                }
                else {
                    outputFileWriter.write("@ATTRIBUTE class {1,2,3,4,5}");
                }
                outputFileWriter.newLine();
                outputFileWriter.write("@DATA");
                outputFileWriter.newLine();
                
                outputFileWriter.flush();
            }
            catch(IOException exc) {
                System.out.println("Exception in writing preamble weka file");
                exc.printStackTrace();
            }
        }
        
    }
}
