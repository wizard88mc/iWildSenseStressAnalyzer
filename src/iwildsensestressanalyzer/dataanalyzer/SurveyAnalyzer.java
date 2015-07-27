package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.esm.StressSurvey;
import iwildsensestressanalyzer.participant.Participant;
import java.util.ArrayList;

/**
 *
 * This class is responsible to calculate some statistical information 
 * about the answers provided by the Participants to the daily surveys, 
 * in particular the number of answered surveys and the distribution of the
 * answers with respect to Energy, Valence and Stress
 * 
 * @author Matteo Ciman
 * @version 0.1 First draft of the class
 */
public class SurveyAnalyzer {
    
    
    public static void analyzeSurveysAnswers(ArrayList<Participant> participants) {
        
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
        
        double average = (double)answersCounter / (double)participants.size();
        
        /**
         * Calculating the standard deviation of the mean number of provided
         * answers
         */
        double standardDeviation = 0;
        
        for (Participant participant: participants) {
            
            standardDeviation += 
                    Math.pow(participant.getSurveyAnswersCount() - average, 2);
        }
        
        standardDeviation /= participants.size();
        
        standardDeviation = Math.sqrt(standardDeviation);
        
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
        System.out.println("Average survey answers provided: " + average);
        System.out.println("Standard deviation: " + standardDeviation);
        System.out.println("Percentage answer = 1: " + percentageOne);
        System.out.println("Percentage answer = 2: " + percentageTwo);
        System.out.println("Percentage answer = 3: " + percentageThree);
        System.out.println("Percentage answer = 4: " + percentageFour);
        System.out.println("Percentage answer = 5: " + percentageFive);
    }
    
}
