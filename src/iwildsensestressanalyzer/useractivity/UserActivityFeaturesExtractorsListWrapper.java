package iwildsensestressanalyzer.useractivity;

import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserActivityFeaturesExtractorsListWrapper {
    
    private final ArrayList<UserActivityFeaturesExtractor> userActivityFeaturesExtractorList;
    
    public UserActivityFeaturesExtractorsListWrapper(ArrayList<UserActivityFeaturesExtractor> 
            userActivityFeaturesExtractorList) {
        
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
     * Calculates statistics information about the Points sum of activities
     * @return [average, variance, standard_deviation] if more than one value, 
     * null otherwise
     */
    public Double[] calculateStatisticsPointsSumOfActivities() {
        
        ArrayList<Double> listValues = getAllPointsSumOfActivities();
        if (listValues != null && !listValues.isEmpty()) {
            return MathUtils.calculateStatisticInformation(listValues);
        }
        else {
            return null;
        }   
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
     * Calculates statistics information about the percentage of workload
     * @return [average, variance,  standard_deviation] if more than zero values, 
     * null otherwise
     */
    public Double[] calculateStatisticsPercentageOfWorkload() {
        
        ArrayList<Double> values = getAllPercentageOfWorkload();
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
        
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
     * Calculates statistics information about the influence of walking 
     * activity on the total
     * @return [average, variance, standard_deviation] if more than zero values, 
     * null otherwise
     */
    public Double[] calculateStatisticsInfluenceOfWalkingActivityOnTotal() {
        
        ArrayList<Double> values = getAllInfluenceOfWalkingActivityOnTotal();
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
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
     * Calculates statistics information about the influence of running 
     * activity on the total activity performed
     * @return [average, variance, standard_deviation] if more than zero values,
     * null otherwise
     */
    public Double[] calculateStatisticsInfluenceOfRunningActivityOnTotal() {
        
        ArrayList<Double> values = getAllInfluenceOfRunningActivityOnTotal();
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
        
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
     * Calculates statistics information about the influence of bycicle activity
     * on the total activity performed
     * @return [average, variance, standard_deviation] if more than zero values, 
     * null otherwise
     */
    public Double[] calculateStatisticsInfluenceOfBicycleActivityOnTotal() {
        
        ArrayList<Double> values = getAllInfluenceOfOnBicycleActivityOnTotal();
       if (values != null && !values.isEmpty()) {
           return MathUtils.calculateStatisticInformation(values);
       }
       else {
           return null;
       }
        
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
     * Calculates statistics information about the percentage of titlting events
     * on the recognized activities
     * @return [average, variance, standard_deviation] if more than zero values,
     * null otherwise
     */
    public Double[] calculateStatisticsPercentageOfTiltingEvents() {
        
        ArrayList<Double> values = getAllPercentagesOfTiltingEvents();
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
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
     * Calculates statistics information about the percentage of in_vehicle 
     * events on the recognized activities
     * @return [average, variance, standard_deviation] if more than zero values, 
     * null otherwise
     */
    public Double[] calculateStatisticsPercentageOfInVehicleEvents() {
        
        ArrayList<Double> values = getAllPercentagesOfInVehicleEvents();
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns the UserActivityEvent list stored by the wrapper
     * @return the ArrayList of UserActivityEvent
     */
    public ArrayList<UserActivityFeaturesExtractor> getUserFeaturesExtractorList() {
        return this.userActivityFeaturesExtractorList;
    }
    
}
