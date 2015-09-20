package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.touches.TouchesBufferedFeaturesExtractor;
import iwildsensestressanalyzer.useractivity.UserActivityFeaturesExtractor;
import iwildsensestressanalyzer.useractivity.UserActivityFeaturesExtractorsListWrapper;
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
    private final boolean easy;
    private ScreenEventsFeaturesExtractor screenEventsFeaturesExtractor = null;
    private UserActivityFeaturesExtractorsListWrapper userActivityFeaturesExtractorsListWrapper = null;
    private TouchesBufferedFeaturesExtractor touchesBufferedFeaturesExtractor = null;
    
    public SurveyDataWrapper(int surveyStressValue, boolean easy) {
        this.surveyStressValue = surveyStressValue;
        this.easy = easy;
    }
    
    /**
     * Takes all the surveys that have stress value = to the stress value of the
     * SurveyDataWrapper and creates the ScreenEventsAnalyzer using only
     * screen events related to the correct stress value
     * @param surveys all the surveys answered by the participant
     */
    public void createScreenEventsFeaturesExtractor(ArrayList<StressSurvey> surveys) {
        
        ArrayList<ScreenOnOff> screenOnOffList = new ArrayList<ScreenOnOff>(); 
        ArrayList<UnlockedScreen> unlockedScreenList = 
                new ArrayList<UnlockedScreen>();
        
        for (StressSurvey survey: surveys) {
            
            if (checkIfCorrectForTheDataWrapper(survey.getStress())) {
                
                screenOnOffList.addAll(survey.getUserPresenceAdvancedEventsWrapper()
                    .getScreenOnOffEvents());
                
                unlockedScreenList.addAll(survey.getUserPresenceAdvancedEventsWrapper()
                    .getUnlockedScreenEvents());
            }
        }
        
        screenEventsFeaturesExtractor = new ScreenEventsFeaturesExtractor(screenOnOffList, 
                unlockedScreenList);
    }
    
    /**
     * Takes all the surveys that have stress vale equal to the stress value of 
     * the SurveyDataWrapper and creates the UserActivityEventsFeaturesExtractor
     * using only events related to the correct stress value
     * @param surveys all the surveys answered by the participant
     */
    public void createUserActivityFeaturesExtractor(ArrayList<StressSurvey> surveys) {
        
        ArrayList<UserActivityFeaturesExtractor> userActivityFeaturesExtractorList = 
                new ArrayList<UserActivityFeaturesExtractor>();
        
        for (StressSurvey survey: surveys) {
            
            if (checkIfCorrectForTheDataWrapper(survey.getStress())) {
                
                userActivityFeaturesExtractorList.add(survey.getUserActivityFeaturesExtractor());
            }
        }
        
        userActivityFeaturesExtractorsListWrapper = 
                new UserActivityFeaturesExtractorsListWrapper(userActivityFeaturesExtractorList);
    }
    
    /**
     * Creates the TouchesBufferedFeatureExtractor object using the UnlockedScreen
     * events stored in the ScreenEventsFeaturesExtractor
     */
    public void createTouchesBufferedFeatureExtractor() {
        
        this.touchesBufferedFeaturesExtractor = 
                new TouchesBufferedFeaturesExtractor(screenEventsFeaturesExtractor.getUnlockedScreenEvents());
        
    }
    
    /**
     * Returns the Screen Events analyzer
     * @return the ScreenEventsAnalyzer object for the stress survey value
     */
    public ScreenEventsFeaturesExtractor getScreenEventsFeaturesExtractor() {
        return this.screenEventsFeaturesExtractor;
    }
    
    /**
     * Returns the Wrapper of the list of the UserActivityFeaturesExtractor. 
     * In this way, with the returned object, it is possible to retrieve all the values
     * for future analysis
     * @return 
     */
    public UserActivityFeaturesExtractorsListWrapper getUserActivityFeaturesExtractorsListWrapper() {
        return this.userActivityFeaturesExtractorsListWrapper;
    }
    
    public TouchesBufferedFeaturesExtractor getTouchesBufferedFeaturesExtractor() {
        return this.touchesBufferedFeaturesExtractor;
    }
    
    /**
     * Checks if the value of the considered 
     * @param stressValue the stress value of the survey
     * @return if the considered survey has to be used to retrieve data
     */
    private boolean checkIfCorrectForTheDataWrapper(int stressValue) {
        
        return !easy && stressValue == surveyStressValue || 
                    (easy && ((surveyStressValue == 1 && (stressValue == 1 || stressValue == 2))
                        || (surveyStressValue == 3 && stressValue == 3) 
                        || (surveyStressValue == 5 && (stressValue == 4 || stressValue == 5))));
        
    }
}
