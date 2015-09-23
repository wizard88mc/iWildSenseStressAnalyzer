package iwildsensestressanalyzer.applicationsused;

import java.util.ArrayList;

/**
 *
 * @author Matteo Ciman
 */
public class ApplicationUsedFeaturesExtractorListWrapper {
    
    private ArrayList<ApplicationUsedFeaturesExtractor> applicationUsedFeaturesExtractorList;
    
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
        
        ArrayList<Double> listValues = new ArrayList<Double>();
        
        for (ApplicationUsedFeaturesExtractor appFeaturesExtractor: 
                applicationUsedFeaturesExtractorList) {
            listValues.add(appFeaturesExtractor.calculateInfluenceOfAppCategory(appCategory));
        }
        
        return listValues;
    }
    
}
