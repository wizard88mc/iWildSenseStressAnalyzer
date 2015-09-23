package iwildsensestressanalyzer.applicationsused;

import java.util.ArrayList;

/**
 *
 * @author Matteo
 */
public class ApplicationUsedFeaturesExtractor {
 
    private ArrayList<ApplicationUsed> applicationUsedList;
    
    /**
     * Calculate the influence of a particular Application Category over the 
     * total number of ApplicationsUsedEvent
     * @param appCategory the category we want to analyze
     * @return the ratio between the number of the target events and the total events
     */
    public double calculateInfluenceOfAppCategory(String appCategory) {
        
        double totalEvents = 0.0, targetEvents = 0.0;
        
        for (ApplicationUsed applicationUsed: applicationUsedList) {
            
            totalEvents += applicationUsed.getNumberOfApplicationsUsedEventList();
            
            if (applicationUsed.getAppCategory().equals(appCategory)) {
                targetEvents += applicationUsed.getNumberOfApplicationsUsedEventList();
            }
        }
        
        return (double) targetEvents / (double) totalEvents;
    }
    
}
