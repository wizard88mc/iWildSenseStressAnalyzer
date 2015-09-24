/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.IWildSenseStressAnalyzer;
import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * Analyze data of the ApplicationUsed
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
            workWithAppCategoryTimingInfluence(wrappers, easyJob);
        }
    }
    
    /**
     * Works with App Category frequency to calculate p-value using ttest 
     * between all the answers of all the surveys
     * @param wrappers all the surveys and the related data to use
     * @param easyJob true if we are doing the easy test, false otherwise
     */
    private static void workWithAppCategoryFrequency(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        for (String appCategory: allAppCategories) {
            
            System.out.println();
            System.out.println("*** Influence of " + appCategory + " application category ***");
            
            ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
                
            for (SurveyDataWrapper wrapper: wrappers) {
                listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper().getAllInfluenceOfAppCategory(appCategory));
            }    
            
            ArrayList<ArrayList<Double>> normalizedValues = 
                    MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
                
            printTTestResults(normalizedValues, easyJob);
        }
    }
    
    /**
     * Works with App Category timing influence over all the timing to perform
     * the ttest and calculate the p-value
     * @param wrappers all the surveys and the related data to use
     * @param easyJob true if we are doing the easy test, false otherwise
     */
    private static void workWithAppCategoryTimingInfluence(SurveyDataWrapper[] wrappers, 
            boolean easyJob) {
        
        for (String appCategory: allAppCategories) {
            
            System.out.println();
            System.out.println("*** Timing influence of " + appCategory +
                    " application category ***");
            
            ArrayList<ArrayList<Double>> listValues = 
                    new ArrayList<ArrayList<Double>>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                        .getAllTimingInfluenceOfAppCategory(appCategory));
            }
            
            ArrayList<ArrayList<Double>> normalizedValues = 
                    MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
            
            printTTestResults(normalizedValues, easyJob);
        }
        
    } 
    
    /**
     * If not already inserted, adds a new app category to the list of all the 
     * categories found
     * @param appCategory the new app category 
     */
    public static void addCategory(String appCategory) {
        
        if (!allAppCategories.contains(appCategory)) {
            if (IWildSenseStressAnalyzer.DEBUG) {
                System.out.println(appCategory);
            }
            allAppCategories.add(appCategory);
        }
    }
    
}
