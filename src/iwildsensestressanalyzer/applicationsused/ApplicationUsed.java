package iwildsensestressanalyzer.applicationsused;

import java.util.ArrayList;

/**
 * This class holds a sequence of consecutive ApplicationsUsedEvent of the 
 * same application
 * @author Matteo Ciman
 */
public class ApplicationUsed {
    
    private final ArrayList<ApplicationsUsedEvent> applicationsUsedEvents;
    private long timestampEndActivity = 0;
    private final String category;
    
    public ApplicationUsed(ArrayList<ApplicationsUsedEvent> applicationsUsedEvents) {
        this.applicationsUsedEvents = applicationsUsedEvents;
        category = CategorizeApps.categorizeApp(this.applicationsUsedEvents.get(0).getApp());
    }
    
    /**
     * Sets the end timestamp of the ApplicationUsed
     * @param timestampEndActivity the end timestamp
     */
    public void setTimestampEndActivity(long timestampEndActivity) {
        this.timestampEndActivity = timestampEndActivity;
    }
    
    /**
     * Returns if the current timestamp of end activity is already set or not
     * @return true if the end timestamp is already set, false otherwise
     */
    public boolean isTimestampEndActivityAlreadySet() {
        return this.timestampEndActivity != 0;
    }
    
    /**
     * Returns the App name of the ApplicationUsed
     * @return 
     */
    public String getAppName() {
        return applicationsUsedEvents.get(0).getApp();
    }
    
    /**
     * Returns the Category of the ApplicationsUsedEvent
     * @return the category of the application
     */
    public String getAppCategory() {
        return this.category;
    }
    
    /**
     * Returns the size of the List of ApplicationsUsedEvent
     * @return the number of the ApplicationsUsedEvent stored
     */
    public int getNumberOfApplicationsUsedEventList() {
        return this.applicationsUsedEvents.size();
    }
    
    /**
     * Returns the total duration of the ApplicationUsed considering the 
     * starting time of the first one and the end time that could be the 
     * timestamp end activity or the timestamp of the last one activity 
     * (it is clearly an approximation)
     * @return the duration of the ApplicationUsed in terms of milliseconds 
     */
    public Double getTotalDurationOfApplicationUsed() {
        if (isTimestampEndActivityAlreadySet()) {
            return Double.valueOf(this.timestampEndActivity) - 
                    this.applicationsUsedEvents.get(0).getTimestamp();
        }
        else {
            if (this.applicationsUsedEvents.size() > 1) {
                return Double.valueOf(this.applicationsUsedEvents.get(applicationsUsedEvents.size() - 1).getTimestamp() 
                    - this.applicationsUsedEvents.get(0).getTimestamp());
            }
            else {
                return 0.0;
            }
        }
    }
    
    /**
     * Creates a list of ApplicationUsed object wrapping together the same 
     * application together
     * @param applicationsUsedEvent
     * @return 
     */
    public static ArrayList<ApplicationUsed> createListOfApplicationsUsed(ArrayList<ApplicationsUsedEvent> applicationsUsedEvent) {
        
        ArrayList<ApplicationUsed> applicationsUsed = new ArrayList<ApplicationUsed>();
        boolean startWithNewApplicationUsed = true; String currentAppName = "";
        
        ArrayList<ApplicationsUsedEvent> currentListApplicationsUsedEvent = null;
        
        for (int i = 0; i < applicationsUsedEvent.size(); ) {
            
            if (startWithNewApplicationUsed) {
                
                currentListApplicationsUsedEvent = 
                        new ArrayList<ApplicationsUsedEvent>();
                
                currentListApplicationsUsedEvent.add(applicationsUsedEvent.get(i));
                
                currentAppName = applicationsUsedEvent.get(i).getApp();
                startWithNewApplicationUsed = false;
                
                i++;
            }
            else {
                
                /**
                 * Checking if it is the last event of the day or not
                 */
                if (!applicationsUsedEvent.get(i).isLastDay()) {
                
                    /**
                     * The current application is the same, just adding the 
                     * current ApplicationsUsedEvent to the list of the 
                     * current event
                     */
                    if (applicationsUsedEvent.get(i).getApp().equals(currentAppName)) {
                        // continue with the old one, just add
                        currentListApplicationsUsedEvent.add(applicationsUsedEvent.get(i));
                        i++;
                    }
                    else {
                        /**
                         * There is a new ApplicationsUsedEvent, I create the 
                         * ApplicationUsed object with the current list of 
                         * ApplicationsUsedEvents and restart to create another one
                         */
                        ApplicationUsed applicationUsed = new ApplicationUsed(currentListApplicationsUsedEvent);

                        applicationUsed.setTimestampEndActivity(applicationsUsedEvent.get(i).getTimestamp());

                        applicationsUsed.add(applicationUsed);
                        /**
                         * Resetting elements
                         */
                        startWithNewApplicationUsed = true;
                        currentAppName = "";
                        currentListApplicationsUsedEvent = null;
                    }
                }
                else {
                    /**
                     * This is the last element of the day, meaning I have to 
                     * close the current list of applications used events
                     */
                    ApplicationUsed applicationUsed = new ApplicationUsed(currentListApplicationsUsedEvent);
                    applicationUsed.setTimestampEndActivity(applicationsUsedEvent.get(i).getTimestamp());
                    applicationsUsed.add(applicationUsed);
                    
                    startWithNewApplicationUsed = true;
                    currentAppName = ""; currentListApplicationsUsedEvent = null;
                    i++;
                }
            }
        }
        
        return applicationsUsed;
    }
    
}
