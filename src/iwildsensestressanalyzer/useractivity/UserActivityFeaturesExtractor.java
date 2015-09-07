package iwildsensestressanalyzer.useractivity;

import iwildsensestressanalyzer.esm.StressSurvey;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * This class is used to analyze data about the UserActivityEvent that belongs
 * to a particular survey
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserActivityFeaturesExtractor {

    private final ArrayList<UserActivityEvent> userActivityEvents;
    private static final HashMap<UserActivityEvent.Activity, Double> activityPoints = 
            new HashMap<UserActivityEvent.Activity, Double>();
    
    /**
     * Initializing the HasMap assigning 
     */
    static {
        activityPoints.put(UserActivityEvent.Activity.IN_VEHICLE, 0.0);
        activityPoints.put(UserActivityEvent.Activity.ON_BICYCLE, 0.5);
        activityPoints.put(UserActivityEvent.Activity.ON_FOOT, 0.75);
        activityPoints.put(UserActivityEvent.Activity.RUNNING, 1.0);
        activityPoints.put(UserActivityEvent.Activity.STILL, 0.0);
        activityPoints.put(UserActivityEvent.Activity.TILTING, 0.0);
        activityPoints.put(UserActivityEvent.Activity.UNKNOWN, 0.0);
        activityPoints.put(UserActivityEvent.Activity.WALKING, 0.75);
    }
    
    public UserActivityFeaturesExtractor(ArrayList<UserActivityEvent> userActivityEvents) {
        this.userActivityEvents = userActivityEvents;
    }
    
    /**
     * Object constructor that takes the StressSurvey and the list of 
     * UserActivityEvent and extract from this list only the events that 
     * belongs to the survey timing
     * @param survey the StressSurvey we are considering
     * @param events a UserActivityEvent list with the events to consider 
     */
    public UserActivityFeaturesExtractor(StressSurvey survey, 
            ArrayList<UserActivityEvent> events) {
        
        userActivityEvents = new ArrayList<UserActivityEvent>();
        
        for (UserActivityEvent event: events) {
            if (StressSurvey.isEventInsideSurveyTiming(survey.getTimestamp(), 
                    event.getTimestamp())) {
                
                userActivityEvents.add(event);
            }
        }
    }
    
    /**
     * Calculates a raw sum of the points collected with the activity performed 
     * during the validity of the survey
     * @return a sum of the points of the activities
     */
    public int calculatePointsSumForActivities() {
        
        int sum = 0;
        
        for (UserActivityEvent event: userActivityEvents) {
            sum += activityPoints.get(event.getActivity());
        }
        
        return sum;
    }
    
    /**
     * Provides a weighted and more intelligent evaluation of the activity 
     * performed giving a percentage evaluation
     * @return 
     */
    public double calculatePercentageOfWorkload() {
        
        double weightedSum = 0; int counter = 0;
        
        for (UserActivityEvent event: userActivityEvents) {
            
            if (!event.isUNKNOWN()) {
                weightedSum += activityPoints.get(event.getActivity());
                counter++;
            }
        }

        return weightedSum / (double) counter;
    }
    
    /**
     * Calculates the percentage of influence of walking activity on the total
     * points collected with all the activities
     * @return the percentage of influence of the walking activity
     */
    public double calculateInfluenceOfWalkingActivityOnTotal() {
        
        int sum = calculatePointsSumForActivities();
        int sumForWalking = 0;
                
        for (UserActivityEvent event: userActivityEvents) {
            if (event.isWALKING()) {
                sumForWalking += activityPoints.get(event.getActivity());
            }
        }
        
        return ((double) sumForWalking / (double) sum) * 100;
    }
    
    /**
     * Calculates the percentage of influence of running activity on the total
     * points collected with all the activities
     * @return the percentage of influence of the running activity
     */
    public double calculateInfluenceOfRunningActivityOnTotal() {
        
        int sum = calculatePointsSumForActivities();
        int sumForRunning = 0;
                
        for (UserActivityEvent event: userActivityEvents) {
            if (event.isRUNNING()) {
                sumForRunning += activityPoints.get(event.getActivity());
            }
        }
        
        return ((double) sumForRunning / (double) sum) * 100;
    }
    
    /**
     * Counts the number of TILTING events 
     * @return an integer representing the total number of TILTING events
     */
    public int calculateNumberOfTiltingEvents() {
        
        int total = 0;
        
        for (UserActivityEvent event: userActivityEvents) {
            if (event.isTILTING()) {
                total++;
            }
        }
        
        return total;
    }
}
