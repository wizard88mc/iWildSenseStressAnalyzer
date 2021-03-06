package iwildsensestressanalyzer.applicationsused;

import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class ApplicationUsedFeaturesExtractorListWrapper {
    
    private final ArrayList<ApplicationUsedFeaturesExtractor> 
            applicationUsedFeaturesExtractorList;
    
    public ApplicationUsedFeaturesExtractorListWrapper(ArrayList<ApplicationUsedFeaturesExtractor> 
            applicationUsedFeaturesExtractorList) {
        
        this.applicationUsedFeaturesExtractorList = 
                applicationUsedFeaturesExtractorList;
    }
    
    /**
     * Creates a list of the influence of the considered application category
     * over all the features extractor
     * @param appCategory the category we are currently considering
     * @return a list of influences of the application category
     */
    public ArrayList<Double> getAllInfluenceOfAppCategory(String appCategory) {
        
        ArrayList<Double> listValues = new ArrayList<>();
        
        for (ApplicationUsedFeaturesExtractor appFeaturesExtractor: 
                applicationUsedFeaturesExtractorList) {
            listValues.add(appFeaturesExtractor.calculateInfluenceOfAppCategory(appCategory));
        }
        
        return listValues;
    }
    
    /**
     * Calculate statistics information about the influence of an app category
     * over all the categories
     * @param appCategory the name of the app category
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise, null if no values available
     */
    public Double[] calculateStatisticsInfluenceOfAppCategory(String appCategory) {
        
        ArrayList<Double> listValues = getAllInfluenceOfAppCategory(appCategory);
        
        if (listValues != null) {
            return MathUtils.calculateStatisticInformation(listValues);
        }
        else {
            return null;
        }
    }
    
    /**
     * Creates a list of the influence of the considered application category
     * over all the features extractor
     * @param appType the application type
     * @return a list of influences of the application type
     */
    public ArrayList<Double> getAllInfluenceOfAppType(String appType) {
        
        ArrayList<Double> listValues = new ArrayList<>();
        
        for (ApplicationUsedFeaturesExtractor appFeatureExtractor: 
                applicationUsedFeaturesExtractorList) {
            
            listValues.add(appFeatureExtractor.calculateTimingInfluenceOfAppType(appType));
        }
        
        return listValues;
    }
    
    /**
     * Calculate statistics information about the influence of an app category
     * over all the categories
     * @param appType the name of the app type
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise, null if no values available
     */
    public Double[] calculateStatisticsInfluenceOfAppType(String appType) {
        
        ArrayList<Double> listValues = getAllInfluenceOfAppType(appType);
        
        if (listValues != null) {
            return MathUtils.calculateStatisticInformation(listValues);
        }
        else {
            return null;
        }
    }
    
    /**
     * Creates a list of influence of the considered application category 
     * over all the features extractor in terms of timing duration
     * @param appCategory the category we are currently considering
     * @return a list of influences of the application category in terms of 
     * timing duration
     */
    public ArrayList<Double> getAllTimingInfluenceOfAppCategory(String appCategory) {
        
        ArrayList<Double> listValues = new ArrayList<>();
        
        for (ApplicationUsedFeaturesExtractor appFeaturesExtractor: 
                applicationUsedFeaturesExtractorList) {
            listValues.add(appFeaturesExtractor.calculateTimingInfluenceOfAppCategory(appCategory));
        }
        
        return listValues;
    }
    
    /**
     * Calculate statistics information about the timing influence of an app category
     * over all the categories
     * @param appCategory the name of the app category
     * @return [average, variance, standard deviation] if more than zero values, 
     * [-1] otherwise, null if no values available
     */
    public Double[] calculateStatisticsOfTimingInfluenceOfAppCategory(String appCategory) {
        
        ArrayList<Double> listValues = getAllTimingInfluenceOfAppCategory(appCategory);
        
        if (listValues != null) {
            return MathUtils.calculateStatisticInformation(listValues);
        }
        else {
            return null;
        }
    }
    
    public ArrayList<Double> getAllTimingInfluenceOfAppType(String appType) {
        
        ArrayList<Double> values = new ArrayList<>();
        
        for (ApplicationUsedFeaturesExtractor appFeaturesExtractor: 
                applicationUsedFeaturesExtractorList) {
            values.add(appFeaturesExtractor.calculateTimingInfluenceOfAppType(appType));
        }
        
        return values;
    }
    
    public Double[] calculateStatisticsOfTimingInfluenceOfAppType(String appType) {
        
        ArrayList<Double> listValues = getAllTimingInfluenceOfAppType(appType);
                
        if (listValues != null) {
            return MathUtils.calculateStatisticInformation(listValues);
        }
        else {
            return null;
        }
    }
}
