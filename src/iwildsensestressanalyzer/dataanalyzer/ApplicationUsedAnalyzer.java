package iwildsensestressanalyzer.dataanalyzer;

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
    
    public static final Boolean DEBUG_APP_CATEGORY = false;
    
    private static final ArrayList<String> allAppCategories = new ArrayList<String>();
    
    public static void analyzeDataOfApplicationUsedForEachParticipant(
            ArrayList<Participant> participants, boolean easyJob, 
            boolean useAllTogether) {
        
        printTitleMessage("*** ANALYZING APPLICATIONS USED FEATURES ***");
        
        if (!useAllTogether) {
            
            for (String appCategory: allAppCategories) {
                
                ArrayList<Integer> tTestPassedForAppCategoryFrequency = 
                            new ArrayList<>(),
                        tTestPassedForAppCategoryTimingInfluence = 
                            new ArrayList<>();
                
                ArrayList<Integer> validTTestsForAppCategoryFrequency = 
                            new ArrayList<>(), 
                        validTTestsForAppCategoryTimingInfluence = 
                            new ArrayList<>();
            
                for (Participant participant: participants) {
                    
                    printTitleMessage("*** Participant " + participant.getIMEI() + 
                        " ***");

                    SurveyDataWrapper[] wrappers;
                    if (!easyJob) {
                        wrappers = participant.getSurveyDataWrappers();
                    }
                    else {
                        wrappers = participant.getEasySurveyDataWrappers();
                    }
                    
                    ArrayList<Boolean> results = 
                            workWithAppCategoryFrequency(wrappers, appCategory, 
                                    easyJob);
                    addTTestResultsToFinalContainer(tTestPassedForAppCategoryFrequency, 
                            results, validTTestsForAppCategoryFrequency);
                    
                    results = workWithAppCategoryTimingInfluence(wrappers, 
                            appCategory, easyJob);
                    addTTestResultsToFinalContainer(tTestPassedForAppCategoryTimingInfluence, 
                            results, validTTestsForAppCategoryTimingInfluence);
                }
                
                /**
                 * Analyzing percentage of success of a particular application
                 * category
                 */
                performStepsForPrintingPercentageOfSuccess(tTestPassedForAppCategoryFrequency, 
                        validTTestsForAppCategoryFrequency, easyJob, 
                        "*** Percentage of success of influence of " + 
                                appCategory + " application category ***");
                
                /**
                 * Analyzing percentage of success of the timing influence 
                 * of a particular application category
                 */
                performStepsForPrintingPercentageOfSuccess(tTestPassedForAppCategoryTimingInfluence, 
                        validTTestsForAppCategoryTimingInfluence, easyJob, 
                        "*** Percentage of success of timing influence of " + 
                                appCategory + " application category ***");
            }
        }
        else {
            
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            for (String appCategory: allAppCategories) {
                
                workWithAppCategoryFrequencyOfAllParticipants(allSurveyDataWrappers, 
                        appCategory, easyJob);
                workWithAppCategoryTimingOfAllParticipants(allSurveyDataWrappers, 
                        appCategory, easyJob);
            }
        }
    }
    
    /**
     * Works with App Category frequency to calculate p-value using ttest 
     * between all the answers of all the surveys
     * @param wrappers all the surveys and the related data to use
     * @param appCategory the category of the app
     * @param easyJob true if we are doing the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithAppCategoryFrequency(SurveyDataWrapper[] wrappers, 
            String appCategory, boolean easyJob) {
        
        printTitleMessage("*** Influence of " + appCategory + " application" + 
                " category ***");
            
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
                
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                    .getAllInfluenceOfAppCategory(appCategory));
        }
                
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with App category frequency using data from all the participant
     * @param listWrappers
     * @param easyJob 
     */
    private static ArrayList<Boolean> workWithAppCategoryFrequencyOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, String appCategory, 
            boolean easyJob) {
            
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

        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with App Category timing influence over all the timing to perform
     * the ttest and calculate the p-value
     * @param wrappers all the surveys and the related data to use
     * @param easyJob true if we are doing the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithAppCategoryTimingInfluence(SurveyDataWrapper[] wrappers, 
            String appCategory, boolean easyJob) {
            
        printTitleMessage("*** Timing influence of " + appCategory +
                " application category ***");
            
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<ArrayList<Double>>();
            
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                    .getAllTimingInfluenceOfAppCategory(appCategory));
        }
            
        return printTTestResults(listValues, easyJob);
        
    }
    
    /**
     * Works with App category timing influence using data from all the participant
     * @param listWrappers
     * @param easyJob 
     */
    private static ArrayList<Boolean> workWithAppCategoryTimingOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, String appCategory, 
            boolean easyJob) {
                    
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
            
        return printTTestResults(listValues, easyJob);
        
    }
    
    /**
     * If not already inserted, adds a new app category to the list of all the 
     * categories found
     * @param appCategory the new app category 
     */
    public static void addCategory(String appCategory) {
        
        if (!allAppCategories.contains(appCategory)) {
            if (DEBUG_APP_CATEGORY) {
                System.out.println(appCategory);
            }
            allAppCategories.add(appCategory);
        }
    }
    
    /**
     * Returns the list of categories of the apps
     * @return a list of different app categories
     */
    public static ArrayList<String> getAllAppCategories() {
        return allAppCategories;
    }
    
}
