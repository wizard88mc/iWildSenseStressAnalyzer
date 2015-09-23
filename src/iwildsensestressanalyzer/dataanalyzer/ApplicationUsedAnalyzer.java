/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * Analyze data 
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class ApplicationUsedAnalyzer extends EventsAnalyzer {
    
    private static final ArrayList<String> allAppCategories = new ArrayList<String>();
    
    public static void analyzeDataOfApplicationUsedForEachParticipant(ArrayList<Participant> participants, 
            boolean easyJob) {
        
        for (Participant participant: participants) {
            
            SurveyDataWrapper[] wrappers;
            if (!easyJob) {
                wrappers = participant.getSurveyDataWrappers();
            }
            else {
                wrappers = participant.getEasySurveyDataWrappers();
            }
            
            workWithAppCategoryFrequency(wrappers, easyJob);
            
        }
    }
    
    private static void workWithAppCategoryFrequency(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        for (String appCategory: allAppCategories) {
            
            ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
                
            for (SurveyDataWrapper wrapper: wrappers) {
                listValues.add(wrapper.getApplicationUsedFeaturesExtractor().calculateInfluenceOfAppCategory(appCategory));
            }    
                
                
                
        }
        
    }
    
    /**
     * If not already inserted, adds a new app category to the list of all the 
     * categories found
     * @param appCategory the new app category 
     */
    public static void addCategory(String appCategory) {
        
        if (!allAppCategories.contains(appCategory)) {
            allAppCategories.add(appCategory);
        }
    }
    
}
