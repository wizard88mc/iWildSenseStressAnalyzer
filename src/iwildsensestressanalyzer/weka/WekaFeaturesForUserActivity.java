package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.useractivity.UserActivityFeaturesExtractor;
import iwildsensestressanalyzer.useractivity.UserActivityFeaturesExtractorsListWrapper;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class WekaFeaturesForUserActivity extends WekaFeaturesCreator {
    
    public static final String[] FEATURES_NAMES = {
        "UserActivity_AVG_PointsSumOfActivities", 
        "UserActivity_STD_PointsSumOfActivities", 
        "UserActivity_AVG_PercentageOfWorkload", 
        "UserActivity_STD_PercentageOfWorkload", 
        "UserActivity_AVG_InfluenceOfWalkingActivityOnTotal", 
        "UserActivity_STD_InfluenceOfWalkingActivityOnTotal", 
        "UserActivity_AVG_InfluenceOfRunningActivityOnTotal", 
        "UserActivity_STD_InfluenceOfRunningActivityOnTotal", 
        "UserActivity_AVG_InfluenceOfBicycleActivityOnTotal", 
        "UserActivity_STD_InfluenceOfBicycleActivityOnTotal", 
        "UserActivity_AVG_PercentageOfTitltingEvents", 
        "UserActivity_STD_PercentageOfTiltingEvents", 
        "UserActivity_AVG_PercentageOfInVehicleEvents", 
        "UserActivity_STD_PercentageOfInVehicleEvents"};
    
    /**
     * Creates a list of features for the UserActivity events
     * @param survey the survey to consider
     * @return a list of features for the UserActivity events
     */
    public static ArrayList<Double> getFeaturesForUserActivity(StressSurvey survey) {
        
        ArrayList<Double> features = new ArrayList<>();
        
        ArrayList<UserActivityFeaturesExtractor> fExtractor = new ArrayList<>();
        
        fExtractor.add(survey.getUserActivityFeaturesExtractor());
        
        UserActivityFeaturesExtractorsListWrapper featuresExtractor = 
                new UserActivityFeaturesExtractorsListWrapper(fExtractor);
        
        Double[] statisticsPointsSumOfActivities = 
                    featuresExtractor.calculateStatisticsPointsSumOfActivities(),
                statisticsPercentageOfWorkload = 
                    featuresExtractor.calculateStatisticsPercentageOfWorkload(),
                statisticsInfluenceOfWalkingActivityOnTotal = 
                    featuresExtractor.calculateStatisticsInfluenceOfWalkingActivityOnTotal(),
                statisticsInfluenceOfRunningActivityOnTotal = 
                    featuresExtractor.calculateStatisticsInfluenceOfRunningActivityOnTotal(),
                statisticsInfluenceOfBicycleActivityOnTotal = 
                    featuresExtractor.calculateStatisticsInfluenceOfBicycleActivityOnTotal(),
                statisticsPercentageOfTiltingEvents = 
                    featuresExtractor.calculateStatisticsPercentageOfTiltingEvents(),
                statisticsPercentageOfInVehicleEvents = 
                    featuresExtractor.calculateStatisticsPercentageOfInVehicleEvents();
        
        addCalculatedFeatures(features, statisticsPointsSumOfActivities);
        addCalculatedFeatures(features, statisticsPercentageOfWorkload);
        addCalculatedFeatures(features, statisticsInfluenceOfWalkingActivityOnTotal);
        addCalculatedFeatures(features, statisticsInfluenceOfRunningActivityOnTotal);
        addCalculatedFeatures(features, statisticsInfluenceOfBicycleActivityOnTotal);
        addCalculatedFeatures(features, statisticsPercentageOfTiltingEvents);
        addCalculatedFeatures(features, statisticsPercentageOfInVehicleEvents);
        
        return features;
    }
}
