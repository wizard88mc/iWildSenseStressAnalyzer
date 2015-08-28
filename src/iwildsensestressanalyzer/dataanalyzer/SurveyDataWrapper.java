package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.userpresenceevent.ScreenEventsFeaturesExtractor;
import iwildsensestressanalyzer.userpresenceevent.ScreenOnOff;
import iwildsensestressanalyzer.userpresenceevent.UnlockedScreen;
import java.util.ArrayList;

/**
 *
 * Used for a single participant to connect together survey data answer (from 
 * 1 to 5) to wrappers that analyze data about the considered events
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class SurveyDataWrapper {
    
    private final int surveyStressValue;
    private ScreenEventsFeaturesExtractor screenEventAnalyzer;
    
    public SurveyDataWrapper(int surveyStressValue) {
        this.surveyStressValue = surveyStressValue;
    }
    
    /**
     * Takes all the surveys that have stress value = to the stress value of the
     * SurveyDataWrapper and creates the ScreenEventsAnalyzer using only
     * screen events related to the correct stress value
     * @param surveys all the surveys answered by the participant
     */
    public void createScreenEventAnalyzer(ArrayList<StressSurvey> surveys) {
        
        ArrayList<ScreenOnOff> screenOnOffList = new ArrayList<ScreenOnOff>(); 
        ArrayList<UnlockedScreen> unlockedScreenList = 
                new ArrayList<UnlockedScreen>();
        
        for (StressSurvey survey: surveys) {
            
            if (survey.getStress() == surveyStressValue) {
                
                screenOnOffList.addAll(survey.getUserPresenceAdvancedEventsWrapper()
                    .getScreenOnOffEvents());
                
                unlockedScreenList.addAll(survey.getUserPresenceAdvancedEventsWrapper()
                    .getUnlockedScreenEvents());
            }
        }
        
        screenEventAnalyzer = new ScreenEventsFeaturesExtractor(screenOnOffList, 
                unlockedScreenList);
    }
    
    /**
     * Returns the Screen Events analyzer
     * @return the ScreenEventsAnalyzer object for the stress survey value
     */
    public ScreenEventsFeaturesExtractor getScreenEventsAnalyzer() {
        return this.screenEventAnalyzer;
    }
}
