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
                        workWithPointsSumOfActivities(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForPointsSumOfActivities, 
                        returnedResults, validTTestsForPointsSumOfActivities);
                
                returnedResults = workWithPercentageOfWorkload(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForPercentageOfWorkload, 
                        returnedResults, validTTestsForPercentageOfWorkload);
                
                returnedResults = 
                        workWithInfluenceOfWalkingActivityOnTotal(wrappers, 
                                easyJob);
                addTTestResultsToFinalContainer(tTestPassedForInfluenceOfWalkingActivityOnTotal, 
                        returnedResults, 
                        validTTestsForInfluenceOfWalkingActivityOnTotal);
                
                returnedResults = 
                        workWithInfluenceOfRunningActivityOnTotal(wrappers, 
                                easyJob);
                addTTestResultsToFinalContainer(tTestPassedForInfluenceOfRunningActivityOnTotal, 
                        returnedResults, 
                        validTTestsForInfluenceOfRunningActivityOnTotal);
                
                returnedResults = 
                        workWithInfluenceOfOnBicycleOnTotal(wrappers, easyJob);
                addTTestResultsToFinalContainer(
                        tTestPassedForInfluenceOfOnBicycleActivityOnTotal, 
                        returnedResults, 
                        validTTestsForInfluenceOfOnBicycleActivityOnTotal);
                
                returnedResults = 
                        workWithPercentageOfTiltingEvents(wrappers, easyJob);
                addTTestResultsToFinalContainer(tTestPassedForPercentageOfTiltingEvents, 
                        returnedResults, validTTestsForPercentageOfTiltingEvents);
                
                returnedResults = workWithPercentageOfInVehicleEvents(wrappers, 
                        easyJob);
                addTTestResultsToFinalContainer(tTestPassedForPercentageOfInVehicleEvents, 
                        returnedResults, validTTestsForPercentageOfInVehicleEvents);
            }
            
            /**
             * Analyzing percentage of success of sum of the points collected
             * with activities feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForPointsSumOfActivities, 
                    validTTestsForPointsSumOfActivities, easyJob, 
                    "*** Percentage of success for Sum of the points collected "
                            + "with activities feature ***");
            
            /**
             * Analyzing percentage of success of percentage of workload feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForPercentageOfWorkload, 
                    validTTestsForPercentageOfWorkload, easyJob, 
                    "*** Percentage of success of percentage of workload "
                            + "feature ***");
            
            /**
             * Analyzing percentage of success of influence of WALKING activity
             * on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForInfluenceOfWalkingActivityOnTotal, 
                    validTTestsForInfluenceOfWalkingActivityOnTotal, easyJob, 
                    "*** Percentage of success of influence of WALKING activity"
                            + " on total feature ***");
            
            /**
             * Analyzing percentage of success of influence of RUNNING activity
             * on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForInfluenceOfRunningActivityOnTotal, 
                    validTTestsForInfluenceOfRunningActivityOnTotal, easyJob, 
                    "*** Percentage of success of influence of RUNNING activity"
                            + " on total feature ***");
            
            /**
             * Analyzing percentage of success of influence of on bicycle 
             * activity on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForInfluenceOfOnBicycleActivityOnTotal, 
                    validTTestsForInfluenceOfOnBicycleActivityOnTotal, easyJob, 
                    "*** Percentage of success of Influence of ON_BICYCLE "
                            + "activity on total activity performed feature ***");
            
            /**
             * Analyzing percentage of success of percentage of tilting events
             * on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForPercentageOfTiltingEvents, 
                    validTTestsForPercentageOfTiltingEvents, easyJob, 
                    "*** Percentage of success of Percentage of tilting events"
                            + " feature ***");
            
            /**
             * Analyzing percentage of success percentage of IN_VEHICLE events
             * on total feature
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForPercentageOfInVehicleEvents, 
                    validTTestsForPercentageOfInVehicleEvents, easyJob, 
                    "*** Percentage of success of Percentage of IN_VEHICLE "
                            + "events feature ***");
            
        }
        else {
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithPointsSumOfActivitiesOfAllParticipants(listWrappers, easyJob);
            workWithPercentageOfWorkloadOfAllParticipants(listWrappers, easyJob);
            workWithInfluenceOfWalkingActivityOnTotalOfAllParticipants(listWrappers, 
                    easyJob);
            workWithInfluenceOfRunningActivityOnTotalOfAllParticipants(listWrappers, 
                    easyJob);
            workWithInfluenceOfOnBicycleOnTotalOfAllParticipants(listWrappers, 
                    easyJob);
            workWithPercentageOfTiltingEventsOfAllParticipants(listWrappers, 
                    easyJob);
            workWithPercentageOfInVehicleEventsOfAllParticipants(listWrappers, 
                    easyJob);
        }
    }
    
    /**
     * Work with Points collected with the activities performed during 
     * the time validity of the survey
     * @param surveyDataWrappers the data wrappers
     * @param easyJob true if it easy the easy task, false otherwise
     * @return a list of passed/not passed test
     */
    private static ArrayList<Boolean> workWithPointsSumOfActivities(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Sum of the points collected with activities ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper surveyDataWrapper: surveyDataWrappers) {
            
            listValues.add(surveyDataWrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPointsSumOfActivities());
        }
        
        return printTTestResults(listValues, easyJob);   
    }
    
    /**
     * Works with Points collected with the performed activities using data 
     * of all the participants
     * @param listWrappers a list with all the wrappers and all the answers 
     * provided by all the participants
     * @param easyJob true if it easy the easy task, false otherwise 
     */
    private static void workWithPointsSumOfActivitiesOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
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
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the Percentage of workload performed for the time of the 
     * survey validity
     * @param surveyDataWrappers the data wrappers
     * @param easyJob true if it easy the easy task, false otherwise
     * @return a list of passed/not passed tests
     */
    private static ArrayList<Boolean> workWithPercentageOfWorkload(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Percentage of workload ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentageOfWorkload());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the percentage of workload using data of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithPercentageOfWorkloadOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
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
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the influence of the walking activity on the total activities
     * performed
     * @param surveyDataWrappers the data wrappers
     * @return a list of passed/not passed test
     */
    private static ArrayList<Boolean> workWithInfluenceOfWalkingActivityOnTotal(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Influence of Walking Activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfWalkingActivityOnTotal());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the influence of walking activity on the total with data
     * of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithInfluenceOfWalkingActivityOnTotalOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
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
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the influence of the running activity on the total activities
     * performed
     * @param surveyDataWrappers the data wrappers
     * @param easyJob true if it easy the easy task, false otherwise
     * @return a list of passed/not passed tests
     */
    private static ArrayList<Boolean> workWithInfluenceOfRunningActivityOnTotal(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Influence of Running Activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfRunningActivityOnTotal());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with influence of running activity on the total performed with data
     * of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithInfluenceOfRunningActivityOnTotalOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
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
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the influence of the bicycle activity on the total number of
     * performed activities
     * @param surveyDataWrappers the data wrappers
     * @param easyJob true if it easy the easy task, false otherwise
     * @return a list of passed/not passed tests results
     */
    private static ArrayList<Boolean> workWithInfluenceOfOnBicycleOnTotal(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Influence of On Bicycle activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfOnBicycleActivityOnTotal());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with influence of bicycle activity on the total activity performed
     * @param listWrappers a list with all the wrappers of all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithInfluenceOfOnBicycleOnTotalOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
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
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the percentage of TILTING events on the total number of 
     * performed activities
     * @param surveyDataWrappers the data wrappers
     * @param easyJob true if it easy the easy task, false otherwise
     * @return a list of passed/not passed tests
     */
    private static ArrayList<Boolean> workWithPercentageOfTiltingEvents(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Percentage of Titlting events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentagesOfTiltingEvents());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the percentage of TILTING events of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithPercentageOfTiltingEventsOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
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
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the percentage of IN_VEHICLE events on the total number of 
     * performed activities
     * @param surveyDataWrappers 
     * @return a list of passed/not passed tests results
     */
    private static ArrayList<Boolean> workWithPercentageOfInVehicleEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Percentage of IN_VEHICLE events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentagesOfInVehicleEvents());
        }
        
        return printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the percentage of IN_VEHICLE events of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithPercentageOfInVehicleEventsOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
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
        printTTestResults(listValues, easyJob);
    }
}
