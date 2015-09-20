package iwildsensestressanalyzer.touches;

import iwildsensestressanalyzer.event.Event;
import java.util.ArrayList;

/**
 *
 * Collects statistics about the intervals between screen touches during an 
 * usage session
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class TouchesBufferedEvent extends Event {

    private final int counter; // number of touches 
    private final double minInterval; // minimum touch interval (time between two consecutive touches) in milliseconds
    private final double maxInterval; // maximum touch interval (time between two consecutive touches) in milliseconds
    private final double range; // range of touch intervals
    private final double mean; // mean of touch intervals
    private final double median; // median of touch intervals
    private final double variance; // variance of touch intervals
    private final double standardDeviation; // standard deviation of touch intervals
    private final double sessionDuration; // duration of the usage session
    
    public TouchesBufferedEvent(String line) {
        
        String[] elements = line.split(",");
        /**
         * [0]: timestamp
         * [1]: counter
         * [2]: min_interval
         * [3]: max_interval
         * [4]: range
         * [5]: mean
         * [6]: median
         * [7]: variance
         * [8]: standardDeviation
         * [9]: sessionDuration
         */
        
        timestamp = Long.valueOf(elements[0]);
        counter = Integer.valueOf(elements[1]);
        minInterval = Double.valueOf(elements[2]);
        maxInterval = Double.valueOf(elements[3]);
        range = Double.valueOf(elements[4]);
        mean = Double.valueOf(elements[5]);
        median = Double.valueOf(elements[6]);
        variance = Double.valueOf(elements[7]);
        standardDeviation = Double.valueOf(elements[8]);
        sessionDuration = Double.valueOf(elements[9]);
    }
    
    /**
     * Returns the number of touches during the usage session
     * @return the number of touches
     */
    public int getCounter() {
        return this.counter;
    }
    
    /**
     * Returns the minimum touch interval (time between two consecutive touches)
     * in milliseconds
     * @return the minimum touch interval
     */
    public double getMinInterval() {
        return this.minInterval;
    }
    
    /**
     * Returns the maximum touch interval (time between two consecutive touches)
     * in milliseconds
     * @return the maximum touch interval
     */
    public double getMaxInterval() {
        return this.maxInterval;
    }
    
    /**
     * Returns the range of touch intervals (max - min interval)
     * @return the range of touch intervals
     */
    public double getRange() {
        return this.range;
    }
    
    /**
     * Returns the mean of touch intervals
     * @return the mean of touch intervals
     */
    public double getMean() {
        return this.mean;
    }
    
    /**
     * Returns the median of touch intervals
     * @return the median of touch intervals
     */
    public double getMedian() {
        return this.median;
    }
    
    /**
     * Returns the variance of touch intervals
     * @return the variance of touch intervals
     */
    public double getVariance() {
        return this.variance;
    }
    
    /**
     * Returns the standard deviation of touch intervals
     * @return the standard deviation of touch intervals
     */
    public double getStandardDeviation() {
        return this.standardDeviation;
    }
    
    /**
     * Returns the duration of the usage session in milliseconds
     * @return the duration of the usage session
     */
    public double getSessionDuration() {
        return this.sessionDuration;
    }
    
    /**
     * Creates a list of TouchesBuffered events that will be added to the 
     * UnlockedScreen events
     * @param lines the list of lines from the file
     * @return a list of TouchesBufferedEvent
     */
    public static ArrayList<TouchesBufferedEvent> createListOfTouchesBufferedEvents(ArrayList<String> lines) {
        
        ArrayList<TouchesBufferedEvent> listEvents = 
                new ArrayList<TouchesBufferedEvent>();
        
        for (String line: lines) {
            listEvents.add(new TouchesBufferedEvent(line));
        }
        
        return listEvents;
    }
    
}
