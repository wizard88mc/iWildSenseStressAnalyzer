package iwildsensestressanalyzer.userpresenceevent;

/**
 *
 * This class represents and event on the screen, that could be
 * - turn on
 * - turn off
 * - unlocked
 * - rotation_0: normal orientation
 * - rotation_90: phone rotated 90 degrees
 * - rotation_180: phone rotated 180 degrees
 * - rotation_270: phone rotated 270 degrees
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceEvent {
    
    private static enum Event {
        ON, OFF, PRESENT, ROTATION_0, ROTATION_90, ROTATION_180, ROTATION_270
    }
    
    private static final String ON = "ON",
            OFF = "OFF", 
            PRESENT = "PRESENT",
            ROTATION_0 = "ROTATION_0",
            ROTATION_90 = "ROTATION_90",
            ROTATION_180 = "ROTATION_180", 
            ROTATION_270 = "ROTATION_270";
    
    private final long timestamp;
    private final Event event;
    
    public UserPresenceEvent(String line) {
        
        /**
         * Takes the line, split into the two elements, the first one is the 
         * timestamp, while the second is the event on the screen
         */
        String[] elements = line.split(",");
        timestamp = Long.valueOf(elements[0]);
        
        if (elements[1].equals(ON)) {
            event = Event.ON;
        }
        else if (elements[1].equals(OFF)) {
            event = Event.OFF;
        }
        else if (elements[1].equals(PRESENT)) {
            event = Event.PRESENT;
        }
        else if (elements[1].equals(ROTATION_0)) {
            event = Event.ROTATION_0;
        }
        else if (elements[1].equals(ROTATION_90)) {
            event = Event.ROTATION_90;
        }
        else if (elements[1].equals(ROTATION_180)) {
            event = Event.ROTATION_180;
        }
        else if (elements[1].equals(ROTATION_270)) {
            event = Event.ROTATION_270;
        }
        else {
            event = null;
        }
    }
    
    /**
     * Returns the timestamp of the event
     * @return the timestamp
     */
    public long getTimestamp() {
        return this.timestamp;
    }
    
    /**
     * Returns if the event is a screen ON event
     * @return true if is ON event
     */
    public boolean isON() {
        return event == Event.ON;
    }
    
    /**
     * Returns if the event is a screen OFF event
     * @return true if is OFF event
     */
    public boolean isOFF() {
        return event == Event.OFF;
    }
    
    /**
     * Returns if the event is a PRESENT event (screen unlocked)
     * @return true if is PRESENT event
     */
    public boolean isPRESENT() {
        return event == Event.PRESENT;
    }
    
    /**
     * Returns if the event is a ROTATION_0 event (screen in the normal orientation)
     * @return true if is ROTATION_0 event
     */
    public boolean isROTATION_0() {
        return event == Event.ROTATION_0;
    }
    
    /**
     * Returns if the event is a ROTATION_90 event
     * @return true if is ROTATION_90 event
     */
    public boolean isROTATION_90() {
        return event == Event.ROTATION_90;
    }
    
    /**
     * Returns if the event is a ROTATION_180 event
     * @return true if is ROTATION_180 event
     */
    public boolean isROTATION_180() {
        return event == Event.ROTATION_180;
    }
    
    /**
     * Returns if the event is a ROTATION_270 event
     * @return true if is ROTATION_270 event
     */
    public boolean isROTATION_270() {
        return event == Event.ROTATION_270;
    }
    
}
