package iwildsensestressanalyzer.weka;

import iwildsensestressanalyzer.esm.StressSurvey;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class WekaFeaturesForDaysAndWeather extends WekaFeaturesCreator {
    
    public static String[] featuresName = {"DAY_WEEK", "MOMENT_DAY", "WEATHER"};
    
    public static ArrayList<String> getFeaturesForDaysAndWeather(StressSurvey survey) {
        
        ArrayList<String> features = new ArrayList<>();
        
        features.add(survey.dayToString());
        
        features.add(survey.dayMomentToString());
        
        if (survey.getWeather() != null) {
            features.add(survey.getWeather().getWeatherForFeatures());
        }
        else {
            features.add("?");
        }
        
        return features;
    }
}
