package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.IWildSenseStressAnalyzer;
import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 *
 * This class is responsible to calculate some statistical information 
 * about the answers provided by the Participants to the daily surveys, 
 * in particular the number of answered surveys and the distribution of the
 * answers with respect to Energy, Valence and Stress
 * 
 * @author Matteo Ciman
 * @version 1.0 
 */
public class SurveyAnalyzer {
    
    private static final int ANSWERS_THRESHOLD = 10;
    private static final int ANSWERS_ONE_PER_DAY = 28;
    
    private static double initialAverage = 0.0;
    private static double initialVariance = 0.0;
    private static double initialStandardDeviation = 0.0;
    
    public static void calculateStatisticsAnswers(ArrayList<Participant> participants) {
        
        double average = 0.0, variance = 0.0, standardDeviation = 0.0;
        int answersCounter = 0;
        int counterOne = 0, counterTwo = 0, counterThree = 0, counterFour = 0, 
                counterFive = 0;
        
        /**
         * Counting the number of provided answers to calculate the mean value
         */
        for (Participant participant: participants) {
            
            answersCounter += participant.getSurveyAnswersCount();
            
            for (StressSurvey survey: participant.getStressSurveys()) {
                
                switch (survey.getStress()) {
                    
                    case 1: {
                        counterOne++;
                        break;
                    }
                    case 2: {
                        counterTwo++;
                        break;
                    }
                    case 3: {
                        counterThree++;
                        break;
                    }
                    case 4: {
                        counterFour++;
                        break;
                    }
                    case 5: {
                        counterFive++;
                        break;
                    }
                }
            }
        }
        
        average = (double)answersCounter / (double)participants.size();
        
        /**
         * Calculating the standard deviation of the mean number of provided
         * answers
         */
        
        for (Participant participant: participants) {
            
            variance += 
                    Math.pow(participant.getSurveyAnswersCount() - average, 2);
        }
        
        variance /= participants.size();
        
        standardDeviation = Math.sqrt(variance);
        
        /**
         * Calculating percentage of stress answers
         */
        int totalAnswers = counterOne + counterTwo + counterThree + counterFour 
                + counterFive;
        double percentageOne = (100 * (double)counterOne) / (double)totalAnswers, 
                percentageTwo = (100 * (double)counterTwo) / (double)totalAnswers,
                percentageThree = (100 * (double)counterThree) / (double)totalAnswers,
                percentageFour = (100 * (double)counterFour) / (double)totalAnswers,
                percentageFive = (100 * (double)counterFive) / (double)totalAnswers;
        
        /**
         * Printing number of surveys for each participant
         */
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Total participants: " 
                        + participants.size());
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("*** PRINTING IMEI: # OF ANSWERS ***");
        for (Participant participant: participants) {
            IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Participant " + participant.getIMEI() + 
                    ": " + participant.getSurveyAnswersCount());
        }
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance(null);
        
        /**
         * Printing results
         */
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Average survey answers provided: " 
                    + MathUtils.DECIMAL_FORMAT.format(average));
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Standard deviation: " 
                    + MathUtils.DECIMAL_FORMAT.format(standardDeviation));
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Percentage answer = 1: " + 
                    MathUtils.DECIMAL_FORMAT.format(percentageOne) + " (" + counterOne + "/" + 
                        totalAnswers + ")");
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Percentage answer = 2: " + 
                        MathUtils.DECIMAL_FORMAT.format(percentageTwo) + " (" + counterTwo + "/" + 
                        totalAnswers + ")");
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Percentage answer = 3: " + 
                        MathUtils.DECIMAL_FORMAT.format(percentageThree) + " (" + counterThree + "/" 
                        + totalAnswers + ")");
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Percentage answer = 4: " + 
                        MathUtils.FORMAT_SUCCESS.format(percentageFour) + " (" + counterFour + "/" 
                        + totalAnswers + ")");
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Percentage answer = 5: " + 
                        MathUtils.DECIMAL_FORMAT.format(percentageFive) + " (" + counterFive + "/" 
                        + totalAnswers + ")");
        
        if (initialAverage == 0.0) {
            initialAverage = average; initialVariance = variance;
            initialStandardDeviation = standardDeviation;
        }
    }
    
    /**
     * Counts the number of participants that have a number of answers provided 
     * to the surveys higher (or lower) with respect to the average number of 
     * answers and the threshold
     * @param participants 
     */
    public static void printAnalysisParticipantsParticipation(ArrayList<Participant> participants) {
        
        int higherThanAverage = 0, lowerThanAverage = 0, 
                higherThanThreshold = 0, lowerThanThreshold = 0, 
                higherThanMoreThanOnePerDay = 0, lowerThanMoreThanOnePerDay = 0;
        
        for (Participant participant: participants) {
            if (participant.getSurveyAnswersCount() >= initialAverage) {
                higherThanAverage++;
            }
            else {
                lowerThanAverage++;
            }
            
            if (participant.getSurveyAnswersCount() >= ANSWERS_THRESHOLD) {
                higherThanThreshold++;
            }
            else {
                lowerThanThreshold++;
            }
            
            if (participant.getSurveyAnswersCount() >= ANSWERS_ONE_PER_DAY) {
                higherThanMoreThanOnePerDay++;
            }
            else {
                lowerThanMoreThanOnePerDay++;
            }
        }
        
        IWildSenseStressAnalyzer.outputWriter.writeOutputStatisticalSignificance(null);
        IWildSenseStressAnalyzer.outputWriter.writeOutputStatisticalSignificance(
                "Number of participants with number of answers higher"
                + " than the average: " + higherThanAverage);
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Number of participants with"
                        + " number of answers lower than the average: " 
                        + lowerThanAverage);
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Number of participants with"
                        + " number of answers higher than the threshold: " 
                        + higherThanThreshold);
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Number of participants with"
                        + " number of answers lower than the threshold: " 
                        + lowerThanThreshold);
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Number of participants with"
                        + " number of answers higher than one per day: " 
                        + higherThanMoreThanOnePerDay);
        
        IWildSenseStressAnalyzer.outputWriter
                .writeOutputStatisticalSignificance("Number of participants with"
                        + " number of answers lower than one per day: " 
                        + lowerThanMoreThanOnePerDay);
    }
    
    /**
     * Returns the average of the answers provided
     * @return the average of the answers provided
     */
    public static double getAverage() {
        return initialAverage;
    }
    
    /**
     * Returns the variance of the number of answers provided
     * @return the variance of the answers provided
     */
    public static double getVariance() {
        return initialVariance;
    }
    
    /**
     * Returns the standard deviation of the number of answers provided
     * @return the standard deviation of answers provided
     */
    public static double getStandardDeviation() {
        return initialStandardDeviation;
    }
    
    /**
     * Creates a new list of participants with answers provided higher than the 
     * threshold
     * @param participantList the original list of participants
     * @param threshold the minimum number of answers
     * @return a new list only with participants with a number of answers higher
     * than the threshold
     */
    private static ArrayList<Participant> removeParticipants(ArrayList<Participant> participantList, 
            int threshold) {
        
        ArrayList<Participant> newList = (ArrayList<Participant>)participantList.clone();
        
        for (int index = 0; index < newList.size(); ) {
            
            if (newList.get(index).getSurveyAnswersCount() <= threshold) {
                newList.remove(index);
            }
            else {
                index++;
            }
        }
        
        return newList;
        
    }
    
    /**
     * Returns a list of participants that answered at lest 0 survey
     * @param participantsList the original list of participants
     * @return a new list with participants with more than 0 survey answers
     */
    public static ArrayList<Participant> getListParticipantsWithMoreThanZeroAnswers(
            ArrayList<Participant> participantsList) {
        
        return removeParticipants(participantsList, 0);
    }
    
    /**
     * Returns a list of participants that answered at least a number of surveys
     * higher than the arbitrary threshold
     * @param participantsList the original list of participants
     * @return a new list with participants that answered a number of surveys 
     * more than our threshold
     */
    public static ArrayList<Participant> getListParticipantsOverArbitraryThreshold(
            ArrayList<Participant> participantsList) {
        
        return removeParticipants(participantsList, ANSWERS_THRESHOLD);
    }
    
    /**
     * Returns a list of participants that answered more than one time per day
     * @param participantsList the original list of participants
     * @return a new list with only participants that answered more than one
     * time per day
     */
    public static ArrayList<Participant> getListParticipantsOverOnePerDayAnswer(
            ArrayList<Participant> participantsList) {
        
        return removeParticipants(participantsList, ANSWERS_ONE_PER_DAY);
    }
    
    /**
     * Returns a list of participants that provided a number of answers more 
     * than then the initial average of survey answers
     * @param participantsList the original list of participants
     * @return a new list with only participants that provided more than the 
     * average number of survey answers
     */
    public static ArrayList<Participant> getListParticipantsOverInitialAverage(
            ArrayList<Participant> participantsList) {
        
        return removeParticipants(participantsList, (int) initialAverage);
    }
}
