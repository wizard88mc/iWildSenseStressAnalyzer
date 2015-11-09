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
 *       duration over the total timing (non sure good feature)
 * 
 * @author Matteo Ciman
 * @version 0.2
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
    
}
