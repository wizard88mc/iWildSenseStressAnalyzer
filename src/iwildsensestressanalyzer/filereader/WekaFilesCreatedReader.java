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
    
    /**
     * Returns the list of the file names created for the classification task
     * @param folderName the name of the folder where to find files
     * @return a list of file names
     */
    public static ArrayList<String> getCreatedWekaFiles(String folderName) {
        
        String FILE = FOLDER_DATA + File.separator + "output" + File.separator + 
                folderName + File.separator + "classification" + File.separator 
                + "CreatedWekaFiles.txt";
        
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
