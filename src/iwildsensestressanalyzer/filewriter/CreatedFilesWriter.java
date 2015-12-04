package iwildsensestressanalyzer.filewriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * File Writer to write the 
 * @author Matteo Ciman
 */
public class CreatedFilesWriter extends OutputFileWriter {
    
    private static final String OUTPUT_FILE = BASE_OUTPUT_FOLDER + 
            "Classification" + File.separator + "WekaFileCreated.txt";
    
    private File outputFile = null;
    private BufferedWriter outputFileWriter = null;
    
    public CreatedFilesWriter() {
        
        try {
            outputFile = new File(OUTPUT_FILE);
            outputFile.getParentFile().mkdirs();
            
            outputFileWriter = new BufferedWriter(new FileWriter(outputFile));
        }
        catch(IOException exc) {
            System.out.println("IOException in creating CretedFilesWriter()");
        }
    }
    
    /**
     * Writes the list of created files on the output file
     * @param files the list of files
     */
    public void writeCreatedFiles(ArrayList<File> files) {
        
        try {
            for (File file: files) {
                outputFileWriter.write(file.getPath());
                outputFileWriter.newLine();
            }
            
            outputFileWriter.flush();
        }
        catch(IOException exc) {
            System.out.println("IOException in CreatedFilesWriter.writeCreatedFiles");
        }
    }
    
    /**
     * Closes the output file
     */
    public void closeFile() {
        try {
            outputFileWriter.flush();
            outputFileWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in CreatedFilesWriter.closeFile");
        }
    }
}
