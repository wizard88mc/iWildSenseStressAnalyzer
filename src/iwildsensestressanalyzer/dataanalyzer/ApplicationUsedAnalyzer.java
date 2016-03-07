package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Analyze data of the ApplicationUsed
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class ApplicationUsedAnalyzer extends EventsAnalyzer {
    
    public static final Boolean DEBUG_APP_CATEGORY = false;
    
    private static final ArrayList<String> ALL_APP_CATEGORIES = new ArrayList<>();
    
    private static final ArrayList<String> ALL_APP_TYPE = new ArrayList<>();
    
    public static void analyzeDataOfApplicationUsedForEachParticipant(
            ArrayList<Participant> participants, boolean easyJob, 
            boolean useAllTogether) {
        
        printTitleMessage("*** ANALYZING APPLICATIONS USED FEATURES ***");
        
        if (!useAllTogether) {
            
            for (String appCategory: ALL_APP_CATEGORIES) {
                
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
            
            for (String appCategory: ALL_APP_CATEGORIES) {
                
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

        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleValues = new ArrayList<>();

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
     * @param appCategory the app category
     * @param easyJob true if we are doing the easy test, false otherwise
     */
    private static ArrayList<Boolean> workWithAppCategoryTimingInfluence(
            SurveyDataWrapper[] wrappers, String appCategory, boolean easyJob) {
            
        printTitleMessage("*** Timing influence of " + appCategory +
                " application category ***");
            
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<>();
            
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
     * Works with App Type timing influence over all the timing to perform
     * the ttest and calculate the p-value
     * @param wrappers all the surveys and the related data to use
     * @param appType the app type
     * @param easyJob true if we are doing the easy test, false otherwise
     * @return 
     */
    public static ArrayList<Boolean> workWithAppTypeFrequency(
            SurveyDataWrapper[] wrappers, String appType, Boolean easyJob) {
        
        printTitleMessage("*** Influence of " + appType + " application" + 
                " type ***");
            
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
                
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                    .getAllInfluenceOfAppType(appType));
        }
                
        return printTTestResults(listValues, easyJob);
    }
    
    public static HashMap<String, Double> analyzeApplicationsUsage(ArrayList<Participant> participants) {
        
        HashMap<String, Double> categoriesPercentageOfParticipants = new HashMap<>();
        
        for (String category: ALL_APP_CATEGORIES) {
            
            Double numberOfTimes = 0.0;
            
            for (Participant participant: participants) {
                
                if (participant.hasUsedTheApplicationCategory(category)) {
                    
                    numberOfTimes += 1.0;
                }
            }
            
            numberOfTimes /= (double) participants.size();
            
            categoriesPercentageOfParticipants.put(category, numberOfTimes);
        }
        
        return categoriesPercentageOfParticipants;
    }
    
    /**
     * If not already inserted, adds a new app category to the list of all the 
     * categories found
     * @param appCategory the new app category 
     */
    public static void addCategory(String appCategory) {
        
        if (!ALL_APP_CATEGORIES.contains(appCategory)) {
            if (DEBUG_APP_CATEGORY) {
                System.out.println(appCategory);
            }
            ALL_APP_CATEGORIES.add(appCategory);
        }
    }
    
    /**
     * Returns the list of categories of the apps
     * @return a list of different app categories
     */
    public static ArrayList<String> getAllAppCategories() {
        return ALL_APP_CATEGORIES;
    }
    
    /**
     * If not already present, adds a new App type to the list of all the
     * possible type of applications (App or Games)
     * @param type the new application type
     */
    public static void addAppType(String type) {
        
        if (!ALL_APP_TYPE.contains(type)) {
            ALL_APP_TYPE.add(type);
        }
    }
    
    /**
     * Returns the list of application types
     * @return a list of different app types
     */
    public static ArrayList<String> getAllAppTypes() {
        return ALL_APP_TYPE;
    }
    
}
