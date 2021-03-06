package iwildsensestressanalyzer.esm;

import iwildsensestressanalyzer.applicationsused.ApplicationUsed;
import iwildsensestressanalyzer.applicationsused.ApplicationUsedFeaturesExtractor;
import iwildsensestressanalyzer.applicationsused.ApplicationsUsedEvent;
import iwildsensestressanalyzer.light.UserPresenceLightEvent;
import iwildsensestressanalyzer.touches.TouchesBufferedEvent;
import iwildsensestressanalyzer.useractivity.UserActivityFeaturesExtractor;
import iwildsensestressanalyzer.useractivity.UserActivityEvent;
import iwildsensestressanalyzer.userpresenceevent.ScreenOnOff;
import iwildsensestressanalyzer.userpresenceevent.UnlockedScreen;
import iwildsensestressanalyzer.userpresenceevent.UserPresenceAdvancedEventsWrapper;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

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
    
    public final Integer MORNING_HOUR = 13, AFTERNOON_HOUR = 17, 
            EVENING_HOUR = 21;
    
    private final long timestamp;
    private final GregorianCalendar surveyDate;
    private final int valence;
    private final int energy;
    private final int stress;
    private final WeatherInfos weather;
    
    private UserPresenceAdvancedEventsWrapper userPresenceAdvancedEventsWrapper;
    /**
     * One UserActivityFeaturesExtractor different for each survey since we need
     * different values for every survey and not combine together all the values
     * (we have evaluations of time intervals, not value that can be used 
     * together)
     */
    private UserActivityFeaturesExtractor userActivityFeaturesExtractor; 
    
    private ApplicationUsedFeaturesExtractor applicationUsedFeaturesExtractor;
    
    public StressSurvey(String lineWithAnswer, 
            ArrayList<WeatherInfos> weatherInfos) {
        
        String[] answerElements = lineWithAnswer.split(",");
        /**
         * [0]: not necessary timestamp
         * [1]: timestamp of the answer 
         * [2]: not necessary timestamp
         * [3]: stress_survey
         * [4]: answer provided: energy-valence-stress
         */
        timestamp = Long.valueOf(answerElements[1]);
        surveyDate = new GregorianCalendar();
        surveyDate.setTimeInMillis(timestamp);
        
        String[] elementsSurvey = answerElements[4].split("-");
        /**
         * [0]: energy
         * [1]: valence
         * [2]: stress
         */
        valence = Integer.valueOf(elementsSurvey[0]);
        energy = Integer.valueOf(elementsSurvey[1]);
        stress = Integer.valueOf(elementsSurvey[2]);
        weather = WeatherInfos.getWeatherInfosForSurvey(timestamp, weatherInfos);
    }
    
    /**
     * Adds the TouchesBufferedEvent to the UnlockedScreen events
     * @param events the list of TouchesBufferedEvent recorded
     */
    public void addTouchesBufferedEvent(ArrayList<TouchesBufferedEvent> events) {
        userPresenceAdvancedEventsWrapper.addTouchesBufferedEvent(events);
    }
    
    /**
     * Adds the UserPresenceLightEvent to the Screen events (both ScreenOnOff 
     * or UnlockedScreen)
     * @param events the list of UserPresenceLightEvent recorded
     */
    public void addUserPresenceLightEvent(ArrayList<UserPresenceLightEvent> events) {
        userPresenceAdvancedEventsWrapper.addUserPresenceLightEvent(events);
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
     * Returns the weather associated with 
     * @return 
     */
    public WeatherInfos getWeather() {
        return this.weather;
    }
    
    /**
     * Returns if the survey was provided in the morning
     * @return true if survey is in the morning, false otherwise
     */
    public boolean isInTheMorning() {
        
        return surveyDate.get(Calendar.HOUR_OF_DAY) <= MORNING_HOUR;
    }
    
    /**
     * Returns if the survey was provided in the afternoon
     * @return true if survey is in the afternoon, false otherwise
     */
    public boolean isInTheAfternoon() {
        
        return !isInTheMorning() && 
                surveyDate.get(Calendar.HOUR_OF_DAY) <= AFTERNOON_HOUR;
    }
    
    public boolean isInTheEvening() {
        
        return !isInTheMorning() && !isInTheAfternoon();
    }
    
    public String dayMomentToString() {
        
        if (isInTheMorning()) {
            return "1";
        }
        else if (isInTheAfternoon()) {
            return "2";
        }
        else {
            return "3";
        }
    }
    
    public boolean isMonday() {
        
        return surveyDate.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY;
    }
    
    public boolean isTuesday() {
    
        return surveyDate.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY;
    }
    
    public boolean isWednesday() {
        
        return surveyDate.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY;
    }
    
    public boolean isThursday() {
        
        return surveyDate.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY;
    }
    
    public boolean isFriday() {
        
        return surveyDate.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY;
    }
    
    public boolean isSaturday() {
        
        return surveyDate.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY;
    }
    
    public boolean isSunday() {
        
        return surveyDate.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
    }
    
    public String dayToString() {
        
        if (isMonday()) {
            return "1";
        }
        else if (isTuesday()) {
            return "2";
        }
        else if (isWednesday()) {
            return "3";
        }
        else if (isThursday()) {
            return "4";
        }
        else if (isFriday()) {
            return "5";
        }
        else if (isSaturday()) {
            return "6";
        }
        else if (isSunday()) {
            return "7";
        }
        else return null;
    }
    
    /**
     * Adds to the survey all the Screen events that belongs to the survey. This
     * method creates a ScreenEventsAnalyzer that will hold only valid Events 
     * for the survey
     * @param eventsWrapper the wrapper with all the Screen events for the 
     * participant
     */
    public void addScreenEvents(UserPresenceAdvancedEventsWrapper eventsWrapper) {
        
        ArrayList<ScreenOnOff> screenOnOffEvents = new ArrayList<>();
        ArrayList<UnlockedScreen> unlockedScreenEvents = new ArrayList<>();
        
        for (ScreenOnOff screenEvent: eventsWrapper.getScreenOnOffEvents()) {
            
            if (isEventInsideSurveyTiming(this.timestamp, 
                    screenEvent.getOnTimestamp())) {
                screenOnOffEvents.add(screenEvent);
            }
        }
        
        for (UnlockedScreen screenEvent: eventsWrapper.getUnlockedScreenEvents()) {
            
            if (isEventInsideSurveyTiming(this.timestamp, 
                    screenEvent.getOnTimestamp())) {
                unlockedScreenEvents.add(screenEvent);
            }
        }
        
        this.userPresenceAdvancedEventsWrapper = 
            new UserPresenceAdvancedEventsWrapper(screenOnOffEvents, 
                unlockedScreenEvents);
    }
    
    /**
     * Adds to the Survey all the UserActivityEvent(s) that belongs to the time
     * range of the survey
     * 
     * @param events a list of UserActivtyEvent 
     */
    public void addUserActivityEvents(ArrayList<UserActivityEvent> events) {
        
        ArrayList<UserActivityEvent> correctEvents = new ArrayList<>();
        
        for (UserActivityEvent event: events) {
            if (isEventInsideSurveyTiming(this.timestamp, event.getTimestamp())) {
                correctEvents.add(event);
            }
        }
        
        userActivityFeaturesExtractor = new UserActivityFeaturesExtractor(correctEvents);
    }
    
    /**
     * Creates a a features extractor with the ApplicationsUsedEvent valid
     * for the particular survey
     * @param listOfEvents list of ApplicationsUsedEvent
     */
    public void addAppliactionUsedList(ArrayList<ApplicationsUsedEvent> listOfEvents) {
        
        ArrayList<ApplicationsUsedEvent> listOfValidEvents = 
                new ArrayList<>();
        
        for (ApplicationsUsedEvent event: listOfEvents) {
            
            if (StressSurvey.isEventInsideSurveyTiming(timestamp, event.getTimestamp())) {
                
                listOfValidEvents.add(event);
            }
        }
        
        applicationUsedFeaturesExtractor =  
                new ApplicationUsedFeaturesExtractor(
                        ApplicationUsed.createListOfApplicationsUsed(
                                listOfValidEvents));
    }
    
    /**
     * Returns if during the time validity of the survey that particular 
     * application category has been used
     * @param appCategory the category of the app
     * @return true if the application category has been used, false otherwise
     */
    public Boolean isApplicationCategoryUsed(String appCategory) {
        
        return applicationUsedFeaturesExtractor.hasUsedTheApplicationCategory(appCategory);
    }
    
    /**
     * Returns if during the time validity of the survey that particular 
     * application type has been used
     * @param appType the application type
     * @return true if the application type has been used, false otherwise
     */
    public Boolean isApplicationTypeUsed(String appType) {
        
        return applicationUsedFeaturesExtractor.hasUsedApplicationType(appType);
    }
    
    /**
     * Returns the screen events wrapper
     * @return the UserPresenceAdvancedEventsWrapper object
     */
    public UserPresenceAdvancedEventsWrapper getUserPresenceAdvancedEventsWrapper() {
        
        return this.userPresenceAdvancedEventsWrapper;
    }
    
    /**
     * Returns the UserActivityEvents wrapper
     * @return the UserActivityEventsWrapper object 
     */
    public UserActivityFeaturesExtractor getUserActivityFeaturesExtractor() {
        
        return this.userActivityFeaturesExtractor;
    }
    
    public ApplicationUsedFeaturesExtractor getApplicationUsedFeaturesExtractor() {
        
        return this.applicationUsedFeaturesExtractor;
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
