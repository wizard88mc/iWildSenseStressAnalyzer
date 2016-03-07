package iwildsensestressanalyzer.applicationsused;

import java.util.ArrayList;

/**
 *
 * Class responsible to extract features from the ApplicationUsed objects
 * FEATURES EXTRACTED
 * 
 * 0.1 - Influence of a particular application category over the total application
 *       used
 * 0.2 - Influence of a particular application category in terms of timing 
 *       duration over the total timing (not sure good feature)
 * 0.3 - Influence of a particular application type over the total times of 
 *       applications used
 * 0.4 - Timing influence of a particular application type over the total 
 *       amount of time
 * 
 * @author Matteo Ciman
 * @version 0.3
 */
public class ApplicationUsedFeaturesExtractor {
 
    private final ArrayList<ApplicationUsed> applicationUsedList;
    
    public ApplicationUsedFeaturesExtractor(ArrayList<ApplicationUsed> applicationUsedList) {
        this.applicationUsedList = applicationUsedList;
    }
    
    /**
     * Calculates the influence of a particular Application Category over the 
     * total number of ApplicationsUsedEvent
     * @param appCategory the category we want to analyze
     * @return the ratio between the number of the target events and the total events
     */
    public double calculateInfluenceOfAppCategory(String appCategory) {
        
        double totalEvents = 0.0, targetEvents = 0.0;
        
        for (ApplicationUsed applicationUsed: applicationUsedList) {
            
            if (applicationUsed.getAppCategory() != null) {
                totalEvents += applicationUsed.getNumberOfApplicationsUsedEventList();
            }
            
            if (applicationUsed.getAppCategory() != null && 
                    applicationUsed.getAppCategory().equals(appCategory)) {
                targetEvents += applicationUsed.getNumberOfApplicationsUsedEventList();
            }
        }
        
        return (double) targetEvents / (double) totalEvents;
    }
    
    /**
     * Calculates the influence in terms of timing of a particular Application
     * Category over the total timing of application
     * @param appCategory the category we are considering
     * @return the ratio between the timing of the app category considered and 
     * the total timing
     */
    public double calculateTimingInfluenceOfAppCategory(String appCategory) {
        
        double totalTiming = 0.0, appTiming = 0.0;
        
        for (ApplicationUsed applicationUsed: applicationUsedList) {
            
            if (applicationUsed.getAppCategory() != null) {
                totalTiming += applicationUsed.getTotalDurationOfApplicationUsed();
            }
            
            if (applicationUsed.getAppCategory() != null && 
                    applicationUsed.getAppCategory().equals(appCategory)) {
                appTiming += applicationUsed.getTotalDurationOfApplicationUsed();
            }
        }
        
        return appTiming / totalTiming;
    }
    
    /**
     * Calculates the influence of a particular Application Type over the 
     * total number of ApplicationsUsedEvent
     * @param appType the application type we want to analyze
     * @return the ratio between the number of the target events and the total events
     */
    public double calculateInfluenceOfAppType(String appType) {
        
        double totalEvents = 0.0, targetEvents = 0.0;
        
        for (ApplicationUsed applicationUsed: applicationUsedList) {
            
            if (applicationUsed.getAppOrGame()!= null) {
                totalEvents += applicationUsed.getNumberOfApplicationsUsedEventList();
            }
            
            if (applicationUsed.getAppOrGame()!= null && 
                    applicationUsed.getAppOrGame().equals(appType)) {
                targetEvents += applicationUsed.getNumberOfApplicationsUsedEventList();
            }
        }
        
        return (double) targetEvents / (double) totalEvents;
    }
    
    /**
     * Calculates the influence of a particular Application Type over the 
     * total number of ApplicationsUsedEvent
     * @param appType the application type we want to analyze
     * @return the ratio between the number of the target events and the total events
     */
    public double calculateTimingInfluenceOfAppType(String appType) {
        
        double totalTiming = 0.0, appTiming = 0.0;
        
        for (ApplicationUsed applicationUsed: applicationUsedList) {
            
            if (applicationUsed.getAppOrGame() != null) {
                totalTiming += applicationUsed.getTotalDurationOfApplicationUsed();
            }
            
            if (applicationUsed.getAppOrGame() != null && 
                    applicationUsed.getAppOrGame().equals(appType)) {
                
                appTiming += applicationUsed.getTotalDurationOfApplicationUsed();
            }
        }
        
        return appTiming / totalTiming;
    }
    
    /**
     * Returns if the application category has been used or not
     * @param appCategory the application category
     * @return true if the application category has been used, false otherwise
     */
    public Boolean hasUsedTheApplicationCategory(String appCategory) {
        
        Boolean hasUsed = false;
        
        for (int i = 0; i <applicationUsedList.size() && !hasUsed; i++) {
            
            if (applicationUsedList.get(i).getAppCategory().equals(appCategory)) {
                hasUsed = true;
            }
        }
        
        return hasUsed;
    }
}
