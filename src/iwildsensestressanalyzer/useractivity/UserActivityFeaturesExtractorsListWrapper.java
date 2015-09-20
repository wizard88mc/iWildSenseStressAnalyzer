package iwildsensestressanalyzer.useractivity;

import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserActivityFeaturesExtractorsListWrapper {
    
    private final ArrayList<UserActivityFeaturesExtractor> userActivityFeaturesExtractorList;
    
    public UserActivityFeaturesExtractorsListWrapper(ArrayList<UserActivityFeaturesExtractor> userActivityFeaturesExtractorList) {
        
        this.userActivityFeaturesExtractorList = userActivityFeaturesExtractorList;
    }
    
    /**
     * Creates a list with all the Points Sum of Activities from all the
     * features extractor
     * @return a list of Points sum of activities
     */
    public ArrayList<Double> getAllPointsSumOfActivities() {
        
        ArrayList<Double> listValues = new ArrayList<Double>();
        
        for (UserActivityFeaturesExtractor featureExtractor: userActivityFeaturesExtractorList) {
            listValues.add(featureExtractor.calculatePointsSumOfActivities());
        }
        
        return listValues;
    }
    
    /**
     * Creates a list with all the Percentages of workload from all the 
     * features extractor
     * @return a list of percentages of workload
     */
    public ArrayList<Double> getAllPercentageOfWorkload() {
        
        ArrayList<Double> listValues = new ArrayList<Double>();
        
        for (UserActivityFeaturesExtractor featuresExtractor: userActivityFeaturesExtractorList) {
            listValues.add(featuresExtractor.calculatePercentageOfWorkload());
        }
        
        return listValues;
    }
    
    /**
     * Creates a list with all the influences of the walking activity on the
     * total activity performed
     * @return a list of influences of the walking activity on the total
     */
    public ArrayList<Double> getAllInfluenceOfWalkingActivityOnTotal() {
        
        ArrayList<Double> listValues = new ArrayList<Double>();
        
        for (UserActivityFeaturesExtractor featuresExtractor: userActivityFeaturesExtractorList) {
            listValues.add(featuresExtractor.calculateInfluenceOfWalkingActivityOnTotal());
        }
        
        return listValues;
    }
    
    /**
     * Creates a list with all the influences of the running activity on the
     * total activity performed
     * @return a list of influences of the running activity on the total
     */
    public ArrayList<Double> getAllInfluenceOfRunningActivityOnTotal() {
        
        ArrayList<Double> listValues = new ArrayList<Double>();
        
        for (UserActivityFeaturesExtractor featuresExtractor: userActivityFeaturesExtractorList) {
            listValues.add(featuresExtractor.calculateInfluenceOfRunningActivityOnTotal());
        }
        
        return listValues;
    }
    
    /**
     * Creates a list with all the influences of the bicycle activity on the 
     * total activity performed
     * @return a list of influences of the bicycle activity on the total
     */
    public ArrayList<Double> getAllInfluenceOfOnBicycleActivityOnTotal() {
        
        ArrayList<Double> listValues = new ArrayList<Double>();
        
        for (UserActivityFeaturesExtractor featuresExtractor: userActivityFeaturesExtractorList) {
            listValues.add(featuresExtractor.calculateInfluenceOfOnBicycleActivityOnTotal());
        }
        
        return listValues;
    }
    
    /**
     * Creates a list with all the percentages of TILTING events on the
     * recognized activities
     * @return 
     */
    public ArrayList<Double> getAllPercentagesOfTiltingEvents() {
        
        ArrayList<Double> listValues = new ArrayList<Double>();
        
        for (UserActivityFeaturesExtractor featuresExtractor: userActivityFeaturesExtractorList) {
            listValues.add(featuresExtractor.calculatePercentageOfTiltingEvents());
        }
        
        return listValues;
    }
    
    /**
     * Creates a list with all the percentages of IN_VEHICLE events on the 
     * recognized activities
     * @return 
     */
    public ArrayList<Double> getAllPercentagesOfInVehicleEvents() {
        
        ArrayList<Double> listValues = new ArrayList<Double>();
        
        for (UserActivityFeaturesExtractor featuresExtractor: userActivityFeaturesExtractorList) {
            listValues.add(featuresExtractor.calculatePercentageOfInVehicleEvents());
        }
        
        return listValues;
    }
    
    /**
     * Returns the UserActivityEvent list stored by the wrapper
     * @return the ArrayList of UserActivityEvent
     */
    public ArrayList<UserActivityFeaturesExtractor> getUserFeaturesExtractorList() {
        return this.userActivityFeaturesExtractorList;
    }
    
}
