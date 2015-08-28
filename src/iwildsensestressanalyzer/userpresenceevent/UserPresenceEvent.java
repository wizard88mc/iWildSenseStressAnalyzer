package iwildsensestressanalyzer.userpresenceevent;

import iwildsensestressanalyzer.event.Event;

/**
 *
 * This class represents and event on the screen, that could be
 * - turn on: ON
 * - turn off: OFF
 * - unlocked: PRESENT 
 * - rotation_0: normal orientation: ROTATION_0
 * - rotation_90: phone rotated 90 degrees: ROTATION_90
 * - rotation_180: phone rotated 180 degrees: ROTATION_180
 * - rotation_270: phone rotated 270 degrees: ROTATION_270
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceEvent extends Event {
    
    private static enum ScreenEvent {
        ON, OFF, PRESENT, ROTATION_0, ROTATION_90, ROTATION_180, ROTATION_270
    }
    
    private static final String ON = "ON",
            OFF = "OFF", 
            PRESENT = "PRESENT",
            ROTATION_0 = "ROTATION_0",
            ROTATION_90 = "ROTATION_90",
            ROTATION_180 = "ROTATION_180", 
            ROTATION_270 = "ROTATION_270";
    
    private final ScreenEvent event;
    
    public UserPresenceEvent(String line) {
        
        /**
         * Takes the line, split into the two elements, the first one is the 
         * timestamp, while the second is the event on the screen
         */
        String[] elements = line.split(",");
        timestamp = Long.valueOf(elements[0]);
        
        if (elements[1].equals(ON)) {
            event = ScreenEvent.ON;
        }
        else if (elements[1].equals(OFF)) {
            event = ScreenEvent.OFF;
        }
        else if (elements[1].equals(PRESENT)) {
            event = ScreenEvent.PRESENT;
        }
        else if (elements[1].equals(ROTATION_0)) {
            event = ScreenEvent.ROTATION_0;
        }
        else if (elements[1].equals(ROTATION_90)) {
            event = ScreenEvent.ROTATION_90;
        }
        else if (elements[1].equals(ROTATION_180)) {
            event = ScreenEvent.ROTATION_180;
        }
        else if (elements[1].equals(ROTATION_270)) {
            event = ScreenEvent.ROTATION_270;
        }
        else {
            event = null;
        }
    }
    
    /**
     * Returns if the event is a screen ON event
     * @return true if is ON event
     */
    public boolean isON() {
        return event == ScreenEvent.ON;
    }
    
    /**
     * Returns if the event is a screen OFF event
     * @return true if is OFF event
     */
    public boolean isOFF() {
        return event == ScreenEvent.OFF;
    }
    
    /**
     * Returns if the event is a PRESENT event (screen unlocked)
     * @return true if is PRESENT event
     */
    public boolean isPRESENT() {
        return event == ScreenEvent.PRESENT;
    }
    
    /**
     * Returns if the event is a ROTATION_0 event (screen in the normal orientation)
     * @return true if is ROTATION_0 event
     */
    public boolean isROTATION_0() {
        return event == ScreenEvent.ROTATION_0;
    }
    
    /**
     * Returns if the event is a ROTATION_90 event
     * @return true if is ROTATION_90 event
     */
    public boolean isROTATION_90() {
        return event == ScreenEvent.ROTATION_90;
    }
    
    /**
     * Returns if the event is a ROTATION_180 event
     * @return true if is ROTATION_180 event
     */
    public boolean isROTATION_180() {
        return event == ScreenEvent.ROTATION_180;
    }
    
    /**
     * Returns if the event is a ROTATION_270 event
     * @return true if is ROTATION_270 event
     */
    public boolean isROTATION_270() {
        return event == ScreenEvent.ROTATION_270;
    }
    
}
