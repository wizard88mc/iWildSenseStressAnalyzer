package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.applicationsused.ApplicationUsedFeaturesExtractor;
import iwildsensestressanalyzer.applicationsused.ApplicationUsedFeaturesExtractorListWrapper;
import iwildsensestressanalyzer.dataanalyzer.ApplicationUsedAnalyzer;
import iwildsensestressanalyzer.esm.StressSurvey;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class WekaFeaturesForApplicationUsed extends WekaFeaturesCreator {
    
    /**
     * CALCULATED FEATURES
     * For each application category
     * - Influence
     *    - average
     *    - standard deviation
     * - Timing Influence
     *    - average
     *    - standard deviation
     * 
     * For each application type
     * - Influence
     *     - average
     *     - standard deviation
     * - Timing influence
     *     - average
     *     - standard deviation
     */
    public static String[] FEATURES_NAMES;
    
    /**
     * Creates a list of features for the UserActivity events
     * @param survey the survey to consider
     * @return a list of features for the UserActivity events
     */
    public static ArrayList<Double> getFeaturesForUserActivity(StressSurvey survey) {
        
        ArrayList<Double> features = new ArrayList<>();
        
        for (String category: ApplicationUsedAnalyzer.getAllAppCategories()) {
            
            ArrayList<ApplicationUsedFeaturesExtractor> fExtractor = 
                    new ArrayList<>();
            fExtractor.add(survey.getApplicationUsedFeaturesExtractor());
            
            ApplicationUsedFeaturesExtractorListWrapper featuresExtractor = 
                    new ApplicationUsedFeaturesExtractorListWrapper(fExtractor);
            
            Double[] statisticsInfluenceOfAppCategory = 
                        featuresExtractor.calculateStatisticsInfluenceOfAppCategory(category),
                    statisticsTimingInfluenceOfAppCategory = 
                        featuresExtractor.calculateStatisticsOfTimingInfluenceOfAppCategory(category);
            
            addCalculatedFeatures(features, statisticsInfluenceOfAppCategory);
            addCalculatedFeatures(features, statisticsTimingInfluenceOfAppCategory);
        }
        
        /**
         * Calculating features for all Application Types
         */
        for (String type: ApplicationUsedAnalyzer.getAllAppTypes()) {
            
            ArrayList<ApplicationUsedFeaturesExtractor> fExtractor = 
                    new ArrayList<>();
            fExtractor.add(survey.getApplicationUsedFeaturesExtractor());
            
            ApplicationUsedFeaturesExtractorListWrapper featuresExtractor = 
                    new ApplicationUsedFeaturesExtractorListWrapper(fExtractor);
            
            Double[] statisticsInfluenceOfAppType = 
                        featuresExtractor.calculateStatisticsInfluenceOfAppType(type),
                    statisticsTimingInfluenceOfAppType = 
                        featuresExtractor.calculateStatisticsOfTimingInfluenceOfAppType(type);
            
            addCalculatedFeatures(features, statisticsInfluenceOfAppType);
            addCalculatedFeatures(features, statisticsTimingInfluenceOfAppType);
        }
        
        return features;
    }
    
    /**
     * Creates the featuresName array
     */
    public static void createFeaturesName() {
        
        ArrayList<String> names = new ArrayList<>();
        
        for (String appCategory: ApplicationUsedAnalyzer.getAllAppCategories()) {
            names.add("ApplicationsUsed_AVG_" + appCategory.replaceAll(" ", "") 
                    + "Influence");
            names.add("ApplicationsUsed_STD_" + appCategory.replaceAll(" ", "") 
                    + "Influence");
            
            names.add("ApplicationsUsed_AVG_" + appCategory.replaceAll(" ", "") 
                    + "TimingInfluence");
            names.add("ApplicationsUsed_STD_" + appCategory.replaceAll(" ", "") 
                    + "TimingInfluence");
        }
        
        for (String appType: ApplicationUsedAnalyzer.getAllAppTypes()) {
            names.add("ApplicationsUsed_AVG_" + appType.replaceAll(" ", "") + 
                    "Influence");
            names.add("ApplicationsUsed_STD_" + appType.replaceAll(" ", "") + 
                    "Influence");
            
            names.add("ApplicationsUsed_AVG_" + appType.replaceAll(" ", "") + 
                    "TimingInfluence");
            names.add("ApplicationsUsed_STD_" + appType.replaceAll(" ", "") + 
                    "TimingInfluence");
        }
        
        FEATURES_NAMES = new String[names.size()];
        FEATURES_NAMES = names.toArray(FEATURES_NAMES);
    }
}
