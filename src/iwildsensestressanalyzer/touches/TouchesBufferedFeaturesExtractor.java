package iwildsensestressanalyzer.touches;

import iwildsensestressanalyzer.userpresenceevent.UnlockedScreen;
import java.util.ArrayList;

/**
 * Extract features from TouchesBuffered events
 * 
 * Features extracted are the values already collected by the ping application
 * 
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class TouchesBufferedFeaturesExtractor {
    
    private final ArrayList<UnlockedScreen> unlockedScreenEvents;
    
    public TouchesBufferedFeaturesExtractor(ArrayList<UnlockedScreen> unlockedScreenEvents) {
        this.unlockedScreenEvents = unlockedScreenEvents;
    }
    
    /**
     * Returns a list with all the counter values of the TouchesBufferedEvent
     * @return list of double with TouchesBufferedEvent counter values
     */
    public ArrayList<Double> getAllCounters() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(Double.valueOf(event.getTouchesBufferedEvent().getCounter()));
        }
        
        return values;
    }
    
    /**
     * Returns a list with all the minimum touch interval between two consecutive
     * touches
     * @return list of double with TouchesBufferedEvent minimum interval values 
     */
    public ArrayList<Double> getAllMinIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(event.getTouchesBufferedEvent().getMinInterval());
        }
        
        return values;
    }
    
    /**
     * Returns a list with all the maximum touch interval between two consecutive
     * touches
     * @return list of double with TouchesBufferedEvent maximum interval values 
     */
    public ArrayList<Double> getAllMaxIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(event.getTouchesBufferedEvent().getMaxInterval());
        }
        
        return values;
    }
    
    /**
     * Returns a list with all the ranges of touch intervals
     * @return list of double with TouchesBufferedEvent range values
     */
    public ArrayList<Double> getAllRanges() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(event.getTouchesBufferedEvent().getRange());
        }
        
        return values;
    }
    
    /**
     * Returns a list with all the mean of touch intervals
     * @return list of double with TouchesBufferedEvent mean of touch intervals 
     * values
     */
    public ArrayList<Double> getAllMeansOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(event.getTouchesBufferedEvent().getMean());
        }
        
        return values;
    }
    
    /**
     * Returns a list with all the median values of touch intervals
     * @return list of double with TouchesBufferedEvent median of touch intervals
     */
    public ArrayList<Double> getAllMediansOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(event.getTouchesBufferedEvent().getMedian());
        }
        
        return values;
    }
    
    /**
     * Returns a list with all the variance values of touch intervals
     * @return list of double with TouchesBufferedEvent variance of touch intervals
     */
    public ArrayList<Double> getAllVariancesOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(event.getTouchesBufferedEvent().getVariance());
        }
        
        return values;
    }
    
    /**
     * Returns a list with all the standard deviation values of touch intervals
     * @return list of double with TouchesBufferedEvent standard deviation of 
     * touch intervals
     */
    public ArrayList<Double> getAllStandardDeviationsOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(event.getTouchesBufferedEvent().getStandardDeviation());
        }
        
        return values;
    }
    
    /**
     * Returns a list with all the session duration values of the usage session
     * @return list of double with TouchesBufferedEvent session duration
     */
    public ArrayList<Double> getAllSessionDurationsOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            values.add(event.getTouchesBufferedEvent().getSessionDuration());
        }
        
        return values;
    }
}
