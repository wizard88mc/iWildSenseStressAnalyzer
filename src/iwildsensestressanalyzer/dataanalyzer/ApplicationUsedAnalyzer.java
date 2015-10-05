package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.IWildSenseStressAnalyzer;
import iwildsensestressanalyzer.participant.Participant;
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
    
    public static void analyzeDataOfApplicationUsedForEachParticipant(
            ArrayList<Participant> participants, boolean easyJob, 
            boolean useAllTogether) {
        
        if (!useAllTogether) {
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
        else {
            
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithAppCategoryFrequencyOfAllParticipants(allSurveyDataWrappers, easyJob);
            workWithAppCategoryTimingOfAllParticipants(allSurveyDataWrappers, easyJob);
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
            
            printTitleMessage("*** Influence of " + appCategory + " application" + 
                    " category ***");
            
            ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
                
            for (SurveyDataWrapper wrapper: wrappers) {
                listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                        .getAllInfluenceOfAppCategory(appCategory));
            }
                
            printTTestResults(listValues, easyJob);
        }
    }
    
    /**
     * Works with App category frequency using data from all the participant
     * @param listWrappers
     * @param easyJob 
     */
    private static void workWithAppCategoryFrequencyOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        for (String appCategory: allAppCategories) {
            
            printTitleMessage("*** GLOBAL ANALYSIS: Influence of " + appCategory 
                + " application category ***");
            
            ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();

            for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

                ArrayList<Double> singleValues = new ArrayList<Double>();

                for (SurveyDataWrapper wrapper: wrappers) {
                    Double[] values = wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                            .calculateStatisticsInfluenceOfAppCategory(appCategory);

                    if (values != null && values[0] != -1) {
                        singleValues.add(values[0]);
                    }
                }

                listValues.add(singleValues);
            }
            
            printTTestResults(listValues, easyJob);
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
            
            printTitleMessage("*** Timing influence of " + appCategory +
                    " application category ***");
            
            ArrayList<ArrayList<Double>> listValues = 
                    new ArrayList<ArrayList<Double>>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                        .getAllTimingInfluenceOfAppCategory(appCategory));
            }
            
            printTTestResults(listValues, easyJob);
        }
    }
    
    /**
     * Works with App category timing influence using data from all the participant
     * @param listWrappers
     * @param easyJob 
     */
    private static void workWithAppCategoryTimingOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        for (String appCategory: allAppCategories) {
            
            printTitleMessage("*** GLOBAL ANALYSIS: Timing influence of " + 
                    appCategory + " application category ***");
            
            ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();

            for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

                ArrayList<Double> singleValues = new ArrayList<Double>();

                for (SurveyDataWrapper wrapper: wrappers) {
                    Double[] values = wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                            .calculateStatisticsOfTimingInfluenceOfAppCategory(appCategory);

                    if (values != null && values[0] != -1) {
                        singleValues.add(values[0]);
                    }
                }

                listValues.add(singleValues);
            }
            
            printTTestResults(listValues, easyJob);
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
