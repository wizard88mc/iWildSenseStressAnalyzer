/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.filewriter.ARFFWekaWriter;
import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 * Works with the classification tasks, creating ARFF files and performing 
 * classification task
 * @author Matteo Ciman
 */
public class WekaAnalyzer {
    
    public static void workWithClassificationProblem(ArrayList<Participant> 
            participants) {
        
        for (Participant participant: participants) {
            
            ARFFWekaWriter output = new ARFFWekaWriter(participant.getIMEI(), 
                    false);
            
        }
        
    }
    
}
