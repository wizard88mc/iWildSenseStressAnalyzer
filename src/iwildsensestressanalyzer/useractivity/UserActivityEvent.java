package iwildsensestressanalyzer.useractivity;

import iwildsensestressanalyzer.event.Event;

/**
 *
 * This class describes the current activity of the user. It is sampled every 
 * minute and is an info provided directly from Google API
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class UserActivityEvent extends Event {
    
    protected static enum Activity {
        IN_VEHICLE, // The device is in a vehicle, like a car 
        ON_BICYCLE, // The device is on bicycle
        ON_FOOT, // The device is on a user who is walking or running
        RUNNING, // The device is on a user who is running
        STILL, // The device is still (not moving)
        TILTING, // The device angle relative to gravity changed significantly (shacking)
        UNKNOWN, // Unable to detect current activity
        WALKING // The device is on a user who is walking
    }
    
    private static final String IN_VEHICLE = "IN_VEHICLE", 
            ON_BICYCLE = "ON_BICYCLE",
            ON_FOOT = "ON_FOOT",
            RUNNING = "RUNNING",
            STILL = "STILL",
            TILTING = "TILTING",
            UNKNOWN = "UNKNOWN",
            WALKING = "WALKING"; 

    private final Activity activity;
    
    /**
     * Creates an UserActivityEvent from the line of the file taking the 
     * timestamp and the detected activity
     * @param line 
     */
    public UserActivityEvent(String line) {
        
        String[] elements = line.split(",");
        
        timestamp = Long.valueOf(elements[0]);
        
        if (elements[1].equals(IN_VEHICLE)) {
            activity = Activity.IN_VEHICLE;
        }
        else if (elements[1].equals(ON_BICYCLE)) {
            activity = Activity.ON_BICYCLE;
        }
        else if (elements[1].equals(ON_FOOT)) {
            activity = Activity.ON_FOOT;
        }
        else if (elements[1].equals(RUNNING)) {
            activity = Activity.RUNNING;
        }
        else if (elements[1].equals(STILL)) {
            activity = Activity.STILL;
        }
        else if (elements[1].equals(TILTING)) {
            activity = Activity.TILTING;
        }
        else if (elements[1].equals(WALKING)) {
            activity = Activity.WALKING;
        }
        else if (elements[1].equals(UNKNOWN)) {
            activity = Activity.UNKNOWN;
        }
        else {
            activity = Activity.UNKNOWN;
            System.out.println("** Error in UserActivityEvent: not recognized string **");
        }
    }
    
    /**
     * Returns if the current activity is IN_VEHICLE
     * @return true if IN_VEHICLE
     */
    public boolean isIN_VEHICLE() {
        return activity == Activity.IN_VEHICLE;
    }
    
    /**
     * Returns if the current activity is ON_BICYCLE
     * @return true if ON_BICYCLE
     */
    public boolean isON_BICYCLE() {
        return activity == Activity.ON_BICYCLE;
    }
    
    /**
     * Returns if the current activity is ON_FOOT
     * @return true if ON_FOOT
     */
    public boolean isON_FOOT() {
        return activity == Activity.ON_FOOT;
    }
    
    /**
     * Returns if the current activity is RUNNING
     * @return true if RUNNING
     */
    public boolean isRUNNING() {
        return activity == Activity.RUNNING;
    }
    
    /**
     * Returns if the current activity is STILL
     * @return true if STILL
     */
    public boolean isSTILL() {
        return activity == Activity.STILL;
    }
    
    /**
     * Returns if the current activity is TILTING
     * @return true if TILTING
     */
    public boolean isTILTING() {
        return activity == Activity.TILTING;
    }
    
    /**
     * Returns if the current activity is UNKNOWN
     * @return true if UNKNOWN
     */
    public boolean isUNKNOWN() {
        return activity == Activity.UNKNOWN;
    }
    
    /**
     * Returns if the current activity is WALKING
     * @return true if WALKING
     */
    public boolean isWALKING() {
        return activity == Activity.WALKING;
    }
    
    /**
     * Returns the activity of the event
     * @return the activity of the event
     */
    public Activity getActivity() {
        return this.activity;
    }
    
}
