package iwildsensestressanalyzer;

import iwildsensestressanalyzer.dataanalyzer.ScreenEventsAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.SurveyAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.TouchesBufferedAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.UserActivityAnalyzer;
import iwildsensestressanalyzer.filereader.ApplicationsUsedReader;
import iwildsensestressanalyzer.filereader.IMEIListReader;
import iwildsensestressanalyzer.filereader.SurveyQuestionnaireReader;
import iwildsensestressanalyzer.filereader.TouchesBufferedReader;
import iwildsensestressanalyzer.filereader.UserActivityReader;
import iwildsensestressanalyzer.filereader.UserPresenceEventsReader;
import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.touches.TouchesBufferedEvent;
import java.util.ArrayList;

/**
 *
 * Manages all the steps necessary to analyze data about the 4 weeks experiment
 * 
 * @author Matteo Ciman
 * @version 1.1
 */
public class IWildSenseStressAnalyzer {
    
    public static final boolean DEBUG = false;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        /**
         * Step 1: collect all the IMEI of the participants that will be used
         * to retrieve the files
         */
        ArrayList<String> listImei = IMEIListReader.getIMEIsList();
        
        /**
         * Step 2: For each IMEI, we create a Participant object that will hold
         * all the answers and his/her behavior
         */
        ArrayList<Participant> participantList = new ArrayList<Participant>();
        
        for (String imei: listImei) {
            
            System.out.println("Acquiring data for participant: " + imei);
            
            /**
             * Creating a new Participant
             */
            Participant newParticipant = new Participant(imei);
            
            /**
             * Retrieving all the answers provided
             */
            ArrayList<String> surveyAnswers = new ArrayList<String>(),
                    questionnaireAnswers = new ArrayList<String>();
            
            SurveyQuestionnaireReader.readFile(imei, surveyAnswers, 
                    questionnaireAnswers);
            
            /**
             * Adding the answers to the Surveys
             */
            newParticipant.addSurveyAnswers(surveyAnswers);
            
            /**
             * Adding the answers to the Questionnaire 
             */
            
            /**
             * Adding the UserPresenceEvent events to the participant
             */
            ArrayList<String> userPresenceEventsLines = 
                    UserPresenceEventsReader.getAllUserPresenceEventsLines(newParticipant);
            newParticipant.addUserPresenceEvents(userPresenceEventsLines);
            
            /**
             * Adding the UserActivityEvent events to the participant
             */
            ArrayList<String> userActivityEventsLines = 
                    UserActivityReader.getAllUserActivityEventsLines(newParticipant);
            newParticipant.addUserActivityEvents(userActivityEventsLines);
            
            /**
             * Adding the ActivityServServiceEvent events to the participant
             */
            /*ArrayList<String> activityServServiceEventsLines = 
                    ActivityServServiceReader.getAllActivityServServiceEventsLines(newParticipant);
            newParticipant.addActivityServServiceEvents(activityServServiceEventsLines);*/
            
            /**
             * Retrieving the ApplicationUsed lines 
             */
            ArrayList<String> applicationUsedEventsLines = 
                    ApplicationsUsedReader.getAllApplicationsUsedEventsLines(newParticipant);
            
            
            
            /**
             * Making association between survey time validity and events
             */
            newParticipant.addUserEventsToSurveys();
            
            /**
             * Retrieving the TouchesBuffered events
             */
            ArrayList<String> touchesBufferedEventsLines = 
                    TouchesBufferedReader.getAllTouchesBufferedEventsLines(newParticipant);
            
            /**
             * Adding TouchesBufferedEvent events to the UnlockedScreen 
             * events
             */
            newParticipant.addTouchesBufferedEventsToUnlockedScreen(
                    TouchesBufferedEvent.createListOfTouchesBufferedEvents(touchesBufferedEventsLines));
            
            newParticipant.spreadEventsAmongSurveyDataWrapper();
            
            participantList.add(newParticipant);
        }
        
        /**
         * Step 3: Printing statistics about the surveys
         */
        SurveyAnalyzer.analyzeSurveysAnswers(participantList);
        
        //ScreenEventsAnalyzer.analyzeScreenDataForEachParticipant(participantList, false);
        //UserActivityAnalyzer.analyzeUserActivityDataForEachParticipant(participantList, false);
        TouchesBufferedAnalyzer.analyzeTouchesBufferedDataForEachParticipant(participantList, false);
        
        //ScreenEventsAnalyzer.analyzeScreenDataForEachParticipant(participantList, true);
        //UserActivityAnalyzer.analyzeUserActivityDataForEachParticipant(participantList, true);
        TouchesBufferedAnalyzer.analyzeTouchesBufferedDataForEachParticipant(participantList, true);
        
        
    }
    
}
