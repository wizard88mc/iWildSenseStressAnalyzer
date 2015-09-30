package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceLightAnalyzer extends EventsAnalyzer {
    
    public static void analyzeUserPresenceLightDataForEachParticipant(
            ArrayList<Participant> participants, boolean easyJob) {
        
        for (Participant participant: participants) {
            
            SurveyDataWrapper[] wrappers;
            if (!easyJob) {
                wrappers = participant.getSurveyDataWrappers();
            }
            else {
                wrappers = participant.getEasySurveyDataWrappers();
            }
            
            workWithLightValueOfScreenOnOffEvents(wrappers, easyJob);
            workWithLightValueOfUnlockedScreenEvents(wrappers, easyJob);
            workWithLightValueForAllScreenEvents(wrappers, easyJob);
        }
    }
    
    private static void workWithLightValueOfScreenOnOffEvents(SurveyDataWrapper[] 
            wrappers, boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Light value for ScreenOnOff events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor().getAllLightValuesForScreenOnOffEvents());
        }
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(values, 0.0, 1.0);
        
        printTTestResults(normalizedValues, easyJob);
    }
    
    private static void workWithLightValueOfUnlockedScreenEvents(SurveyDataWrapper[] 
            wrappers, boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Light value for UnlockedScreen events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor().getAllLightValuesForUnlockedScreenEvents());
        }
        
        printTTestResults(MathUtils.normalizeSetOfDoubleData(values, 0.0, 1.0), easyJob);
    }
    
    private static void workWithLightValueForAllScreenEvents(SurveyDataWrapper[]
            wrappers, boolean easyJob) {
        
        System.out.println();
        System.out.println("*** Light value for ALL screen events ***");
        
        ArrayList<ArrayList<Double>> values = new ArrayList<ArrayList<Double>>();
        
        for (SurveyDataWrapper wrapper: wrappers) {
            values.add(wrapper.getUserPresenceLightFeaturesExtractor().getAllLightValuesForAllScreenEvents());
        }
        
        printTTestResults(MathUtils.normalizeSetOfDoubleData(values, 0.0, 1.0), easyJob);
        
    }    
}
