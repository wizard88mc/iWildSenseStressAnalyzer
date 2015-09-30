package iwildsensestressanalyzer;

import iwildsensestressanalyzer.dataanalyzer.ApplicationUsedAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.ScreenEventsAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.SurveyAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.UserPresenceLightAnalyzer;
import iwildsensestressanalyzer.filereader.ApplicationsUsedReader;
import iwildsensestressanalyzer.filereader.IMEIListReader;
import iwildsensestressanalyzer.filereader.SurveyQuestionnaireReader;
import iwildsensestressanalyzer.filereader.TouchesBufferedReader;
import iwildsensestressanalyzer.filereader.UserActivityReader;
import iwildsensestressanalyzer.filereader.UserPresenceEventsReader;
import iwildsensestressanalyzer.filereader.UserPresenceLightReader;
import iwildsensestressanalyzer.light.UserPresenceLightEvent;
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
    
    public static final boolean DEBUG = true;

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
            
            if (DEBUG) {
                System.out.println("DEBUG: Adding the UserPresenceEvent events to the "+ 
                        "participant");
            }
            /**
             * Adding the UserPresenceEvent events to the participant
             */
            ArrayList<String> userPresenceEventsLines = 
                    UserPresenceEventsReader.getAllUserPresenceEventsLines(newParticipant);
            newParticipant.addUserPresenceEvents(userPresenceEventsLines);
            
            if (DEBUG) {
                System.out.println("DEBUG: Adding the UserActivityEvent events " + 
                        "to the participant");
            }
            /**
             * Adding the UserActivityEvent events to the participant
             */
            ArrayList<String> userActivityEventsLines = 
                    UserActivityReader.getAllUserActivityEventsLines(newParticipant);
            newParticipant.addUserActivityEvents(userActivityEventsLines);
            
            if (DEBUG) {
                System.out.println("DEBUG: Retrieving Light lines");
            }
            /**
             * Retrieving Light lines 
             */
            ArrayList<String> userPresenceLightEventsLines = 
                    UserPresenceLightReader.getALlUserPresenceLightEventsLines(newParticipant);
            
            /**
             * Adding the ActivityServServiceEvent events to the participant
             */
            /*ArrayList<String> activityServServiceEventsLines = 
                    ActivityServServiceReader.getAllActivityServServiceEventsLines(newParticipant);
            newParticipant.addActivityServServiceEvents(activityServServiceEventsLines);*/
            
            if (DEBUG) {
                System.out.println("DEBUG: Retrieving ApplicationUsed lines");
            }
            /**
             * Retrieving the ApplicationUsed lines 
             */
            ArrayList<String> applicationUsedEventsLines = 
                    ApplicationsUsedReader.getAllApplicationsUsedEventsLines(newParticipant);
            
            if (DEBUG) {
                System.out.println("DEBUG: Adding ApplicationUsed Events");
            }
            newParticipant.addApplicationsUsedEvents(applicationUsedEventsLines);
            
            
            if (DEBUG) {
                System.out.println("DEBUG: Making association between survey " + 
                        "time validity and events");
            }
            /**
             * Making association between survey time validity and events
             */
            newParticipant.addUserEventsToSurveys();
            
            if (DEBUG) {
                System.out.println("DEBUG: Retrieving TouchesBuffered events");
            }
            /**
             * Retrieving the TouchesBuffered events
             */
            ArrayList<String> touchesBufferedEventsLines = 
                    TouchesBufferedReader.getAllTouchesBufferedEventsLines(newParticipant);
            
            if (DEBUG) {
                System.out.println("DEBUG: Adding TouchesBufferedEvent events " + 
                        "to the UnlockedScreen events");
            }
            /**
             * Adding TouchesBufferedEvent events to the UnlockedScreen 
             * events
             */
            newParticipant.addTouchesBufferedEventsToUnlockedScreen(
                    TouchesBufferedEvent.createListOfTouchesBufferedEvents(touchesBufferedEventsLines));
            
            if (DEBUG) {
                System.out.println("DEBUG: Adding UserPresenceLightEvent " + 
                        "to the Screen events");
            }
            /**
             * Adding UserPresenceLightEvent events to the Screen events
             */
            newParticipant.addUserPresenceLightEvents(UserPresenceLightEvent.
                    createListOfUserPresenceLightEvents(userPresenceLightEventsLines));
            
            if (DEBUG) {
                System.out.println("DEBUG: Spreading events among survey data wrapper");
            }
            newParticipant.spreadEventsAmongSurveyDataWrapper();
            
            participantList.add(newParticipant);
        }
        
        /**
         * Step 3: Printing statistics about the surveys
         */
        SurveyAnalyzer.analyzeSurveysAnswers(participantList);
        
        ScreenEventsAnalyzer.analyzeScreenDataForEachParticipant(participantList, false, true);
        //UserActivityAnalyzer.analyzeUserActivityDataForEachParticipant(participantList, false);
        //TouchesBufferedAnalyzer.analyzeTouchesBufferedDataForEachParticipant(participantList, false);
        //ApplicationUsedAnalyzer.analyzeDataOfApplicationUsedForEachParticipant(participantList, false);
        //UserPresenceLightAnalyzer.analyzeUserPresenceLightDataForEachParticipant(participantList, false);
        
        ScreenEventsAnalyzer.analyzeScreenDataForEachParticipant(participantList, true, true);
        //UserActivityAnalyzer.analyzeUserActivityDataForEachParticipant(participantList, true);
        //TouchesBufferedAnalyzer.analyzeTouchesBufferedDataForEachParticipant(participantList, true);
        //ApplicationUsedAnalyzer.analyzeDataOfApplicationUsedForEachParticipant(participantList, true);
        //UserPresenceLightAnalyzer.analyzeUserPresenceLightDataForEachParticipant(participantList, true);
    }
    
}
