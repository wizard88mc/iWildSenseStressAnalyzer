package iwildsensestressanalyzer.light;

import iwildsensestressanalyzer.event.Event;

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
    
    public double getLightValue() {
        return this.light;
    }
}
