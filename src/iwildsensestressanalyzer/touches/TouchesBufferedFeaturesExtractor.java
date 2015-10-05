package iwildsensestressanalyzer.touches;

import iwildsensestressanalyzer.userpresenceevent.UnlockedScreen;
import iwildsensestressanalyzer.utils.MathUtils;
import java.util.ArrayList;

/**
 * Extract features from TouchesBuffered events
 * 
 * Features extracted are the values already collected by the ping application:
 * - counter: number of touches during usage session
 * - min_interval: minimum touch interval (time between two consecutive touches)
 * - max_interval: maximum touch interval (time between two consecutive touches)
 * - range: range of touch intervals (max_interval - min_interval)
 * - mean: mean of touch intervals
 * - median: median of touch intervals
 * - variance: variance of touch intervals
 * - standard_deviation: standard deviation of touch intervals
 * - session_duration: duration of the usage session
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
            if (event.getTouchesBufferedEvent() != null) {
                values.add(Double.valueOf(event.getTouchesBufferedEvent().getCounter()));
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about counter values
     * @return [average, variance, standard deviation] if there is at least one 
     * value, null otherwise
     */
    public Double[] calculateStatisticsCounterValues() {
        
        ArrayList<Double> values = getAllCounters();
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
        
    }
    
    /**
     * Returns a list with all the minimum touch interval between two consecutive
     * touches
     * @return list of double with TouchesBufferedEvent minimum interval values 
     */
    public ArrayList<Double> getAllMinIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getTouchesBufferedEvent() != null) {
                values.add(event.getTouchesBufferedEvent().getMinInterval());
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about min_intervals
     * @return [average, variance, standard_deviation] if there is at least one
     * value, null otherwise
     */
    public Double[] calculateStatisticsMinIntervalValues() {
        
        ArrayList<Double> values = getAllMinIntervals();
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
        
    }
    
    /**
     * Returns a list with all the maximum touch interval between two consecutive
     * touches
     * @return list of double with TouchesBufferedEvent maximum interval values 
     */
    public ArrayList<Double> getAllMaxIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getTouchesBufferedEvent() != null) {
                values.add(event.getTouchesBufferedEvent().getMaxInterval());
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about max_intervals
     * @return [average, variance, standard_deviation] if there is at least one
     * value, null otherwise
     */
    public Double[] calculateStatisticsMaxIntervalValues() {
        
        ArrayList<Double> values = getAllMaxIntervals();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list with all the ranges of touch intervals
     * @return list of double with TouchesBufferedEvent range values
     */
    public ArrayList<Double> getAllRanges() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getTouchesBufferedEvent() != null) {
                values.add(event.getTouchesBufferedEvent().getRange());
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about range values
     * @return [average, variance, standard_deviation] if there is at least one
     * value, null otherwise
     */
    public Double[] calculateStatisticsRangeValues() {
        
        ArrayList<Double> values = getAllRanges();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list with all the mean of touch intervals
     * @return list of double with TouchesBufferedEvent mean of touch intervals 
     * values
     */
    public ArrayList<Double> getAllMeansOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getTouchesBufferedEvent() != null) {
                values.add(event.getTouchesBufferedEvent().getMean());
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about mean values of touch intervals
     * @return [average, variance, standard_deviation] if there is at least one 
     * value, null otherwise
     */
    public Double[] calculateStatisticsMeanValues() {
        
        ArrayList<Double> values = getAllMeansOfTouchIntervals();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list with all the median values of touch intervals
     * @return list of double with TouchesBufferedEvent median of touch intervals
     */
    public ArrayList<Double> getAllMediansOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getTouchesBufferedEvent() != null) {
                values.add(event.getTouchesBufferedEvent().getMedian());
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about median values of touch intervals
     * @return [average, variance, standard_deviation] if there is at least one 
     * value, null otherwise
     */
    public Double[] calculateStatisticsMedianValues() {
        
        ArrayList<Double> values = getAllMediansOfTouchIntervals();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list with all the variance values of touch intervals
     * @return list of double with TouchesBufferedEvent variance of touch intervals
     */
    public ArrayList<Double> getAllVariancesOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getTouchesBufferedEvent() != null) {
                values.add(event.getTouchesBufferedEvent().getVariance());
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about variance values of touch intervals
     * @return [average, variance, standard_deviation] if there is at least one
     * value, null otherwise
     */
    public Double[] calculateStatisticsVarianceValues() {
        
        ArrayList<Double> values = getAllVariancesOfTouchIntervals();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list with all the standard deviation values of touch intervals
     * @return list of double with TouchesBufferedEvent standard deviation of 
     * touch intervals
     */
    public ArrayList<Double> getAllStandardDeviationsOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getTouchesBufferedEvent() != null) {
                values.add(event.getTouchesBufferedEvent().getStandardDeviation());
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about standard deviation values
     * of touch intervals
     * @return [average, variance, standard_deviation] if there is at least one
     * value, null otherwise
     */
    public Double[] calculateStatisticsStandardDeviationValues() {
        
        ArrayList<Double> values = getAllStandardDeviationsOfTouchIntervals();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
    
    /**
     * Returns a list with all the session duration values of the usage session
     * @return list of double with TouchesBufferedEvent session duration
     */
    public ArrayList<Double> getAllSessionDurationsOfTouchIntervals() {
        
        ArrayList<Double> values = new ArrayList<Double>();
        
        for (UnlockedScreen event: unlockedScreenEvents) {
            if (event.getTouchesBufferedEvent() != null) {
                values.add(event.getTouchesBufferedEvent().getSessionDuration());
            }
        }
        
        return values;
    }
    
    /**
     * Calculates statistics information about session duration values of 
     * touch intervals
     * @return [average, variance, standard_deviation] if there is at least one
     * value, null otherwise
     */
    public Double[] calculateStatisticsSessionDurationValues() {
    
        ArrayList<Double> values = getAllSessionDurationsOfTouchIntervals();
        
        if (values != null && !values.isEmpty()) {
            return MathUtils.calculateStatisticInformation(values);
        }
        else {
            return null;
        }
    }
}
