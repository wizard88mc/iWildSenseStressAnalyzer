package iwildsensestressanalyzer;

import iwildsensestressanalyzer.dataanalyzer.ApplicationUsedAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.ScreenEventsAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.SurveyAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.TouchesBufferedAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.UserActivityAnalyzer;
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
    
    public static final boolean DEBUG = false;
    public static final boolean COMPELTE_ANALYSIS = false;

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
        int counter = 1;
        for (String imei: listImei) {
            
            System.out.println("Acquiring data for participant (" + counter + 
                    "/" + listImei.size() + "): " + imei);
            
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
            
            if (COMPELTE_ANALYSIS) {
                ArrayList<String> userPresenceEventsLines = 
                    UserPresenceEventsReader.getAllUserPresenceEventsLines(newParticipant);
                newParticipant.addUserPresenceEvents(userPresenceEventsLines);
            
                if (DEBUG) {
                    System.out.println("DEBUG: Adding the UserPresenceEvent events to the "+ 
                        "participant");
                }
                /**
                 * Adding the UserPresenceEvent events to the participant
                 */

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
            }
            
            participantList.add(newParticipant);
            counter++;
        }
        
        /**
         * Step 3: Printing statistics about the surveys
         */
        SurveyAnalyzer.calculateStatisticsAnswers(participantList);
        
        /**
         * Analyzing answers provided, to understand how many participants 
         * have the number of answers higher than the average
         */
        SurveyAnalyzer.printAnalysisParticipantsParticipation(participantList);
        
        /**
         * Calculating statistics for participants with more than one answer
         * per day
         */
        System.out.println();
        System.out.println("*** Removing participants with 0 answers "
                + "provided ***");
        SurveyAnalyzer.calculateStatisticsAnswers(SurveyAnalyzer.
                getListParticipantsWithMoreThanZeroAnswers(participantList));
        
        /**
         * Calculating statistics for participants with more than our
         * threshold answers number
         */
        System.out.println();
        System.out.println("*** Keeping only participants with answers higher than "
                + "our arbitrary threshold ***");
        SurveyAnalyzer.calculateStatisticsAnswers(SurveyAnalyzer.
                getListParticipantsOverArbitraryThreshold(participantList));
        
        /**
         * Calculating statistics for participants with more than one per 
         * day answer
         */
        System.out.println();
        System.out.println("*** Keeping participants with more than one survey "
                + "answer per day ***");
        SurveyAnalyzer.calculateStatisticsAnswers(SurveyAnalyzer.
                getListParticipantsOverOnePerDayAnswer(participantList));
        
        /**
         * Calculating statistics for participants with more answers than the
         * initial average
         */
        System.out.println();
        System.out.println("*** Keeping participants with number of answers "
                + "higher than the initial average ***");
        SurveyAnalyzer.calculateStatisticsAnswers(SurveyAnalyzer.
                getListParticipantsOverInitialAverage(participantList));
        
        /**
         * First Step: all features, difficult task, participants divided
         * Second Step: all features, difficult task, participants together
         * Third Step: all features, easy task, participants divided
         * Fourth Step: all features, easy task, participants together
         */
        
        /*performAnalysis(participantList, false, false);
        performAnalysis(participantList, false, true);
        performAnalysis(participantList, true, false);
        performAnalysis(participantList, true, true);*/
    }
    
    /**
     * Calls all the methods of the different analyzer to perform the data
     * analysis
     * @param participantsList the list of the participants
     * @param easyTask true if it is the easy analysis, false otherwise
     * @param allTogether true if combine together data of all participants, 
     * false otherwise
     */
    private static void performAnalysis(ArrayList<Participant> participantsList, 
            boolean easyTask, boolean allTogether) {
        
        ScreenEventsAnalyzer.analyzeScreenDataForEachParticipant(participantsList, 
                easyTask, allTogether);
        UserActivityAnalyzer.analyzeUserActivityDataForEachParticipant(participantsList, 
                easyTask, allTogether);
        TouchesBufferedAnalyzer.analyzeTouchesBufferedDataForEachParticipant(participantsList, 
                easyTask, allTogether);
        ApplicationUsedAnalyzer.analyzeDataOfApplicationUsedForEachParticipant(participantsList, 
                easyTask, allTogether);
        UserPresenceLightAnalyzer.analyzeUserPresenceLightDataForEachParticipant(participantsList, 
                easyTask, allTogether);
        
    }
}
