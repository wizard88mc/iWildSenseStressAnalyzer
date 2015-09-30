package iwildsensestressanalyzer.light;

import iwildsensestressanalyzer.event.Event;
import java.util.ArrayList;

/**
 *
 * Class for the event that registers the light value when the screen 
 * fires an ON event
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserPresenceLightEvent extends Event {
    
    private final double light;
    
    public UserPresenceLightEvent(String line) {
        
        String[] elements = line.split(",");
        /**
         * [0]: timestamp
         * [1]: sensor (always 5)
         * [2]: light value
         * [4]: -
         * [5]: -
         */
        this.timestamp = Long.valueOf(elements[0]);
        this.light = Double.valueOf(elements[2]);
    }
    
    /**
     * Returns the light value related to the event
     * @return light value as double
     */
    public double getLightValue() {
        return this.light;
    }
    
    /**
     * Creates a list of UserPresenceLightEvent stanrting from the list of lines
     * recorded
     * @param lines the list of lines with the recorded event
     * @return a list of UserPresenceLightEvent objects
     */
    public static ArrayList<UserPresenceLightEvent> 
        createListOfUserPresenceLightEvents(ArrayList<String> lines) {
        
        ArrayList<UserPresenceLightEvent> events = 
                new ArrayList<UserPresenceLightEvent>();

        for (String line: lines) {
            events.add(new UserPresenceLightEvent(line));
        }
            
        return events;
    }
}
