package iwildsensestressanalyzer.event;

/**
 *
 * Base class for each event recorder by the application
 * 
 * @author Matteo Ciman
 * @version 1.0
 */
public class Event {
    
    protected long timestamp; // timestamp of the event
    
    /**
     * Returns the timestamp the event is collected
     * @return the timestamp of the event
     */
    public long getTimestamp() {
        return this.timestamp;
    }
}
