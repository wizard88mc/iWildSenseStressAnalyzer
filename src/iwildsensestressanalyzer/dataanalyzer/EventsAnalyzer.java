package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.IWildSenseStressAnalyzer;
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
            new ArrayList<>();
            
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
     * @param listValues a list of array of double with the values for the
     * t-test
     * @param easyJob false if using all the 5-likert scale, false otherwise
     * @return list of passed or not passed test for each test
     */
    protected static ArrayList<Boolean> printTTestResults(ArrayList<ArrayList<Double>> listValues, 
            boolean easyJob) {
        
        ArrayList<ArrayList<Double>> normalizedValues = 
                MathUtils.normalizeSetOfDoubleData(listValues, 0.0, 1.0);
        
        /**
         * Converting data to an array of double for the TTest library
         */
        ArrayList<double[]> valuesForTTest = new ArrayList<>();
        for (ArrayList<Double> values: normalizedValues) {
            valuesForTTest.add(MathUtils.convertToArrayDouble(values));
        }
        
        ArrayList<Boolean> testPassed = new ArrayList<>();
        
        if (!easyJob) {
            IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("   |   1   |   2   |   3   |   4   |   5   |");
        }
        else {
            IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("   |   1   |   2   |   3   |");
        }
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("--------------------------------------------");
        
        for (int i = 0; i < valuesForTTest.size(); i++) {
            
            String outputString = "";
            
            for (int j = 0; j < valuesForTTest.size(); j++) {
             
                if (j == 0) {
                    outputString += " " + (i + 1) + " |" + " ----- |"; 
                }
                else {
                    if (j <= i) {
                        outputString += " ----- |";
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
                                double tTest = new TTest().pairedTTest(valuesForTTest.get(i), 
                                    valuesForTTest.get(j));
                                
                                if (Double.isNaN(tTest) || Double.isInfinite(tTest)) {
                                    tTest = 1.0;
                                }
                            
                                outputString += MathUtils.decimalFormat.format(tTest) + "|";
                                
                                if (tTest <= 0.05) {
                                    testPassed.add(true);
                                }
                                else {
                                    testPassed.add(false);
                                }
                            }
                            catch(Exception exc) {
                                outputString += " ----- |";
                                testPassed.add(null);
                            }
                        }
                        else {
                            outputString += " ----- |";
                            testPassed.add(null);
                        }
                    }
                }
            }
            IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance(outputString);
        }
        
        return testPassed;
    }
    
    /**
     * Prints the percentage of success of a particular feature
     * @param values the values of success to print
     * @param easyJob true is it is the easy test, false otherwise
     */
    protected static void printTTestPercentagesOfSuccess(ArrayList<Double> values, 
            boolean easyJob) {
        
        int numRowsAndColumns;
        
        if (!easyJob) {
            IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("   | 1 | 2 | 3 | 4 | 5 |");
            numRowsAndColumns = 5;
        }
        else {
            IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("   | 1 | 2 | 3 |");
            numRowsAndColumns = 3;
        }
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("--------------------------------------------");
        
        int counterForValues = 0;
        for (int i = 0; i < numRowsAndColumns; i++) {
            
            String outputString = "";
            
            for (int j = 0; j < numRowsAndColumns; j++) {
             
                if (j == 0) {
                    outputString += " " + (i + 1) + " |" + " - |"; 
                }
                else {
                    if (j <= i) {
                        outputString += " - |";
                    }
                    else {
                        /**
                         * This is the only case we have to perform TTest
                         */
                        
                        /**
                         * First check if both data list has at least one value, 
                         * otherwise no test can be performed
                         */
                        if (values.get(counterForValues) != null) { 
                            outputString += MathUtils.formatSuccess.format(values.get(counterForValues)) + "%|";
                        }
                        else {
                            outputString += " - |";
                        }
                        
                        counterForValues++;
                    }
                }
            }
            IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance(outputString);
        }
    }
    
    /**
     * Performs all the steps necessary to calculate and print the percentage
     * of success of a particular feature
     * @param values the set of values of tests passed
     * @param validTTests a list with the number of valid t-tests
     * @param easyJob true if using only three values, false for five values
     * @param titleMessage the message to write
     */
    protected static void performStepsForPrintingPercentageOfSuccess(ArrayList<Integer> values, 
            ArrayList<Integer> validTTests, boolean easyJob, String titleMessage) {
        
        printTitleMessage(titleMessage);
        ArrayList<Double> results = calculatePercentages(values, validTTests);
        printTTestPercentagesOfSuccess(results, easyJob);
    }
    
    /**
     * Prints the title message when printing results
     * @param message 
     */
    public static void printTitleMessage(String message) {
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance(null);
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance(message);
    }
    
    /**
     * Called for every participant, it takes the results of the t-test for 
     * each one of the possible combination of stress values and increases the
     * counter for the ones that passed the test
     * @param resultsContainer the list of combinations with the number of 
     * overall passed values
     * @param tTestResults a list of t-test results 
     * @param validTests a list to count the number of valid tests
     */
    protected static void addTTestResultsToFinalContainer(ArrayList<Integer> resultsContainer, 
            ArrayList<Boolean> tTestResults, ArrayList<Integer> validTests) {
        
        if (resultsContainer.isEmpty()) {
            for (Boolean result: tTestResults) {
                resultsContainer.add(0);
                validTests.add(0);
            }
        }
        
        for (int index = 0; index < resultsContainer.size(); index++) {
            
            if (tTestResults.get(index) != null) {
                if (tTestResults.get(index)) {
                    resultsContainer.set(index, resultsContainer.get(index) + 1);
                }
                validTests.set(index, validTests.get(index) + 1);
            }
        }
    }
    
    /**
     * Calculates the percentage of checks that passed the t test
     * @param results the final results of 
     * @param validTTests the total number of participants
     * @return a list with the percentages of success
     */
    protected static ArrayList<Double> calculatePercentages(ArrayList<Integer> results, 
            ArrayList<Integer> validTTests) {
        
        ArrayList<Double> dResults = new ArrayList<>();
        for (Integer result: results) {
            dResults.add(null);
        }
        
        for (int index = 0; index < results.size(); index++) {
            
            if (index < validTTests.size()) {
            
                try {
                    Double result = (results.get(index) / 
                            (double) validTTests.get(index)) * 100;

                    if (result.isInfinite() || result.isNaN()) {
                        result = null;
                    }
                    dResults.set(index, result);
                }
                catch(Exception exc) {
                    dResults.set(index, null);
                }
            }
            else {
                dResults.set(index, null);
            }
        }
        
        return dResults;
    }
}
