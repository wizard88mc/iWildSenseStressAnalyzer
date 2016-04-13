package iwildsensestressanalyzer.dataanalyzer;

import iwildsensestressanalyzer.filewriter.StatisticsFileWriter;
import iwildsensestressanalyzer.participant.Participant;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class responsible of calculating several statistical information from the data
 * - percentage of participants using a particular application
 * 
 * @author Matteo Ciman
 */
public class StatisticsAnalyzer {
    
    private final ArrayList<Participant> participants;
    private final StatisticsFileWriter statisticsFileWriter;
    
    public StatisticsAnalyzer(ArrayList<Participant> participants) {
        this.participants = participants;
        
        statisticsFileWriter = new StatisticsFileWriter();
    }
    
    /**
     * Perform the steps of statistic analysis 
     */
    public void performAnalysis() {
        
        analyzeApplicationsUsage();
        analyzeStressValuesPerDayDistribution();
        analyzeStressValuesPerTimeDistribution();
        analyzeStressValuesPerTimePerDayDistribution();
    }
 
    /**
     * Evaluates the usage of all application categories among participants
     */
    private void analyzeApplicationsUsage() {
        
        HashMap<String, Double> percentageUsage = 
                ApplicationUsedAnalyzer.analyzeApplicationsUsage(participants);
        
        statisticsFileWriter.writeApplicationUsage(percentageUsage);
    }
    
    /**
     * Evaluates stress value distribution among the days of the week
     */
    private void analyzeStressValuesPerDayDistribution() {
        
        ArrayList<ArrayList<Double>> stressPerDays = new ArrayList<>();
        
        for (Participant participant: participants) {
            
            ArrayList<ArrayList<Double>> participantStressPerDays = 
                    participant.getStressValuesPerDay();
            
            ArrayList<Double> averageValuesPerDay = new ArrayList<>(), 
                    standardDeviationPerDay = new ArrayList<>();
            
            participant.setStressValuesPerDays(participantStressPerDays);
            
            /**
             * Calculating average and standard deviation values 
             */
            for (ArrayList<Double> days: participantStressPerDays) {
                
                Double[] values = MathUtils.calculateStatisticInformation(days);
                averageValuesPerDay.add(values[0]);
                standardDeviationPerDay.add(values[2]);
            }
            
            /**
             * Add output line for the participant
             */
            statisticsFileWriter.writeStressPerDay(participant.getIMEI(), 
                    averageValuesPerDay, standardDeviationPerDay);
            
            
            for (int i = 0; i < averageValuesPerDay.size(); i++) {
                
                if (stressPerDays.size() != averageValuesPerDay.size()) {
                    stressPerDays.add(new ArrayList<Double>());
                }
                
                stressPerDays.get(i).add(averageValuesPerDay.get(i));
            }
        }
        
        ArrayList<Double> averageStressPerDays = new ArrayList<>(), 
                standardDeviationPerDay = new ArrayList<>();
        
        Participant.setStressValuesPerDaysForAllParticipants(stressPerDays);
        
        for (ArrayList<Double> days: stressPerDays) {
            
            Double[] values = MathUtils.calculateStatisticInformation(days);
            averageStressPerDays.add(values[0]);
            standardDeviationPerDay.add(values[2]);
        }
        
        /**
         * Add output line for all participants
         */
        statisticsFileWriter.writeStressPerDay("ALL", averageStressPerDays, 
                standardDeviationPerDay);
        
        statisticsFileWriter.closeWriterStressPerDay();
    }
    
    /**
     * Evaluates stress distribution among the time ranges of the day
     */
    private void analyzeStressValuesPerTimeDistribution() {
        
        ArrayList<ArrayList<Double>> stressPerHours = new ArrayList<>();
        
        for (Participant participant: participants) {
            
            ArrayList<ArrayList<Double>> participantStressPerHours = 
                    participant.getStressValuesPerHours();
            
            ArrayList<Double> averageValuesPerHours = new ArrayList<>(), 
                    standardDeviationPerHours = new ArrayList<>();
            
            for (ArrayList<Double> stressValues: participantStressPerHours) {
                
                Double[] values = MathUtils.calculateStatisticInformation(stressValues);
                averageValuesPerHours.add(values[0]);
                standardDeviationPerHours.add(values[2]);
            }
            
            participant.setStressValuesPerHours(participantStressPerHours);
            
            /**
             * Writing on the output file
             */
            statisticsFileWriter.writeStressPerHour(participant.getIMEI(), 
                    averageValuesPerHours, standardDeviationPerHours);
            
            for (int i = 0; i < averageValuesPerHours.size(); i++) {
                
                if (stressPerHours.size() != averageValuesPerHours.size()) {
                    stressPerHours.add(new ArrayList<Double>());
                }
                
                stressPerHours.get(i).add(averageValuesPerHours.get(i));
            }
        }
        
        Participant.setStressValuesPerHoursForAllParticipants(stressPerHours);
        
        ArrayList<Double> averageStressPerHours = new ArrayList<>(), 
                standardDeviationPerHours = new ArrayList<>();
        
        for (ArrayList<Double> valuesHours: stressPerHours) {
            
            Double[] values = 
                    MathUtils.calculateStatisticInformation(valuesHours);
            
            averageStressPerHours.add(values[0]);
            standardDeviationPerHours.add(values[2]);
        }
        
        /**
         * Add output line for all participants
         */
        statisticsFileWriter.writeStressPerHour("ALL", averageStressPerHours, 
                standardDeviationPerHours);
        
        statisticsFileWriter.closeWriterStressPerHours();
    }
    
    /**
     * Evaluates stress distribution among time ranges and days of the week
     */
    private void analyzeStressValuesPerTimePerDayDistribution() {
        
        ArrayList<ArrayList<Double>> stressPerHoursPerDays = new ArrayList<>();
        
        for (Participant participant: participants) {
            
            ArrayList<ArrayList<Double>> participantStressPerHoursPerDays = 
                    participant.getStressValuesPerHoursPerDay();
            
            ArrayList<Double> averageValuesPerHoursPerDays = new ArrayList<>(), 
                    standardDeviationPerHoursPerDays = new ArrayList<>();
            
            for (ArrayList<Double> stressValues: participantStressPerHoursPerDays) {
                
                Double[] values = MathUtils.calculateStatisticInformation(stressValues);
                averageValuesPerHoursPerDays.add(values[0]);
                standardDeviationPerHoursPerDays.add(values[2]);
            }
            
            /**
             * Writing on the output file
             */
            statisticsFileWriter.writeStressPerHoursPerDays(participant.getIMEI(), 
                    averageValuesPerHoursPerDays, 
                    standardDeviationPerHoursPerDays);
            
            for (int i = 0; i < averageValuesPerHoursPerDays.size(); i++) {
                
                if (stressPerHoursPerDays.size() != 
                        averageValuesPerHoursPerDays.size()) {
                    
                    stressPerHoursPerDays.add(new ArrayList<Double>());
                }
                
                stressPerHoursPerDays.get(i).add(averageValuesPerHoursPerDays.get(i));
            }
        }
        
        ArrayList<Double> averageStressPerHoursPerDays = new ArrayList<>(), 
                standardDeviationPerHoursPerDays = new ArrayList<>();
        
        for (ArrayList<Double> valuesHoursPerDays: stressPerHoursPerDays) {
            
            Double[] values = 
                    MathUtils.calculateStatisticInformation(valuesHoursPerDays);
            
            averageStressPerHoursPerDays.add(values[0]);
            standardDeviationPerHoursPerDays.add(values[2]);
        }
        
        /**
         * Add output line for all participants
         */
        statisticsFileWriter.writeStressPerHoursPerDays("ALL", averageStressPerHoursPerDays, 
                standardDeviationPerHoursPerDays);
        
        statisticsFileWriter.closeWriterStressPerHoursPerDays();
    }
}
