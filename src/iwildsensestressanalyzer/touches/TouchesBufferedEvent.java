package iwildsensestressanalyzer.touches;

import iwildsensestressanalyzer.event.Event;

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
    private final long minInterval; // minimum touch interval (time between two consecutive touches) in milliseconds
    private final long maxInterval; // maximum touch interval (time between two consecutive touches) in milliseconds
    private final long range; // range of touch intervals
    private final long mean; // mean of touch intervals
    private final long median; // median of touch intervals
    private final long variance; // variance of touch intervals
    private final long standardDeviation; // standard deviation of touch intervals
    private final long sessionDuration; // duration of the usage session
    
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
        minInterval = Long.valueOf(elements[2]);
        maxInterval = Long.valueOf(elements[3]);
        range = Long.valueOf(elements[4]);
        mean = Long.valueOf(elements[5]);
        median = Long.valueOf(elements[6]);
        variance = Long.valueOf(elements[7]);
        standardDeviation = Long.valueOf(elements[8]);
        sessionDuration = Long.valueOf(elements[9]);
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
    public long getMinInterval() {
        return this.minInterval;
    }
    
    /**
     * Returns the maximum touch interval (time between two consecutive touches)
     * in milliseconds
     * @return the maximum touch interval
     */
    public long getMaxInterval() {
        return this.maxInterval;
    }
    
    /**
     * Returns the range of touch intervals (max - min interval)
     * @return the range of touch intervals
     */
    public long getRange() {
        return this.range;
    }
    
    /**
     * Returns the mean of touch intervals
     * @return the mean of touch intervals
     */
    public long getMean() {
        return this.mean;
    }
    
    /**
     * Returns the median of touch intervals
     * @return the median of touch intervals
     */
    public long getMedian() {
        return this.median;
    }
    
    /**
     * Returns the variance of touch intervals
     * @return the variance of touch intervals
     */
    public long getVariance() {
        return this.variance;
    }
    
    /**
     * Returns the standard deviation of touch intervals
     * @return the standard deviation of touch intervals
     */
    public long getStandardDeviation() {
        return this.standardDeviation;
    }
    
    /**
     * Returns the duration of the usage session in milliseconds
     * @return the duration of the usage session
     */
    public long getSessionDuration() {
        return this.sessionDuration;
    }
    
}
