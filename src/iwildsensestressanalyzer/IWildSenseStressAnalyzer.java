package iwildsensestressanalyzer;

import iwildsensestressanalyzer.applicationsused.CategorizeApps;
import iwildsensestressanalyzer.dataanalyzer.ApplicationUsedAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.EventsAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.ScreenEventsAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.StatisticsAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.StressValuesAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.SurveyAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.TouchesBufferedAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.UserActivityAnalyzer;
import iwildsensestressanalyzer.dataanalyzer.UserPresenceLightAnalyzer;
import iwildsensestressanalyzer.esm.WeatherInfos;
import iwildsensestressanalyzer.filereader.ApplicationsUsedReader;
import iwildsensestressanalyzer.filereader.IMEIListReader;
import iwildsensestressanalyzer.filereader.SurveyQuestionnaireReader;
import iwildsensestressanalyzer.filereader.TouchesBufferedReader;
import iwildsensestressanalyzer.filereader.UserActivityReader;
import iwildsensestressanalyzer.filereader.UserPresenceEventsReader;
import iwildsensestressanalyzer.filereader.UserPresenceLightReader;
import iwildsensestressanalyzer.filereader.WeatherInfoReader;
import iwildsensestressanalyzer.filewriter.StatisticalSignificanceOutputWriter;
import iwildsensestressanalyzer.light.UserPresenceLightEvent;
import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.touches.TouchesBufferedEvent;
import iwildsensestressanalyzer.weka.WekaAnalyzer;
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
    public static final boolean COMPELTE_ANALYSIS = true;
    public static StatisticalSignificanceOutputWriter outputWriter = null;
    
    public static final String DATA_ACQUISITION = "data_acquisition", 
            WEKA_ANALYSIS = "weka_analysis", 
            PREFERENCE_LEARNING_PREPARATION = "preference_preparation",
            KEEP_ALL = "all", 
            ONLY_MORE_THAN_ZERO = "only_more_zero", 
            ONLY_MORE_THRESHOLD = "only_more_threshold",
            ONLY_MORE_ONE_PER_DAY= "only_one_per_day",
            ONLY_MORE_INITIAL_AVERAGE = "only_more_average";
    
    public static final String TITLE_ALL_PARTICIPANTS = "*** Keeping all "
                + "participants ***",
            
            TITLE_PARTICIPANTS_MORE_ZERO_ANSWERS = "*** Removing"
                + " participants with 0 answers provided ***", 
            
            TITLE_MORE_THRESHOLD = "*** Keeping only participants with answers"
                + " higher than our arbitrary threshold ***", 
            
            TITLE_MORE_ONE_SURVEY_PER_DAY = "*** Keeping participants with more"
                + " than one survey answer per day ***", 
            
            TITLE_MORE_THAN_INITIAL_AVERAGE = "*** Keeping participants with"
                + " number of answers higher than the initial average ***";
    
    public static final String FOLDER_MORE_ZERO_ANSWERS = "More_Zero_Answers",
            FOLDER_MORE_THRESHOLD = "More_Than_Threshold",
            FOLDER_MORE_ONE_SURVEY_PER_DAY = "More_Than_OnePerDay",
            FOLDER_MORE_THAN_INITIAL_AVERAGE = "More_Than_Average";

    /**
     * @param args the command line arguments
     * - [0]: "data_acquisition" or "weka_analysis"
     * - [1]: "all", "only_more_zero", "only_more_threshold", "only_one_per_day", 
     *        "only_more_average"
     */
    public static void main(String[] args) {

        if (args[0].equals(DATA_ACQUISITION)) {
            
            if (outputWriter == null) {
                outputWriter = new StatisticalSignificanceOutputWriter();
            }
            /**
             * Step 1: collect all the IMEI of the participants that will be used
             * to retrieve the files
             */
            ArrayList<String> listImei = IMEIListReader.getIMEIsList();

            /**
             * Step 2: For each IMEI, we create a Participant object that will hold
             * all the answers and his/her behavior
             */
            ArrayList<Participant> participantList = new ArrayList<>();
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
                ArrayList<String> surveyAnswers = new ArrayList<>(),
                        questionnaireAnswers = new ArrayList<>();

                SurveyQuestionnaireReader.readFile(imei, surveyAnswers, 
                        questionnaireAnswers);
                
                ArrayList<String> weatherLines = WeatherInfoReader
                        .readFile(newParticipant.getIMEI());
                
                ArrayList<WeatherInfos> weatherInfos = new ArrayList<>();
                for (String line: weatherLines) {
                    weatherInfos.add(new WeatherInfos(line));
                }

                /**
                 * Adding the answers to the Surveys
                 */
                newParticipant.addSurveyAnswers(surveyAnswers, weatherInfos);

                if (COMPELTE_ANALYSIS) {
                    ArrayList<String> userPresenceEventsLines = 
                        UserPresenceEventsReader.getAllUserPresenceEventsLines(newParticipant);
                    newParticipant.addUserPresenceEvents(userPresenceEventsLines);

                    if (DEBUG) {
                        System.out.println("DEBUG: Adding the UserPresenceEvent"
                                + " events to the participant");
                    }
                    /**
                     * Adding the UserPresenceEvent events to the participant
                     */

                    if (DEBUG) {
                        System.out.println("DEBUG: Adding the UserActivityEvent"
                                + " events to the participant");
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
                        UserPresenceLightReader
                            .getALlUserPresenceLightEventsLines(newParticipant);


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
                        ApplicationsUsedReader
                            .getAllApplicationsUsedEventsLines(newParticipant);

                    if (DEBUG) {
                        System.out.println("DEBUG: Adding ApplicationUsed"
                                + " Events");
                    }

                    newParticipant.addApplicationsUsedEvents(applicationUsedEventsLines);

                    if (DEBUG) {
                        System.out.println("DEBUG: Making association between"
                                + " survey time validity and events");
                    }
                    /**
                     * Making association between survey time validity and events
                     */

                    newParticipant.addUserEventsToSurveys();


                    if (DEBUG) {
                        System.out.println("DEBUG: Retrieving TouchesBuffered"
                                + " events");
                    }
                    /**
                     * Retrieving the TouchesBuffered events
                     */
                    ArrayList<String> touchesBufferedEventsLines = 
                            TouchesBufferedReader
                                .getAllTouchesBufferedEventsLines(newParticipant);

                    if (DEBUG) {
                        System.out.println("DEBUG: Adding TouchesBufferedEvent"
                                + " events to the UnlockedScreen events");
                    }
                    /**
                     * Adding TouchesBufferedEvent events to the UnlockedScreen 
                     * events
                     */
                    newParticipant.addTouchesBufferedEventsToUnlockedScreen(
                            TouchesBufferedEvent
                                    .createListOfTouchesBufferedEvents(touchesBufferedEventsLines));

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
                        System.out.println("DEBUG: Spreading events among"
                                + " survey data wrapper");
                    }
                    newParticipant.spreadEventsAmongSurveyDataWrapper();
                }

                participantList.add(newParticipant);
                counter++;
            }
            
            CategorizeApps.closeFile();

            /**
             * Step 3: Printing statistics about the surveys
             */
            SurveyAnalyzer.calculateStatisticsAnswers(participantList);

            /**
             * Step 4: Analyzing answers provided, to understand how many participants 
             * have the number of answers higher than the average
             */
            SurveyAnalyzer.printAnalysisParticipantsParticipation(participantList);

            /**
             * Calculating statistics for participants with more than one answer
             * per day
             */
            
            ArrayList<Participant> participantsToUse = null;
            
            switch(args[1]) {
            
                case ONLY_MORE_THAN_ZERO: {
                    System.out.println();
                    System.out.println(TITLE_PARTICIPANTS_MORE_ZERO_ANSWERS);
                    EventsAnalyzer.printTitleMessage(null);
                    EventsAnalyzer.printTitleMessage(TITLE_PARTICIPANTS_MORE_ZERO_ANSWERS);

                    participantsToUse = SurveyAnalyzer.
                            getListParticipantsWithMoreThanZeroAnswers(participantList);

                    SurveyAnalyzer.calculateStatisticsAnswers(participantsToUse);

                    StatisticsAnalyzer statisticsAnalyzer = 
                            new StatisticsAnalyzer(participantsToUse);
                    statisticsAnalyzer.performAnalysis();

                    performStatisticalAnalysisSteps(participantsToUse);
            
                    break;
                }

            /**
             * Calculating statistics for participants with more than our
             * threshold answers number
             */
                case ONLY_MORE_THRESHOLD: {
                    System.out.println();
                    System.out.println(TITLE_MORE_THRESHOLD);
                    EventsAnalyzer.printTitleMessage(null);
                    EventsAnalyzer.printTitleMessage(TITLE_MORE_THRESHOLD);

                    participantsToUse = 
                            SurveyAnalyzer.
                            getListParticipantsOverArbitraryThreshold(participantList);

                    SurveyAnalyzer.calculateStatisticsAnswers(participantsToUse);

                    performStatisticalAnalysisSteps(participantsToUse);
                    
                    break; 
                }

            /**
             * Calculating statistics for participants with more than one per 
             * day answer
             */
                case ONLY_MORE_ONE_PER_DAY: {
                    System.out.println();
                    System.out.println(TITLE_MORE_ONE_SURVEY_PER_DAY);
                    EventsAnalyzer.printTitleMessage(null);
                    EventsAnalyzer.printTitleMessage(TITLE_MORE_ONE_SURVEY_PER_DAY);

                    participantsToUse = SurveyAnalyzer.
                            getListParticipantsOverOnePerDayAnswer(participantList);

                    SurveyAnalyzer.calculateStatisticsAnswers(participantsToUse);

                    performStatisticalAnalysisSteps(participantsToUse);

                    break;
                }

            /**
             * Calculating statistics for participants with more answers than the
             * initial average
             */
                case ONLY_MORE_INITIAL_AVERAGE: {
                    System.out.println();
                    System.out.println(TITLE_MORE_THAN_INITIAL_AVERAGE);
                    EventsAnalyzer.printTitleMessage(null);
                    EventsAnalyzer.printTitleMessage(TITLE_MORE_THAN_INITIAL_AVERAGE);

                    ArrayList<Participant> participantsListWithMoreThanAverageAnswers = 
                            SurveyAnalyzer.
                            getListParticipantsOverInitialAverage(participantList);

                    SurveyAnalyzer.calculateStatisticsAnswers(participantsListWithMoreThanAverageAnswers);

                    performStatisticalAnalysisSteps(participantsListWithMoreThanAverageAnswers);

                    break;
                }
            }

            outputWriter.closeFile();

            /**
             * Now starting working with Weka files for classification
             */
            System.out.println("*** Creating weka files for classification task ***");
            
            switch(args[1]) {
                case ONLY_MORE_THAN_ZERO: {
            
                    System.out.println("*** Creating file for classification task for "
                        + "participants with more than 0 answers ***");
                    WekaAnalyzer.createWekaFiles(participantsToUse, 
                        FOLDER_MORE_ZERO_ANSWERS);

                    break;
                }
                case ONLY_MORE_THRESHOLD: {
            
                    System.out.println("*** Creating file for classification task for "
                        + "participants with answers more than the arbitrary threshold ***");
                    WekaAnalyzer.createWekaFiles(participantsToUse, 
                        FOLDER_MORE_THRESHOLD);

                    break;
                }
                case ONLY_MORE_ONE_PER_DAY: {
                
                    System.out.println("*** Creating files for classification task for "
                        + "participants with more than one answer per day ***");
                    WekaAnalyzer.createWekaFiles(participantsToUse, 
                        FOLDER_MORE_ONE_SURVEY_PER_DAY);

                    break;
                }
                case ONLY_MORE_INITIAL_AVERAGE: {
            
                    System.out.println("*** Creating files for classification task for "
                        + "participant with more answers than the initial average ***");
                    WekaAnalyzer.createWekaFiles(participantsToUse, 
                        FOLDER_MORE_THAN_INITIAL_AVERAGE);
                
                    break;
                }
            }

            WekaAnalyzer.createdFilesWriter.closeFile();
            
            WeatherInfos.printWeatherPossibilities();
        }
        else if (args[0].equals(WEKA_ANALYSIS)) {
            WekaAnalyzer.startWithWekaClassificationTask(args[1], args[2]);
        }
    }
    
    private static void performStatisticalAnalysisSteps(ArrayList<Participant> participantsList) {
        
        /**
         * First Step: all features, difficult task, participants divided
         * Second Step: all features, difficult task, participants together
         * Third Step: all features, easy task, participants divided
         * Fourth Step: all features, easy task, participants together
         */
        
        performAnalysis(participantsList, false, false);
        performAnalysis(participantsList, false, true);
        performAnalysis(participantsList, true, false);
        performAnalysis(participantsList, true, true);
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
        
        if (!easyTask) {
            StressValuesAnalyzer.analyzeStressValues(participantsList, allTogether);
        }
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
