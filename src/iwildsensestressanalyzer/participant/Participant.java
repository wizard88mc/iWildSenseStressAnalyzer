package iwildsensestressanalyzer.participant;

import iwildsensestressanalyzer.activityservservice.ActivityServServiceEvent;
import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.useractivity.UserActivityEvent;
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
    private ArrayList<UserActivityEvent> userActivityEventsList;
    private ArrayList<ActivityServServiceEvent> activityServServiceEventsList;
    
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
        
        stressSurveyList = new ArrayList<StressSurvey>();
        
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
        
        userPresenceEventsList = new ArrayList<UserPresenceEvent>();
        
        for (String event: linesEvents) {
            userPresenceEventsList.add(new UserPresenceEvent(event));
        }
    }
    
    /**
     * Creates a set of UserActivityEvent objects that represents the activity
     * performed by the participant among the day
     * 
     * @param linesActivities the list of lines recorded with activity data
     */
    public void addUserActivityEvents(ArrayList<String> linesActivities) {
        
        userActivityEventsList = new ArrayList<UserActivityEvent>();
        
        for (String activity: linesActivities) {
            userActivityEventsList.add(new UserActivityEvent(activity));
        }
    }
    
    /**
     * Creates a set of ActivityServServiceEvent objects that represents the 
     * information about the currently running services 
     * 
     * @param linesServServices the list of lines recorded with the service data
     */
    public void addActivityServServiceEvents(ArrayList<String> linesServServices) {
        
        activityServServiceEventsList = new ArrayList<ActivityServServiceEvent>();
        
        for (String event: linesServServices) {
            activityServServiceEventsList.add(new ActivityServServiceEvent(event));
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
    
}
