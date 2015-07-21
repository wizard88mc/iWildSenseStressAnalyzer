package iwildsensestressanalyzer.participant;

import iwildsensestressanalyzer.esm.StressSurvey;
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
    
}
