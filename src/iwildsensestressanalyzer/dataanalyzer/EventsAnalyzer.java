package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;
import org.apache.commons.math3.stat.inference.TTest;

/**
 *
 * Static class that contains methods suitable for all the EventsAnalyzer
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class EventsAnalyzer {
    
    /**
     * Creates the list of SurveyDataWrapper from all the participants that 
     * will be used to analyze data all together
     * @param participants the list of participants
     * @param easyJob true if considering the easy task, false otherwise
     * @return A list of list of SurveyDataWrapper, one SurveyDataWrapper for
     * each participant
     */
    protected static ArrayList<ArrayList<SurveyDataWrapper>> prepareDataWrappersForAllParticipants(
        ArrayList<Participant> participants, boolean easyJob) {
        
        ArrayList<ArrayList<SurveyDataWrapper>> allSurveyDataWrappers =
            new ArrayList<ArrayList<SurveyDataWrapper>>();
            
        if (!easyJob) {    
            for(int i = 0; i < participants.get(0).getSurveyDataWrappers().length; i++) {
                allSurveyDataWrappers.add(new ArrayList<SurveyDataWrapper>());
            }

            for(Participant participant: participants) {
                SurveyDataWrapper[] wrappers = participant.getSurveyDataWrappers();
                for (int i = 0; i < wrappers.length; i++) {
                    allSurveyDataWrappers.get(i).add(wrappers[i]);
                }
            }

        }
        else {
            for (int i = 0; i < participants.get(0).getEasySurveyDataWrappers().length; i++) {
                allSurveyDataWrappers.add(new ArrayList<SurveyDataWrapper>());
            }

            for (Participant participant: participants) {
                SurveyDataWrapper[] wrappers = participant.getEasySurveyDataWrappers();
                for (int i = 0; i < wrappers.length; i++) {
                    allSurveyDataWrappers.get(i).add(wrappers[i]);
                }
            }
        }
        
        return allSurveyDataWrappers;
    }
    
    /**
     * Prints the confusion matrix between all the stress states 
     * @param normalizedValues a list of array of double with the values for the
     * t-test
     * @param easyJob
     */
    protected static void printTTestResults(ArrayList<ArrayList<Double>> normalizedValues, 
            boolean easyJob) {
        
        /**
         * Converting data to an array of double for the TTest library
         */
        ArrayList<double[]> valuesForTTest = new ArrayList<double[]>();
        for (ArrayList<Double> values: normalizedValues) {
            valuesForTTest.add(MathUtils.convertToArrayDouble(values));
        }
        
        if (!easyJob) {
            System.out.println("   |   1   |   2   |   3   |   4   |   5   |");
        }
        else {
            System.out.println("   |   1   |   2   |   3   |");
        }
        System.out.println("--------------------------------------------");
        for (int i = 0; i < valuesForTTest.size(); i++) {
            
            String outputString = "";
            
            for (int j = 0; j < valuesForTTest.size(); j++) {
             
                if (j == 0) {
                    outputString += " " + (i + 1) + " |" + "  NaN  |"; 
                }
                else {
                    if (j <= i) {
                        outputString += "  NaN  |";
                    }
                    else {
                        /**
                         * This is the only case we have to perform TTest
                         */
                        
                        /**
                         * First check if both data list has at least one value, 
                         * otherwise no test can be performed
                         */
                        if (valuesForTTest.get(i) != null && 
                                valuesForTTest.get(j) != null &&
                                valuesForTTest.get(i).length != 0 && 
                                valuesForTTest.get(j).length != 0) {
                            
                            try {
                                double tTest = new TTest().tTest(valuesForTTest.get(i), 
                                    valuesForTTest.get(j));
                                
                                if (Double.isNaN(tTest) || Double.isInfinite(tTest)) {
                                    tTest = 1.0;
                                }
                            
                                outputString += MathUtils.decimalFormat.format(tTest) + "|";
                            }
                            catch(Exception exc) {
                                outputString += "  ---  |";
                            }
                        }
                        else {
                            outputString += "  ---  |";
                        }
                    }
                }
            }
            System.out.println(outputString);
        }
    }
    
}
