package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.participant.Participant;
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
         * Printing number of surveys for each participant (maybe to remove the 
         * ones with number of answers lower of a particular value??)
         */
        System.out.println("Total participants: " + participants.size());
        System.out.println("*** PRINTING IMEI: # OF ANSWERS ***");
        for (Participant participant: participants) {
            System.out.println("Participant " + participant.getIMEI() + 
                    ": " + participant.getSurveyAnswersCount());
        }
        
        System.out.println();
        /**
         * Printing results
         */
        DecimalFormat format = new DecimalFormat("#.##");
        System.out.println("Average survey answers provided: " + format.format(average));
        System.out.println("Standard deviation: " + format.format(standardDeviation));
        System.out.println("Percentage answer = 1: " + format.format(percentageOne) + 
                " (" + counterOne + "/" + totalAnswers + ")");
        System.out.println("Percentage answer = 2: " + format.format(percentageTwo) + 
                " (" + counterTwo + "/" + totalAnswers + ")");
        System.out.println("Percentage answer = 3: " + format.format(percentageThree) + 
                " (" + counterThree + "/" + totalAnswers + ")");
        System.out.println("Percentage answer = 4: " + format.format(percentageFour) + 
                " (" + counterFour + "/" + totalAnswers + ")");
        System.out.println("Percentage answer = 5: " + format.format(percentageFive) + 
                " (" + counterFive + "/" + totalAnswers + ")");
        
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
                higherThanThreshold = 0, lowerThanThreshold = 0;
        
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
        }
        
        System.out.println();
        System.out.println("Number of participants with number of answers higher"
                + " than the average: " + higherThanAverage);
        System.out.println("Number of participants with number of answers lower"
                + " than the average: " + lowerThanAverage);
        System.out.println("Number of participants with number of answers higher"
                + " than the threshold: " + higherThanThreshold);
        System.out.println("Number of participants with number of answers lower"
                + " than the threshold: " + lowerThanThreshold);
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
