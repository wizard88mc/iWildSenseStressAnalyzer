package iwildsensestressanalyzer.filewriter;

import iwildsensestressanalyzer.utils.MathUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * Writes the output of the 
 * @author Matteo CIman
 */
public class StatisticsFileWriter extends OutputFileWriter {
    
    private static final String STATISTICS_FOLDER = "statistics_analysis";
    
    private static final String APPLICATION_CATEGORY_USAGE_FILE = 
            "application_usage.csv";
    private static final String STRESS_DISTRIBUTION_PER_DAY = 
            "stress_per_day.csv";
    private static final String STRESS_DISTRIBUTION_PER_HOURS = 
            "stress_per_hours.csv";
    private static final String STRESS_DISTRIBUTION_PER_HOURS_PER_DAY = 
            "stress_per_hours_per_day.csv";
    
    private BufferedWriter applicationCategoryUsageFileWriter = null,
        stressDistributionPerDayFileWriter = null,
            stressDistributionPerHoursFileWriter = null,
            stressDistributionPerHoursPerDayFileWriter = null;
    
    public StatisticsFileWriter() {
        
        File applicationCategoryUsageFile = new File(BASE_OUTPUT_FOLDER + 
                File.separator + STATISTICS_FOLDER + File.separator + 
                APPLICATION_CATEGORY_USAGE_FILE);
        
        if (!applicationCategoryUsageFile.exists()) {
            applicationCategoryUsageFile.getParentFile().mkdirs();
        }
        
        try {
            applicationCategoryUsageFileWriter = 
                new BufferedWriter(new FileWriter(applicationCategoryUsageFile));
        
        }
        catch(IOException exc) {
            System.out.println("IOException in creating Application category "
                    + "usage file.");
            exc.printStackTrace();
            applicationCategoryUsageFileWriter = null;
        }
        
        File stressPerDay = new File(BASE_OUTPUT_FOLDER + File.separator + 
                STATISTICS_FOLDER + File.separator + STRESS_DISTRIBUTION_PER_DAY);
        
        if (!stressPerDay.exists()) {
            stressPerDay.getParentFile().mkdirs();
        }
        
        try {
            stressDistributionPerDayFileWriter = 
                    new BufferedWriter(new FileWriter(stressPerDay));
        }
        catch(IOException exc) {
            System.out.println("IOException in creating stress distribution per day file.");
            exc.printStackTrace();
            stressDistributionPerDayFileWriter = null;
        }
        
        File stressPerHours = new File(BASE_OUTPUT_FOLDER + File.separator + 
                STATISTICS_FOLDER + File.separator + STRESS_DISTRIBUTION_PER_HOURS);
        
        if (!stressPerHours.exists()) {
            stressPerHours.getParentFile().mkdirs();
        }
        
        try {
            stressDistributionPerHoursFileWriter = 
                    new BufferedWriter(new FileWriter(stressPerHours));
        }
        catch(IOException exc) {
            System.out.println("IOException in creating stress distribution per"
                    + " hours file.");
            exc.printStackTrace();
            stressDistributionPerHoursFileWriter = null;
        }
        
        File stressPerHoursPerDay = new File(BASE_OUTPUT_FOLDER + 
                File.separator + STATISTICS_FOLDER + File.separator + 
                STRESS_DISTRIBUTION_PER_HOURS_PER_DAY);
        
        if (!stressPerHoursPerDay.exists()) {
            stressPerHoursPerDay.getParentFile().mkdirs();
        }
        
        try {
            stressDistributionPerHoursPerDayFileWriter = 
                    new BufferedWriter(new FileWriter(stressPerHoursPerDay));
        }
        catch(IOException exc) {
            System.out.println("IOException in creating stress distribution "
                    + "per hours per day file.");
            exc.printStackTrace();
            stressDistributionPerHoursPerDayFileWriter = null;
        }
    }
    
    /**
     * Writes on the output file a list of lines category,percentage of usage 
     * @param category a map with all the categories and the percentage of usage
     */
    public void writeApplicationUsage(HashMap<String, Double> category) {
        
        for (String key: category.keySet()) {
            
            try {
                if (applicationCategoryUsageFileWriter != null) {
                    
                    applicationCategoryUsageFileWriter.write(key + "," + 
                        MathUtils.DECIMAL_FORMAT.format(category.get(key)));
                    
                    applicationCategoryUsageFileWriter.newLine();
                    applicationCategoryUsageFileWriter.flush();
                }
            }
            catch(IOException exc) {
                System.out.println("IOException in writing application usage"
                        + " line.");
                exc.printStackTrace();
            }
        }
        
        try {
            applicationCategoryUsageFileWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing application category "
                    + "usage file.");
            exc.printStackTrace();
        }
    }
    
    /**
     * Writes on the output file a line participant,mean_value,standard_deviation
     * for all the days of the week
     * @param participant the name of the participant (IMEI or ALL)
     * @param meanValues the list of mean values of all the days
     * @param standardDeviation the list of standard deviations for each day
     */
    public void writeStressPerDay(String participant, 
            ArrayList<Double> meanValues, ArrayList<Double> standardDeviation) {
        
        if (stressDistributionPerDayFileWriter != null) {
            
            String outputString = participant;

            for (int i = 0; i < meanValues.size(); i++) {
                outputString += "," + 
                        MathUtils.DECIMAL_FORMAT.format(meanValues.get(i)) + "," + 
                        MathUtils.DECIMAL_FORMAT.format(standardDeviation.get(i));
            }

            try {
                stressDistributionPerDayFileWriter.write(outputString);
                stressDistributionPerDayFileWriter.newLine();
                stressDistributionPerDayFileWriter.flush();
            }
            catch(IOException exc) {
                System.out.println("IOException in writing stress values per"
                        + " day");
                exc.printStackTrace();
            }
        }   
    }
    
    /**
     * Writes on the output file a line participant,mean_value,standard_deviation
     * for all the hours of the day
     * @param participant the name of the participant (IMEI or ALL)
     * @param meanValues the list of mean values of all the time ranges
     * @param standardDeviation the list of standard deviations for all the 
     * time ranges
     */
    public void writeStressPerHour(String participant, 
            ArrayList<Double> meanValues, ArrayList<Double> standardDeviation) {
        
        if (stressDistributionPerHoursFileWriter != null) {
            
            String outputString = participant;

            for (int i = 0; i < meanValues.size(); i++) {
                outputString += "," + 
                        MathUtils.DECIMAL_FORMAT.format(meanValues.get(i)) + "," + 
                        MathUtils.DECIMAL_FORMAT.format(standardDeviation.get(i));
            }

            try {
                stressDistributionPerHoursFileWriter.write(outputString);
                stressDistributionPerHoursFileWriter.newLine();
                stressDistributionPerHoursFileWriter.flush();
            }
            catch(IOException exc) {
                System.out.println("IOException in writing stress values per"
                        + " day");
                exc.printStackTrace();
            }
        }
    }
    
    /**
     * Writes on the output file a line participant,mean_value, standard_deviation
     * for all the hours and days of the week
     * @param participant the name of the participant (IMEI or ALL)
     * @param meanValues the list of mean values of all the time ranges and days
     * @param standardDeviation the list of standard deviations
     */
    public void writeStressPerHoursPerDays(String participant, 
            ArrayList<Double> meanValues, ArrayList<Double> standardDeviation) {
        
        if (stressDistributionPerHoursPerDayFileWriter != null) {
            
            String outputString = participant;
            
            for (int i = 0; i < meanValues.size(); i++) {
                
                outputString += "," + 
                        MathUtils.DECIMAL_FORMAT.format(meanValues.get(i)) + 
                        "," + 
                        MathUtils.DECIMAL_FORMAT.format(standardDeviation.get(i));
            }
            
            try {
                stressDistributionPerHoursPerDayFileWriter.write(outputString);
                stressDistributionPerHoursPerDayFileWriter.newLine();
                stressDistributionPerHoursPerDayFileWriter.flush();
            }
            catch(IOException exc) {
                System.out.println("IOException in writing stress value per "
                        + "hours per day");
                exc.printStackTrace();
            }
        }
    }
    
    /**
     * Closes the writer for the stress values of the week
     */
    public void closeWriterStressPerDay() {
        
        try {
            stressDistributionPerDayFileWriter.flush();
            stressDistributionPerDayFileWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing stress distribution per"
                    + " day file writer");
            exc.printStackTrace();
        }
    }
    
    /**
     * Closes the writer for the stress values divided by time ranges
     */
    public void closeWriterStressPerHours() {
        
        try {
            stressDistributionPerHoursFileWriter.flush();
            stressDistributionPerHoursFileWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing stress distribution per"
                    + " hours file writer.");
            exc.printStackTrace();
        }
    }
    
    /**
     * Closes the writer for the stress values divided by time ranges and days
     */
    public void closeWriterStressPerHoursPerDays() {
       
        try {
            stressDistributionPerHoursPerDayFileWriter.flush();
            stressDistributionPerHoursPerDayFileWriter.close();
        }
        catch(IOException exc) {
            System.out.println("IOException in closing stress distribution per"
                    + " hours per days file writer");
            exc.printStackTrace();
        }
    }
}
