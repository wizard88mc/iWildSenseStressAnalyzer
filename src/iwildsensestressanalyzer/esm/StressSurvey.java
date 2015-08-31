package iwildsensestressanalyzer.esm;

import iwildsensestressanalyzer.useractivity.UserActivityAnalyzer;
import iwildsensestressanalyzer.useractivity.UserActivityEvent;
import iwildsensestressanalyzer.userpresenceevent.ScreenOnOff;
import iwildsensestressanalyzer.userpresenceevent.UnlockedScreen;
import iwildsensestressanalyzer.userpresenceevent.UserPresenceAdvancedEventsWrapper;
import java.util.ArrayList;
import java.util.Calendar;

/**
 *
 * This class holds the answers provided by the participant to the ESM 
 * survey about Valence, Energy and Stress among the day
 * 
 * @author Matteo Ciman
 * @version 0.1: First draft of the class with the main constructor
 */
public class StressSurvey {
    
    private static final int TOTAL_HOURS_VALIDITY = -3;
    
    private final long timestamp;
    private final int valence;
    private final int energy;
    private final int stress;
    
    private UserPresenceAdvancedEventsWrapper userPresenceAdvancedEventsWrapper;
    private UserActivityAnalyzer userActivityAnalyzer;
    
    public StressSurvey(String lineWithAnswer) {
        
        String[] answerElements = lineWithAnswer.split(",");
        /**
         * [0]: not necessary timestamp
         * [1]: timestamp of the answer 
         * [2]: not necessary timestamp
         * [3]: stress_survey
         * [4]: answer provided: energy-valence-stress
         */
        timestamp = Long.valueOf(answerElements[1]);
        
        String[] elementsSurvey = answerElements[4].split("-");
        /**
         * [0]: energy
         * [1]: valence
         * [2]: stress
         */
        valence = Integer.valueOf(elementsSurvey[0]);
        energy = Integer.valueOf(elementsSurvey[1]);
        stress = Integer.valueOf(elementsSurvey[2]);
    }
    
    /**
     * Returns the timestamp when the answer is provided
     * @return the timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }
    
    /**
     * Returns the energy answer
     * @return the energy answer
     */
    public int getEnergy() {
        return this.energy;
    }
    
    /**
     * Returns the valence answer
     * @return the valence answer
     */
    public int getValence() {
        return this.valence;
    }
    
    /**
     * Returns the stress answer
     * @return the stress answer
     */
    public int getStress() {
        return this.stress;
    }
    
    /**
     * Adds to the survey all the Screen events that belongs to the survey. This
     * method creates a ScreenEventsAnalyzer that will hold only valid Events 
     * for the survey
     * @param eventsWrapper the wrapper with all the Screen events for the 
     * participant
     */
    public void addScreenEvents(UserPresenceAdvancedEventsWrapper eventsWrapper) {
        
        ArrayList<ScreenOnOff> screenOnOffEvents = new ArrayList<ScreenOnOff>();
        ArrayList<UnlockedScreen> unlockedScreenEvents = new ArrayList<UnlockedScreen>();
        
        for (ScreenOnOff screenEvent: eventsWrapper.getScreenOnOffEvents()) {
            
            if (isEventStartedInsideSurveyValidityTime(this.timestamp, 
                    screenEvent.getOnTimestamp())) {
                screenOnOffEvents.add(screenEvent);
            }
        }
        
        for (UnlockedScreen screenEvent: eventsWrapper.getUnlockedScreenEvents()) {
            
            if (isEventStartedInsideSurveyValidityTime(this.timestamp, screenEvent.getOnTimestamp())) {
                unlockedScreenEvents.add(screenEvent);
            }
        }
        
        eventsWrapper = new UserPresenceAdvancedEventsWrapper(screenOnOffEvents, 
                unlockedScreenEvents);
    }
    
    /**
     * Adds to the Survey all the UserActivityEvent(s) that belongs to the time
     * range of the survey
     * 
     * @param events a list of UserActivtyEvent 
     */
    public void addUserActivityEvents(ArrayList<UserActivityEvent> events) {
        
        userActivityAnalyzer = new UserActivityAnalyzer(this, events);
    }
    
    /**
     * Returns the screen events wrapper
     * @return the UserPresenceAdvancedEventsWrapper object
     */
    public UserPresenceAdvancedEventsWrapper getUserPresenceAdvancedEventsWrapper() {
        return this.userPresenceAdvancedEventsWrapper;
    }
    
    /**
     * Returns if the considered timestamp of one event is inside the range
     * of validity of the current survey, that is the time it is answered
     * minus 3 hours that is the distance between each survey
     * 
     * @param surveyTimestamp the timestamp of the survey
     * @param eventTimestamp the timestamp of the considered event
     * @return true if the event is inside the validity time of the survey, 
     * false otherwise
     */
    public static boolean isEventInsideSurveyTiming(long surveyTimestamp, 
            long eventTimestamp) {
        
        Calendar surveyDate = Calendar.getInstance(), 
                startValidity = Calendar.getInstance(), 
                event = Calendar.getInstance();
        surveyDate.setTimeInMillis(surveyTimestamp);
        startValidity.setTimeInMillis(surveyTimestamp);
        event.setTimeInMillis(eventTimestamp);
        
        startValidity.add(Calendar.HOUR, TOTAL_HOURS_VALIDITY);
        
        return (event.after(startValidity) && event.before(surveyDate));
    }
    
    /**
     * Returns if the considered event is started (but not necessarily finished)
     * during the validity time of the survey
     * @param surveyTimestamp the timestamp of the survey
     * @param eventTimestamp the timestamp of the event
     * @return if the event started after the validity start time of the survey
     */
    public static boolean isEventStartedInsideSurveyValidityTime(long surveyTimestamp, 
            long eventTimestamp) {
        
        Calendar surveyStartValidity = Calendar.getInstance(),
                eventTime = Calendar.getInstance();
        
        surveyStartValidity.setTimeInMillis(surveyTimestamp);
        surveyStartValidity.add(Calendar.HOUR, TOTAL_HOURS_VALIDITY);
        
        eventTime.setTimeInMillis(eventTimestamp);
        
        return eventTime.after(surveyStartValidity);
    }
}
