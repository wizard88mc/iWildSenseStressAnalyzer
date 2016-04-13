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
        
        ArrayList<String> labels = EventsAnalyzer.labels5LabelsTaks;
        if (easyJob) {
            labels = EventsAnalyzer.labels3LabelsTask;
        }
        
        printTitleMessage("*** ANALYZING APPLICATIONS USED FEATURES ***");
        
        if (!useAllTogether) {
            
            for (String appCategory: ALL_APP_CATEGORIES) {
                
                ArrayList<Integer> tTestPassedForAppCategoryFrequency = 
                    new ArrayList<>(),
                    tTestPassedForAppCategoryTimingInfluence = new ArrayList<>();

                ArrayList<Integer> validTTestsForAppCategoryFrequency = 
                    new ArrayList<>(), 
                    validTTestsForAppCategoryTimingInfluence = new ArrayList<>();
            
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
                                    labels);
                    addTTestResultsToFinalContainer(tTestPassedForAppCategoryFrequency, 
                            results, validTTestsForAppCategoryFrequency);
                    
                    results = workWithAppCategoryTimingInfluence(wrappers, 
                            appCategory, labels);
                    addTTestResultsToFinalContainer(tTestPassedForAppCategoryTimingInfluence, 
                            results, validTTestsForAppCategoryTimingInfluence);
                }
                
                /**
                 * Analyzing percentage of success of a particular application
                 * category
                 */
                performStepsForPrintingPercentageOfSuccess(tTestPassedForAppCategoryFrequency, 
                       validTTestsForAppCategoryFrequency, labels, 
                       "*** Percentage of success of influence of " + 
                               appCategory + " application category ***");

                /**
                 * Analyzing percentage of success of the timing influence 
                 * of a particular application category
                 */
                performStepsForPrintingPercentageOfSuccess(tTestPassedForAppCategoryTimingInfluence, 
                       validTTestsForAppCategoryTimingInfluence, labels, 
                       "*** Percentage of success of timing influence of " + 
                               appCategory + " application category ***");
            }
            /**
             * Analyzing features for application types
             */
            for (String appType: ALL_APP_TYPE) {
                
                ArrayList<Integer> tTestPassedForAppTypeFrequency = 
                                new ArrayList<>(),
                    tTestPassedForAppTypeTimingInfluence = new ArrayList<>();

                ArrayList<Integer> validTTestsForAppTypeFrequency = 
                        new ArrayList<>(), 
                    validTTestsForAppTypeTimingInfluence = new ArrayList<>();
                
                for (Participant participant: participants) {

                    SurveyDataWrapper[] wrappers;
                    if (!easyJob) {
                        wrappers = participant.getSurveyDataWrappers();
                    }
                    else {
                        wrappers = participant.getEasySurveyDataWrappers();
                    }
                    
                    ArrayList<Boolean> results = 
                            workWithAppTypeFrequency(wrappers, appType, 
                                    labels);
                    addTTestResultsToFinalContainer(tTestPassedForAppTypeFrequency, 
                            results, validTTestsForAppTypeFrequency);
                    
                    results = workWithAppTypeTimingInfluence(wrappers, 
                            appType, labels);
                    addTTestResultsToFinalContainer(tTestPassedForAppTypeTimingInfluence, 
                            results, validTTestsForAppTypeTimingInfluence);
                }
                
                /**
                 * Analyzing percentage of success of a particular application
                 * category
                 */
                performStepsForPrintingPercentageOfSuccess(tTestPassedForAppTypeFrequency, 
                    validTTestsForAppTypeFrequency, labels, 
                    "*** Percentage of success of influence of " + 
                        appType + " application type ***");

                /**
                 * Analyzing percentage of success of the timing influence 
                 * of a particular application category
                 */
                performStepsForPrintingPercentageOfSuccess(tTestPassedForAppTypeTimingInfluence, 
                    validTTestsForAppTypeTimingInfluence, labels, 
                    "*** Percentage of success of timing influence of " + 
                        appType + " application type ***");
            }
        }
        else {
            
            ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            for (String appCategory: ALL_APP_CATEGORIES) {
                
                workWithAppCategoryFrequencyOfAllParticipants(allSurveyDataWrappers, 
                        appCategory, labels);
                workWithAppCategoryTimingOfAllParticipants(allSurveyDataWrappers, 
                        appCategory, labels);
            }
            
            for (String appType: ALL_APP_TYPE) {
                
                workWithAppTypeFrequencyOfAllParticipants(allSurveyDataWrappers,
                       appType, labels);
                workWithAppTypeTimingOfAllParticipants(allSurveyDataWrappers, 
                        appType, labels);
            }
        }
    }
    
    /**
     * Works with App Category frequency to calculate p-value using ttest 
     * between all the answers of all the surveys
     * @param wrappers all the surveys and the related data to use
     * @param appCategory the category of the app
     * @param labels the labels for the output table
     */
    private static ArrayList<Boolean> workWithAppCategoryFrequency(SurveyDataWrapper[] wrappers, 
            String appCategory, ArrayList<String> labels) {
        
        printTitleMessage("*** Influence of " + appCategory + " application" + 
                " category ***");
            
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
                
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                    .getAllInfluenceOfAppCategory(appCategory));
        }
                
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with App category frequency using data from all the participant
     * @param listWrappers
     * @param labels the list of labels for the output table 
     */
    private static ArrayList<Boolean> workWithAppCategoryFrequencyOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, String appCategory, 
            ArrayList<String> labels) {
            
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

        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with App Category frequency to calculate p-value using ttest 
     * between all the answers of all the surveys
     * @param wrappers all the surveys and the related data to use
     * @param appCategory the category of the app
     * @param labels the labels for the output table
     */
    private static ArrayList<Boolean> workWithAppTypeFrequency(SurveyDataWrapper[] 
            wrappers, String appType, ArrayList<String> labels) {
        
        printTitleMessage("*** Influence of " + appType + " application" + 
                " type ***");
            
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
                
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                    .getAllInfluenceOfAppType(appType));
        }
                
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the application type frequency for all the participants
     * @param listWrappers
     * @param appType
     * @param labels a list of labels for the output table 
     */
    private static void workWithAppTypeFrequencyOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, String appType, 
            ArrayList<String> labels) {
            
        printTitleMessage("*** GLOBAL ANALYSIS: Influence of " + appType 
            + " application type ***");

        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleValues = new ArrayList<>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                        .calculateStatisticsInfluenceOfAppType(appType);

                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }

            listValues.add(singleValues);
        }

        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with App Category timing influence over all the timing to perform
     * the ttest and calculate the p-value
     * @param wrappers all the surveys and the related data to use
     * @param appCategory the app category
     * @param labels list of labels for the output folder
     */
    private static ArrayList<Boolean> workWithAppCategoryTimingInfluence(
            SurveyDataWrapper[] wrappers, String appCategory, ArrayList<String> labels) {
            
        printTitleMessage("*** Timing influence of " + appCategory +
                " application category ***");
            
        ArrayList<ArrayList<Double>> listValues = 
                new ArrayList<>();
            
        for (SurveyDataWrapper wrapper: wrappers) {
            
            listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                    .getAllTimingInfluenceOfAppCategory(appCategory));
        }
            
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Timing influence of application category analysis for all participants
     * @param listWrappers
     * @param appCategory the application category
     * @param labels list of labels for the output table
     * @return 
     */
    private static void workWithAppCategoryTimingOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, String appCategory, 
            ArrayList<String> labels) {
                    
        printTitleMessage("*** GLOBAL ANALYSIS: Timing influence of " + 
                appCategory + " application category ***");
            
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleValues = new ArrayList<>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                        .calculateStatisticsOfTimingInfluenceOfAppCategory(appCategory);

                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }

            listValues.add(singleValues);
        }
            
        printTTestResults(listValues, labels);   
    }
    
    /**
     * Works with App Type timing influence over all the timing to perform
     * the ttest and calculate the p-value
     * @param wrappers all the surveys and the related data to use
     * @param appType the app type
     * @param labels list of labels for the output table
     * @return 
     */
    private static ArrayList<Boolean> workWithAppTypeTimingInfluence(
            SurveyDataWrapper[] wrappers, String appType, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** Timing Influence of " + appType + " application" + 
                " type ***");
            
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
                
        for (SurveyDataWrapper wrapper: wrappers) {
            listValues.add(wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                    .getAllTimingInfluenceOfAppType(appType));
        }
                
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with application type timing influence for all the participants
     * @param listWrappers
     * @param appType
     * @param labels list of labels for the output folder
     */
    private static void workWithAppTypeTimingOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, String appType, 
            ArrayList<String> labels) {
                    
        printTitleMessage("*** GLOBAL ANALYSIS: Timing influence of " + 
                appType + " application type ***");
            
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();

        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {

            ArrayList<Double> singleValues = new ArrayList<>();

            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getApplicationUsedFeaturesExtractorListWrapper()
                        .calculateStatisticsOfTimingInfluenceOfAppType(appType);

                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }

            listValues.add(singleValues);
        }
            
        printTTestResults(listValues, labels);   
    }
    
    /**
     * Calculates the number of participants that used a particular application
     * category
     * @param participants the list of participants
     * @return Application category => percentage of usage
     */
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
        
        for (String type: ALL_APP_TYPE) {
            
            Double numberOfTimes = 0.0;
            
            for (Participant participant: participants) {
                
                if (participant.hasUsedTheApplicationType(type)) {
                    
                    numberOfTimes += 1.0;
                }
            }
            
            numberOfTimes /= (double) participants.size();
            
            categoriesPercentageOfParticipants.put(type, numberOfTimes);
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
