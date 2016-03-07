package iwildsensestressanalyzer.participant;

import iwildsensestressanalyzer.activityservservice.ActivityServServiceEvent;
import iwildsensestressanalyzer.applicationsused.ApplicationsUsedEvent;
import iwildsensestressanalyzer.dataanalyzer.SurveyDataWrapper;
import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.light.UserPresenceLightEvent;
import iwildsensestressanalyzer.touches.TouchesBufferedEvent;
import iwildsensestressanalyzer.useractivity.UserActivityEvent;
import iwildsensestressanalyzer.userpresenceevent.UserPresenceAdvancedEventsWrapper;
import iwildsensestressanalyzer.userpresenceevent.UserPresenceEvent;
import java.util.ArrayList;

/**
 *
 * This class holds all the information of a Participant, from his/her IMEI
 * to the answers provided to the surveys and questionnaires
 * 
 * @author Matteo Ciman
 * @version 1.1
 */
public class Participant {
    
    private final String IMEI;
    private ArrayList<StressSurvey> stressSurveyList;
    private ArrayList<UserPresenceEvent> userPresenceEventsList;
    private ArrayList<TouchesBufferedEvent> touchesBufferedEventsList;
    private ArrayList<UserActivityEvent> userActivityEventsList;
    private ArrayList<ApplicationsUsedEvent> applicationsUsedEventsList;
    private ArrayList<ActivityServServiceEvent> activityServServiceEventsList;
    
    private UserPresenceAdvancedEventsWrapper userPresenceAdvancedEventsWrapper;
    
    private final SurveyDataWrapper[] surveyDataWrappers = {new SurveyDataWrapper(1, false), 
        new SurveyDataWrapper(2, false), new SurveyDataWrapper(3, false), new SurveyDataWrapper(4, false), 
        new SurveyDataWrapper(5, false)};
    
    private final SurveyDataWrapper[] easyDataWrappers = {new SurveyDataWrapper(1, true), 
        new SurveyDataWrapper(3, true), new SurveyDataWrapper(5, true)};
    
    /**
     * Constructor with the IMEI of the participant
     * 
     * @param imei the IMEI of the Participant
     */
    public Participant(String imei) {
        this.IMEI = imei;
    }
    
    /**
     * Returns the IMEI of the Participant
     * @return the IMEI
     */
    public String getIMEI() {
        return IMEI;
    }
    
    /**
     * Creates a set of StressSurvey objects using the lines that contain
     * the provided answers
     * 
     * @param linesAnswers the set of lines with the answers to the stress surveys 
     */
    public void addSurveyAnswers(ArrayList<String> linesAnswers) {
        
        stressSurveyList = new ArrayList<>();
        
        for (String answer: linesAnswers) {
            stressSurveyList.add(new StressSurvey(answer));
        }   
    }
    
    /**
     * Creates a set of UserPresenceEvent objects that represents an interaction
     * of the participant with the screen
     * 
     * @param linesEvents the list of lines recorded with user presence data
     */
    public void addUserPresenceEvents(ArrayList<String> linesEvents) {
        
        userPresenceEventsList = new ArrayList<>();
        
        for (String event: linesEvents) {
            userPresenceEventsList.add(new UserPresenceEvent(event));
        }
        
        /**
         * With all the basic events SCREEN ON/OFF/PRESENT/ROTATION, we can
         * create the AdvancedEvents, i.e. ScreenOnOff or UnlockedScreen 
         * that will be considered for future analysis
         */
        userPresenceAdvancedEventsWrapper = 
                new UserPresenceAdvancedEventsWrapper(userPresenceEventsList);
    }
    
    /**
     * Creates a set of UserActivityEvent objects that represents the activity
     * performed by the participant among the day
     * 
     * @param linesActivities the list of lines recorded with activity data
     */
    public void addUserActivityEvents(ArrayList<String> linesActivities) {
        
        userActivityEventsList = new ArrayList<>();
        
        for (String activity: linesActivities) {
            userActivityEventsList.add(new UserActivityEvent(activity));
        }
    }
    
    /**
     * Creates a set of ApplicationsUsedEvent objects that represents 
     * interaction with applications
     * @param lines the list of lines recorded
     */
    public void addApplicationsUsedEvents(ArrayList<String> lines) {
        
        applicationsUsedEventsList = new ArrayList<>();
        
        for (String line: lines) {
            applicationsUsedEventsList.add(new ApplicationsUsedEvent(line));
        }
    }
    
    /**
     * Creates a set of ActivityServServiceEvent objects that represents the 
     * information about the currently running services 
     * 
     * @param linesServServices the list of lines recorded with the service data
     */
    public void addActivityServServiceEvents(ArrayList<String> linesServServices) {
        
        activityServServiceEventsList = new ArrayList<>();
        
        for (String event: linesServServices) {
            activityServServiceEventsList.add(new ActivityServServiceEvent(event));
        }
    }
    
    /**
     * Calls private methods of the Participant class to add to each Survey
     * of the participant the events that happened during the time validity
     * of the survey
     */
    public void addUserEventsToSurveys() {
        
        addUserPresenceEventsToSurveys();
        addUserActivityEventsToSurveys();
        addApplicationsUsedToSurveys();
    }
    
    /**
     * For each StressSurvey answered by the participant, we search for the
     * correct UserPresenceEvent objects that are associated with that Survey
     */
    private void addUserPresenceEventsToSurveys() {
        
        for (StressSurvey survey: stressSurveyList) {
            survey.addScreenEvents(userPresenceAdvancedEventsWrapper);
        }
    }
    
    /**
     * For each StressSurvey answered by the participant, we search the correct
     * UserActivityEvent objects that are associated with that Survey
     */
    private void addUserActivityEventsToSurveys() {
        
        for (StressSurvey survey: stressSurveyList) {
            survey.addUserActivityEvents(userActivityEventsList);
        }
    }
    
    /**
     * For each StressSurvey answered by the participant, we search for the 
     * correct ApplicationsUsedEvent valid during the survey
     */
    private void addApplicationsUsedToSurveys() {
        
        for (StressSurvey survey: stressSurveyList) {
            survey.addAppliactionUsedList(applicationsUsedEventsList);
        }
    }
    
    /**
     * Takes all the events created for the participant and divides them among 
     * survey wrappers depending on the value of the perceived stress of the 
     * user
     */
    public void spreadEventsAmongSurveyDataWrapper() {
        
        for (SurveyDataWrapper dataWrapper: surveyDataWrappers) {
            
            dataWrapper.createScreenEventsFeaturesExtractor(stressSurveyList);
            dataWrapper.createUserActivityFeaturesExtractor(stressSurveyList);
            dataWrapper.createTouchesBufferedFeaturesExtractor();
            dataWrapper.createApplicationUsedFeaturesExtractor(stressSurveyList);
            dataWrapper.createUserPresenceLightFeaturesExtractor();
        }
        
        for (SurveyDataWrapper dataWrapper: easyDataWrappers) {
            
            dataWrapper.createScreenEventsFeaturesExtractor(stressSurveyList);
            dataWrapper.createUserActivityFeaturesExtractor(stressSurveyList);
            dataWrapper.createTouchesBufferedFeaturesExtractor();
            dataWrapper.createApplicationUsedFeaturesExtractor(stressSurveyList);
            dataWrapper.createUserPresenceLightFeaturesExtractor();
        }
    }
    
    /**
     * Adds the TouchesBuffered events to the set of Screen events
     * @param events a list of TouchesBufferedEvent events
     */
    public void addTouchesBufferedEventsToUnlockedScreen(ArrayList<TouchesBufferedEvent> events) {
        
        for (StressSurvey survey: stressSurveyList) {
            survey.addTouchesBufferedEvent(events);
        }
    }
    
    /**
     * Adds the UserPresenceLight events to the set of Screen events
     * @param events a list of UserPresenceLight events
     */
    public void addUserPresenceLightEvents(ArrayList<UserPresenceLightEvent> events) {
        
        for (StressSurvey survey: stressSurveyList) {
            survey.addUserPresenceLightEvent(events);
        }
    }
    
    /**
     * Returns the StressSurvey provided by the Participant
     * @return the ArrayList of StressSurvey answers
     */
    public ArrayList<StressSurvey> getStressSurveys() {
        
        return this.stressSurveyList;
    }
    
    /**
     * Returns the number of answers provided to the Surveys
     * @return the number of answers provided to the Surveys
     */
    public int getSurveyAnswersCount() {
        
        return this.stressSurveyList.size();
    }
    
    /**
     * Returns all the UserPresenceEvents of the Participant
     * @return the list of UserPresenceEvent
     */
    public ArrayList<UserPresenceEvent> getUserPresenceEventsList() {
        
        return this.userPresenceEventsList;
    }
    
    /**
     * Returns the SurveyDataWrappers used to store data related for each value
     * of the 
     * @return 
     */
    public SurveyDataWrapper[] getSurveyDataWrappers() {
        return this.surveyDataWrappers;
    }
    
    /**
     * Returns the easy version of the SurveyDataWrappers used to store data 
     * combining together relaxed (1 and 2), normal (3) and stressed (4 and 5)
     * @return 
     */
    public SurveyDataWrapper[] getEasySurveyDataWrappers() {
        return this.easyDataWrappers;
    }
    
    /**
     * Returns if the participant has ever used the application category
     * during the time validity of all the surveys
     * @param appCategory the application category
     * @return true if the participant used the application category, false 
     * otherwise
     */
    public Boolean hasUsedTheApplicationCategory(String appCategory) {
        
        Boolean used = false;
        
        for (int i = 0; i < stressSurveyList.size() && !used; i++) {
            
            if (stressSurveyList.get(i).isApplicationCategoryUsed(appCategory)) {
                used = true;
            }
        }
        
        return used;
    }
}
