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
        
        if (!allTogether) {
            for (Participant participant: participants) {

                SurveyDataWrapper[] wrappers;
                if (!easyJob) {
                    wrappers = participant.getSurveyDataWrappers();
                }
                else {
                    wrappers = participant.getEasySurveyDataWrappers();
                }

                workWithPointsSumOfActivities(wrappers, easyJob);
                workWithPercentageOfWorkload(wrappers, easyJob);
                workWithInfluenceOfWalkingActivityOnTotal(wrappers, easyJob);
                workWithInfluenceOfRunningActivityOnTotal(wrappers, easyJob);
                workWithInfluenceOfOnBicycleOnTotal(wrappers, easyJob);
                workWithPercentageOfTiltingEvents(wrappers, easyJob);
                workWithPercentageOfInVehicleEvents(wrappers, easyJob);
            }
        }
        else {
            ArrayList<ArrayList<SurveyDataWrapper>> listWrappers = 
                    prepareDataWrappersForAllParticipants(participants, easyJob);
            
            workWithPointsSumOfActivitiesOfAllParticipants(listWrappers, easyJob);
            workWithPercentageOfWorkloadOfAllParticipants(listWrappers, easyJob);
            workWithInfluenceOfWalkingActivityOnTotalOfAllParticipants(listWrappers, easyJob);
            workWithInfluenceOfRunningActivityOnTotalOfAllParticipants(listWrappers, easyJob);
            workWithInfluenceOfOnBicycleOnTotalOfAllParticipants(listWrappers, easyJob);
            workWithPercentageOfTiltingEventsOfAllParticipants(listWrappers, easyJob);
            workWithPercentageOfInVehicleEventsOfAllParticipants(listWrappers, easyJob);
        }
    }
    
    /**
     * Work with Points collected with the activities performed during 
     * the time validity of the survey
     * @param surveyDataWrappers the data wrappers
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithPointsSumOfActivities(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Sum of the points collected with activities ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper surveyDataWrapper: surveyDataWrappers) {
            
            listValues.add(surveyDataWrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPointsSumOfActivities());
        }
        
        printTTestResults(listValues, easyJob);   
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
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            
            ArrayList<Double> singleValues = new ArrayList<Double>();
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
     */
    private static void workWithPercentageOfWorkload(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Percentage of workload ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentageOfWorkload());
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the percentage of workload using data of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithPercentageOfWorkloadOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Percentage of workload ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
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
     */
    private static void workWithInfluenceOfWalkingActivityOnTotal(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Influence of Walking Activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfWalkingActivityOnTotal());
        }
        
        printTTestResults(listValues, easyJob);
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
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
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
     */
    private static void workWithInfluenceOfRunningActivityOnTotal(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Influence of Running Activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfRunningActivityOnTotal());
        }
        
        printTTestResults(listValues, easyJob);
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
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
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
     */
    private static void workWithInfluenceOfOnBicycleOnTotal(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Influence of On Bicycle activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllInfluenceOfOnBicycleActivityOnTotal());
        }
        
        printTTestResults(listValues, easyJob);
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
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
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
     */
    private static void workWithPercentageOfTiltingEvents(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Percentage of Titlting events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentagesOfTiltingEvents());
        }
        
        printTTestResults(listValues, easyJob);
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
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
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
     */
    private static void workWithPercentageOfInVehicleEvents(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Percentage of IN_VEHICLE events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentagesOfInVehicleEvents());
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the percentage of IN_VEHICLE events of all the participants
     * @param listWrappers a list with all the wrappers of all the participants
     * @param easyJob true if it easy the easy task, false otherwise
     */
    private static void workWithPercentageOfInVehicleEventsOfAllParticipants(
        ArrayList<ArrayList<SurveyDataWrapper>> listWrappers, boolean easyJob) {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Percentage of IN_VEHICLE events");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        for (ArrayList<SurveyDataWrapper> wrappers: listWrappers) {
            ArrayList<Double> singleValues = new ArrayList<Double>();
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
