package iwildsensestressanalyzer.esm;

import iwildsensestressanalyzer.filereader.WeatherMappingReader;
import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Contains all the information about the weather associated with a particular 
 * survey
 * @author Matteo Ciman
 */
public class WeatherInfos {
    
    private final long timestamp;
    private final String city, country;
    private final Float firstField;
    private final String secondField;
    private final Float thirdField;
    private final Float fourthField;
    private final String weatherDescription;
    private final Float fifthField;
    
    private static final ArrayList<String> WEATHER_POSSIBILITIES = new ArrayList<>();
    
    public WeatherInfos(String line) {
        
        String[] elements = line.split(",");
        
        timestamp = Long.valueOf(elements[0]);
        city = elements[1]; country = elements[2];
        if (!elements[3].equals("")) {
            firstField = Float.valueOf(elements[3]);
        }
        else {
            firstField = null;
        }
        secondField = elements[4];
        if (!elements[5].equals(elements[5])) {
            thirdField = Float.valueOf(elements[5]);
        }
        else {
            thirdField = null;
        }
        if (!elements[6].equals(elements[6])) {
            fourthField = Float.valueOf(elements[6]);
        }
        else {
            fourthField = null;
        }
        if (elements[7].equals("Unknown")) {
            weatherDescription = null;
        }
        else {
            weatherDescription = 
                    WeatherMappingReader.getWeatherValue(elements[7]);
            if (!WEATHER_POSSIBILITIES.contains(weatherDescription)) {
                WEATHER_POSSIBILITIES.add(weatherDescription);
            }
        }
        if (!elements[8].equals("")) {
            fifthField = Float.valueOf(elements[8]);
        }
        else {
            fifthField = null;
        }
    }
    
    public long getTimestamp() {
        return this.timestamp;
    }
    
    public String getWeatherDescription() {
        return this.weatherDescription;
    }
    
    public String getWeatherForFeatures() {
        if (weatherDescription != null) {
            return String.valueOf(WEATHER_POSSIBILITIES.indexOf(weatherDescription));
        }
        else return null;
    }
    
    /**
     * Returns, from a list of WeatherInfos, the one associated with the 
     * survey
     * @param surveyTimestap the timestamp of the survey
     * @param weatherInfos the list of WeatherInfos to find the right one
     * @return the WeatherInfos associated with the survey if it exists, false
     * otherwise
     */
    public static WeatherInfos getWeatherInfosForSurvey(long surveyTimestap, 
            ArrayList<WeatherInfos> weatherInfos) {
        
        long MAX_DISTANCE = 30 * 60 * 1000; //max distance
        
        GregorianCalendar surveyDate = new GregorianCalendar();
        surveyDate.setTimeInMillis(surveyTimestap);
        
        boolean found = false; WeatherInfos rightOne = null;
        
        for (int i = 0; i < weatherInfos.size() && !found; i++) {
            if (Math.abs(surveyTimestap - weatherInfos.get(i).getTimestamp()) < 
                    MAX_DISTANCE) {
                
                rightOne = weatherInfos.get(i); found = true;
            }
        }
        
        return rightOne;
    }
    
    public static void printWeatherPossibilities() {
        
        System.out.println("****** Weather Conditions ******");
        for (int i = 0; i < WEATHER_POSSIBILITIES.size(); i++) {
            System.out.println("[" + i + "] => " + WEATHER_POSSIBILITIES.get(i));
        }
        System.out.println("*********************************");
    }
}
