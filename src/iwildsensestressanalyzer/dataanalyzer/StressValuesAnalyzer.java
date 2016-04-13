package iwildsensestressanalyzer.dataanalyzer;

import static iwildsensestressanalyzer.dataanalyzer.EventsAnalyzer.addTTestResultsToFinalContainer;
import static iwildsensestressanalyzer.dataanalyzer.EventsAnalyzer.printTitleMessage;
import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 * Evaluates statistical significance of stress values of the days and hours
 * @author Matteo Ciman
 */
public class StressValuesAnalyzer extends EventsAnalyzer {
    
    private static final ArrayList<String> LABELS_STRESS_HOURS = new ArrayList<>(),
            LABELS_STRESS_DAYS = new ArrayList<>();
    
    static {
        LABELS_STRESS_HOURS.add("M"); LABELS_STRESS_HOURS.add("A");
        LABELS_STRESS_HOURS.add("E");
        
        LABELS_STRESS_DAYS.add("M"); LABELS_STRESS_DAYS.add("T");
        LABELS_STRESS_DAYS.add("W"); LABELS_STRESS_DAYS.add("T");
        LABELS_STRESS_DAYS.add("F"); LABELS_STRESS_DAYS.add("S");
        LABELS_STRESS_DAYS.add("S");
    }
    
    public static void analyzeStressValues(ArrayList<Participant> participants, 
            boolean useAllTogether) {
        
        printTitleMessage("*** ANALYZING STRESS VALUES DATA ***");
        
        if (!useAllTogether) {
            
            ArrayList<Integer> validTTestsForStressValuesPerDay = 
                                new ArrayList<>(), 
                validTTestsForStressValuesPerHours = new ArrayList<>();
            
            ArrayList<Integer> tTestPassedForStressValuesPerDay = 
                                new ArrayList<>(),
                tTestPassedForStressValuesPerHours = new ArrayList<>();
            
            for (Participant participant: participants) {
                
                printTitleMessage("*** Participant " + participant.getIMEI() + 
                        " ***");
                
                ArrayList<Boolean> results = 
                        workWithStressValuesPerHours(participant);
                addTTestResultsToFinalContainer(tTestPassedForStressValuesPerHours, 
                        results, validTTestsForStressValuesPerHours);

                results = workWithStressValuesPerDays(participant);
                addTTestResultsToFinalContainer(tTestPassedForStressValuesPerDay, 
                        results, validTTestsForStressValuesPerDay);
            }
            
            /**
             * Analyzing percentage of success of a particular application
             * category
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForStressValuesPerHours, 
                    validTTestsForStressValuesPerHours, LABELS_STRESS_HOURS, 
                    "*** Percentage of success of stress values divided "
                            + "by day ranges ***");

            /**
             * Analyzing percentage of success of the timing influence 
             * of a particular application category
             */
            performStepsForPrintingPercentageOfSuccess(tTestPassedForStressValuesPerDay, 
                    validTTestsForStressValuesPerDay, LABELS_STRESS_DAYS, 
                    "*** Percentage of success of stress values divided"
                            + " by days ***");
            
        }
        else {
            workWithStressValuesPerHoursForAllParticipants();
            workWithStressValuesPerDaysForAllParticipants();
        }
    }
    
    private static ArrayList<Boolean> workWithStressValuesPerHours(Participant participant) {
        
        printTitleMessage("*** Stress values per hours ***");
            
        ArrayList<ArrayList<Double>> listValues = participant.getListStressValuesPerHours();
                
        return EventsAnalyzer.printTTestResults(listValues, LABELS_STRESS_HOURS);
    }
    
    private static ArrayList<Boolean> workWithStressValuesPerHoursForAllParticipants() {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Stress values per hours ***");
        
        ArrayList<ArrayList<Double>> listValues = Participant.getStressValuesPerHoursForAllParticipants();
        
        return printTTestResults(listValues, LABELS_STRESS_HOURS);
    }
    
    private static ArrayList<Boolean> workWithStressValuesPerDays(Participant participant) {
        
        printTitleMessage("*** Stress values per days ***");
        
        ArrayList<ArrayList<Double>> listValues = participant.getListStressValuesPerDays();
        
        return EventsAnalyzer.printTTestResults(listValues, LABELS_STRESS_DAYS);
    }
    
    private static ArrayList<Boolean> workWithStressValuesPerDaysForAllParticipants() {
        
        printTitleMessage("*** GLOBAL ANALYSIS: Stress values per days ***");
        
        ArrayList<ArrayList<Double>> listValues = Participant.getStressValuesPerDaysForAllParticipants();
        
        return printTTestResults(listValues, LABELS_STRESS_DAYS);
    }
}
