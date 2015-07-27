package iwildsensestressanalyzer.participant;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.userpresenceevent.UserPresenceEvent;
import java.util.ArrayList;

/**
 *
 * This class holds all the information of a Participant, from his/her IMEI
 * to the answers provided to the surveys and questionnaires
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class Participant {
    
    private final String IMEI;
    private ArrayList<StressSurvey> stressSurveyList;
    private ArrayList<UserPresenceEvent> userPresenceEventList;
    
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
     * @param linesEvents 
     */
    public void addUserPresenceEvents(ArrayList<String> linesEvents) {
        
        userPresenceEventList = new ArrayList<UserPresenceEvent>();
        
        for (String event: linesEvents) {
            userPresenceEventList.add(new UserPresenceEvent(event));
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
     * @return 
     */
    public int getSurveyAnswersCount() {
        
        return this.stressSurveyList.size();
    }
    
}
