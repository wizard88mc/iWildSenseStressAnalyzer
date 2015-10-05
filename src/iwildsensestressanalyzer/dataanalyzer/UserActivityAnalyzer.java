package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserActivityAnalyzer extends EventsAnalyzer {
    
    public static void analyzeUserActivityDataForEachParticipant(ArrayList<Participant>
            participants, boolean easyJob) {
        
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
    
    /**
     * Work with Points collected with the activities performed during 
     * the time validity of the survey
     * @param surveyDataWrappers the data wrappers
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
     * Works with the Percentage of workload performed for the time of the 
     * survey validity
     * @param surveyDataWrappers the data wrappers
     */
    private static void workWithPercentageOfWorkload(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Percentage of working load ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper()
                    .getAllPercentageOfWorkload());
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
     * Works with the influence of the running activity on the total activities
     * performed
     * @param surveyDataWrappers the data wrappers
     */
    private static void workWithInfluenceOfRunningActivityOnTotal(SurveyDataWrapper[] 
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Influence of Running Activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper().getAllInfluenceOfRunningActivityOnTotal());
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the influence of the bicycle activity on the total number of
     * performed activities
     * @param surveyDataWrappers the data wrappers
     */
    private static void workWithInfluenceOfOnBicycleOnTotal(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Influence of On Bicycle activity on Total ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper().getAllInfluenceOfOnBicycleActivityOnTotal());
        }
        
        printTTestResults(listValues, easyJob);
    }
    
    /**
     * Works with the percentage of TILTING events on the total number of 
     * performed activities
     * @param surveyDataWrappers the data wrappers
     */
    private static void workWithPercentageOfTiltingEvents(SurveyDataWrapper[]
            surveyDataWrappers, boolean easyJob) {
        
        printTitleMessage("*** Percentage of Titlting events ***");
        
        ArrayList<ArrayList<Double>> listValues = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: surveyDataWrappers) {
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper().getAllPercentagesOfTiltingEvents());
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
            listValues.add(wrapper.getUserActivityFeaturesExtractorsListWrapper().getAllPercentagesOfInVehicleEvents());
        }
        
        printTTestResults(listValues, easyJob);
    }
}
