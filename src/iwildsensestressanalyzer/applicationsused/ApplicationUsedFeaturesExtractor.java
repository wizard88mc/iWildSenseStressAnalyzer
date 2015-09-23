package iwildsensestressanalyzer.applicationsused;

import java.util.ArrayList;

/**
 *
 * Class responsible to extract features from the ApplicationUsed objects
 * FEATURES EXTRACTED
 * 
 * 0.1 - Influence of a particular application category on the total application
 *       used
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class ApplicationUsedFeaturesExtractor {
 
    private final ArrayList<ApplicationUsed> applicationUsedList;
    
    public ApplicationUsedFeaturesExtractor(ArrayList<ApplicationUsed> applicationUsedList) {
        this.applicationUsedList = applicationUsedList;
    }
    
    /**
     * Calculate the influence of a particular Application Category over the 
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
    
}
