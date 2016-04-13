package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserActivityAnalyzer extends EventsAnalyzer {
    
    public static void analyzeUserActivityDataForEachParticipant(ArrayList<Participant>
            participants, boolean easyJob, boolean allTogether) {
        
        ArrayList<String> labels = EventsAnalyzer.labels5LabelsTaks;
        if (easyJob) {
            labels = EventsAnalyzer.labels3LabelsTask;
        }
        
        printTitleMessage("*** ANALYZING USER ACTIVITY FEATUERS ***");
        
        if (!allTogether) {
            
            ArrayList<Integer> tTestPassedForPointsSumOfActivities = 
                        new ArrayList<>(), 
                    tTestPassedForPercentageOfWorkload = 
                        new ArrayList<>(),
                    tTestPassedForInfluenceOfWalkingActivityOnTotal = 
                        new ArrayList<>(),
                    tTestPassedForInfluenceOfRunningActivityOnTotal = 
                        new ArrayList<>(), 
                    tTestPassedForInfluenceOfOnBicycleActivityOnTotal = 
                        new ArrayList<>(), 
                    tTestPassedForPercentageOfTiltingEvents = 
                        new ArrayList<>(), 
                    tTestPassedForPercentageOfInVehicleEvents = 
                        new ArrayList<>();
            
            ArrayList<Integer> validTTestsForPointsSumOfActivities = 
                        new ArrayList<>(), 
                    validTTestsForPercentageOfWorkload = new ArrayList<>(), 
                    validTTestsForInfluenceOfWalkingActivityOnTotal = 
                        new ArrayList<>(), 
                    validTTestsForInfluenceOfRunningActivityOnTotal = 
                        new ArrayList<>(), 
                    validTTestsForInfluenceOfOnBicycleActivityOnTotal = 
                        new ArrayList<>(), 
                    validTTestsForPercentageOfTiltingEvents = new ArrayList<>(), 
                    validTTestsForPercentageOfInVehicleEvents = 
                        new ArrayList<>();
                    
            
            for (Participant participant: participants) {
                
                printTitleMessage("*** Participant " + participant.getIMEI() + 
                        " ***");

                SurveyDataWrapper[] wrappers;
                if (!easyJob) {
                    wrappers = participant.getSurveyDataWrappers();
                }
                else {
                    wrappers = participant.getEasySurveyDataWrappers();
                }

                ArrayList<Boolean> returnedResults = 
                        workWithPointsSumOfActivities(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForPointsSumOfActivities, 
                        returnedResults, validTTestsForPointsSumOfActivities);
                
                returnedResults = workWithPercentageOfWorkload(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForPercentageOfWorkload, 
                        returnedResults, validTTestsForPercentageOfWorkload);
                
                returnedResults = 
                        workWithInfluenceOfWalkingActivityOnTotal(wrappers, 
                                labels);
                addTTestResultsToFinalContainer(tTestPassedForInfluenceOfWalkingActivityOnTotal, 
                        returnedResults, 
                        validTTestsForInfluenceOfWalkingActivityOnTotal);
                
                returnedResults = 
                        workWithInfluenceOfRunningActivityOnTotal(wrappers, 
                                labels);
                addTTestResultsToFinalContainer(tTestPassedForInfluenceOfRunningActivityOnTotal, 
                        returnedResults, 
                        validTTestsForInfluenceOfRunningActivityOnTotal);
                
                returnedResults = 
                        workWithInfluenceOfOnBicycleOnTotal(wrappers, labels);
                addTTestResultsToFinalContainer(
                        tTestPassedForInfluenceOfOnBicycleActivityOnTotal, 
                        returnedResults, 
                        validTTestsForInfluenceOfOnBicycleActivityOnTotal);
                
                returnedResults = 
                        workWithPercentageOfTiltingEvents(wrappers, labels);
                addTTestResultsToFinalContainer(tTestPassedForPercentageOfTiltingEvents, 
                        returnedResults, validTTestsForPercentageOfTiltingEvents);
                
                returnedResults = workWithPercentageOfInVehicleEvents(wrappers, 
                        labels);
                addTTestResultsToFinalContainer(tTestPassedForPercentageOfInVehicleEvents, 
                        returnedResults, validTTestsForPercentageOfInVehicleEvents);
            }
            
            /**
             * Analyzing percentage of success of sum of the points collected
             * with activities feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForPointsSumOfActivities, 
                    validTTestsForPointsSumOfActivities, labels, 
                    "*** Percentage of success for Sum of the points collected "
                            + "with activities feature ***");
            
            /**
             * Analyzing percentage of success of percentage of workload feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForPercentageOfWorkload, 
                    validTTestsForPercentageOfWorkload, labels, 
                    "*** Percentage of success of percentage of workload "
                            + "feature ***");
            
            /**
             * Analyzing percentage of success of influence of WALKING activity
             * on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForInfluenceOfWalkingActivityOnTotal, 
                    validTTestsForInfluenceOfWalkingActivityOnTotal, labels, 
                    "*** Percentage of success of influence of WALKING activity"
                            + " on total feature ***");
            
            /**
             * Analyzing percentage of success of influence of RUNNING activity
             * on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForInfluenceOfRunningActivityOnTotal, 
                    validTTestsForInfluenceOfRunningActivityOnTotal, labels, 
                    "*** Percentage of success of influence of RUNNING activity"
                            + " on total feature ***");
            
            /**
             * Analyzing percentage of success of influence of on bicycle 
             * activity on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForInfluenceOfOnBicycleActivityOnTotal, 
                    validTTestsForInfluenceOfOnBicycleActivityOnTotal, labels, 
                    "*** Percentage of success of Influence of ON_BICYCLE "
                            + "activity on total activity performed feature ***");
            
            /**
             * Analyzing percentage of success of percentage of tilting events
             * on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForPercentageOfTiltingEvents, 
                    validTTestsForPercentageOfTiltingEvents, labels, 
                    "*** Percentage of success of Percentage of tilting events"
                            + " feature ***");
            
            /**
             * Analyzing percentage of success percentage of IN_VEHICLE events
             * on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForPercentageOfInVehicleEvents, 
                    validTTestsForPercentageOfInVehicleEvents, labels, 
                    "*** Percentage of success of Percentage of IN_VEHICLE "
                            + "events feature ***");
            
        }
        else {
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithPointsSumOfActivitiesOfAllParticipants(listWrappers, labels);
            workWithPercentageOfWorkloadOfAllParticipants(listWrappers, labels);
            workWithInfluenceOfWalkingActivityOnTotalOfAllParticipants(listWrappers, 
                    labels);
            workWithInfluenceOfRunningActivityOnTotalOfAllParticipants(listWrappers, 
                    labels);
            workWithInfluenceOfOnBicycleOnTotalOfAllParticipants(listWrappers, 
                    labels);
            workWithPercentageOfTiltingEventsOfAllParticipants(listWrappers, 
                    labels);
            workWithPercentageOfInVehicleEventsOfAllParticipants(listWrappers, 
                    labels);
        }
    }
    
    /**
     * Work with Points collected with the activities performed during 
     * the time validity of the survey
     * @param surveyDataWrappers the data wrappers
     * @param labels list of labels for the output table
     * @return a list of passed/not passed test
     */
    private static ArrayList<Boolean> workWithPointsSumOfActivities(SurveyDataWrapper[] 
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Sum of the points collected with activities ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper surveyDataWrapper: surveyDataWrappers) {
            
            listValues.add(surveyDataWrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPointsSumOfActivities());
        }
        
        return printTTestResults(listValues, labels);   
    }
    
    /**
     * Works with Points collected with the performed activities using data 
     * of all the participants
     * @param listWrappers a list with all the wrappers and all the answers 
     * provided by all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithPointsSumOfActivitiesOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Sum of the points collected " +
                "with activities ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getUserActivityFeaturesExtractorsListWrapper()
                        .calculateStatisticsPointsSumOfActivities();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the Percentage of workload performed for the time of the 
     * survey validity
     * @param surveyDataWrappers the data wrappers
     * @param labels list of labels for the output table
     * @return a list of passed/not passed tests
     */
    private static ArrayList<Boolean> workWithPercentageOfWorkload(SurveyDataWrapper[] 
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Percentage of workload ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentageOfWorkload());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the percentage of workload using data of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithPercentageOfWorkloadOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Percentage of workload ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getUserActivityFeaturesExtractorsListWrapper()
                        .calculateStatisticsPercentageOfWorkload();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the influence of the walking activity on the total activities
     * performed
     * @param surveyDataWrappers the data wrappers
     * @param labels list of labels for the output table
     * @return a list of passed/not passed test
     */
    private static ArrayList<Boolean> workWithInfluenceOfWalkingActivityOnTotal(SurveyDataWrapper[] 
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Influence of Walking Activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfWalkingActivityOnTotal());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the influence of walking activity on the total with data
     * of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithInfluenceOfWalkingActivityOnTotalOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Influence of walking Activity" + 
                " on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            for (SurveyDataWrapper wrapper: wrappers) {
                
                Double[] values = wrapper.getUserActivityFeaturesExtractorsListWrapper()
                        .calculateStatisticsInfluenceOfWalkingActivityOnTotal();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the influence of the running activity on the total activities
     * performed
     * @param surveyDataWrappers the data wrappers
     * @param labels list of labels for the output table
     * @return a list of passed/not passed tests
     */
    private static ArrayList<Boolean> workWithInfluenceOfRunningActivityOnTotal(SurveyDataWrapper[] 
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Influence of Running Activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfRunningActivityOnTotal());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with influence of running activity on the total performed with data
     * of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithInfluenceOfRunningActivityOnTotalOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Influence of Running activity "
                + "on total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getUserActivityFeaturesExtractorsListWrapper()
                        .calculateStatisticsInfluenceOfRunningActivityOnTotal();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the influence of the bicycle activity on the total number of
     * performed activities
     * @param surveyDataWrappers the data wrappers
     * @param labels list of labels for the output table
     * @return a list of passed/not passed tests results
     */
    private static ArrayList<Boolean> workWithInfluenceOfOnBicycleOnTotal(SurveyDataWrapper[]
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Influence of On Bicycle activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfOnBicycleActivityOnTotal());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with influence of bicycle activity on the total activity performed
     * @param listWrappers a list with all the wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithInfluenceOfOnBicycleOnTotalOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Influence of On Bicycle activity"
                + " on total activity performed");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getUserActivityFeaturesExtractorsListWrapper()
                        .calculateStatisticsInfluenceOfBicycleActivityOnTotal();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the percentage of TILTING events on the total number of 
     * performed activities
     * @param surveyDataWrappers the data wrappers
     * @param labels list of labels for the output table
     * @return a list of passed/not passed tests
     */
    private static ArrayList<Boolean> workWithPercentageOfTiltingEvents(SurveyDataWrapper[]
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Percentage of Titlting events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentagesOfTiltingEvents());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the percentage of TILTING events of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithPercentageOfTiltingEventsOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Percentage of Tilting events "
                + "***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getUserActivityFeaturesExtractorsListWrapper()
                        .calculateStatisticsPercentageOfTiltingEvents();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the percentage of IN_VEHICLE events on the total number of 
     * performed activities
     * @param surveyDataWrappers 
     * @param labels list of labels for the output table
     * @return a list of passed/not passed tests results
     */
    private static ArrayList<Boolean> workWithPercentageOfInVehicleEvents(SurveyDataWrapper[] 
            surveyDataWrappers, ArrayList<String> labels) {
        
        printTitleMessage("*** Percentage of IN_VEHICLE events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentagesOfInVehicleEvents());
        }
        
        return printTTestResults(listValues, labels);
    }
    
    /**
     * Works with the percentage of IN_VEHICLE events of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param labels list of labels for the output table
     */
    private static void workWithPercentageOfInVehicleEventsOfAllParticipants(
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, 
            ArrayList<String> labels) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Percentage of IN_VEHICLE events");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<>();
            
            for (SurveyDataWrapper wrapper: wrappers) {
                Double[] values = wrapper.getUserActivityFeaturesExtractorsListWrapper()
                        .calculateStatisticsPercentageOfInVehicleEvents();
                if (values != null && values[0] != -1) {
                    singleValues.add(values[0]);
                }
            }
            listValues.add(singleValues);
        }
        printTTestResults(listValues, labels);
    }
}
