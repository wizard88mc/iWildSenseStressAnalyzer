/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class WekaFilesCreatedReader extends BasicFileReader {
    
    private static final String FILE = FOLDER_DATA + File.separator + "output"
            + File.separator + "classification" + File.separator + "CreatedWekaFiles.txt";
    
    /**
     * Returns the list of the file names created for the classification task
     * @return a list of file names
     */
    public static ArrayList<String> getCreatedWekaFiles() {
        
        ArrayList<String> files = new ArrayList<>();
        BufferedReader reader;
        
        try {
            String line;
            
            reader = new BufferedReader(new FileReader(new File(FILE)));
            
            while ((line = reader.readLine()) != null) {
                files.add(line);
            }
            
        }
        catch(IOException exc) {
            System.out.println("IOException in reading list of files ");
        }
        
        return files;
    }
}
