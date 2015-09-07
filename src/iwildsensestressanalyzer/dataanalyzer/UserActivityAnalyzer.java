package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserActivityAnalyzer {
    
    public static void analyzeUserActivityDataForEachParticipant(ArrayList<Participant>
            participants) {
        
        for (Participant participant: participants) {
            
            SurveyDataWrapper[] wrappers = participant.getSurveyDataWrappers();
            
        }
        
    }
    
    private static void workWithPointsSumForActivities() {
        
        
        
        
    }
}
