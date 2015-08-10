package iwildsensestressanalyzer.activityservservice;

import java.util.ArrayList;

/**
 *
 * This class stores information about the ActivityServService event that is
 * sampled every 60 seconds
 * 
 * @author Matteo Ciman
 * @version 0.1
 */
public class ActivityServServiceEvent {
   
    public static enum State {
        STARTED, FOREGROUND, PERSISTENT, SYSTEM
    }
    
    private static final String STARTED = "STARTED",
            FOREGROUND = "FOREGROUND",
            PERSISTENT = "PERSISTENT",
            SYSTEM = "SYSTEM";
    
    private final long timestamp; // timestamp of the event
    private final String service; // Name of the service (Java class name)
    private final String parentProcess; // Name of the process that started the service
    private final String UID; // userID of the application the service belongs to.
    private final String PID; // process ID of the parent process of the service
    private final long activeSince; // alive time of the service
    private final ArrayList<State> states; // current state of the service
    private final boolean foreground; // true if the process is in foreground
    private final boolean serviceInternetAccess; // true if service has internet access
    private final int clientCount; // number of clients connected to the service
    private final int crashCount; // number of time the service crashed
    private final long lastActive; // The time the service was last active
    
    public ActivityServServiceEvent(String line) {
        
        /**
         * Since if the state of a service is more than one there is a comma (,)
         * that separates these states, it is necessary to change that comma
         * in something different to avoid any kind of problems when splitting
         * the line entry
         */
        if (line.contains("\"")) {
            /**
             * First find the location on the string of the first and the second
             * (last) occurrence of the "
             */
            int indexFirstLocation = line.indexOf("\""),
                    indexLastLocation = line.lastIndexOf("\"");
            
            /**
             * Need to divide the line into three pieces:
             * 1) before the state array
             * 2) the state array with commas
             * 3) after the state array
             */
            String firstPart = line.substring(0, indexFirstLocation),
                    secondPart = line.substring(indexFirstLocation, indexLastLocation),
                    thirdPart = line.substring(indexLastLocation);
            
            secondPart = secondPart.replace(",", "-");
            
            line = firstPart.concat(secondPart).concat(thirdPart);
        }
        
        String[] elements = line.split(",");
        /**
         * [0]: timestamp
         * [1]: service
         * [2]: parentProcess
         * [3]: UID
         * [4]: PID
         * [5]: activeSince
         * [6]:[state,..]
         * [7]: foreground (true, false)
         * [8]: serviceInternetAccess (true, false)
         * [9]: clientCount
         * [10]: crashCount
         * [11]: lastActive
         */
        
        timestamp = Long.valueOf(elements[0]);
        service = elements[1];
        parentProcess = elements[2];
        UID = elements[3];
        PID = elements[4];
        activeSince = Long.valueOf(elements[5]);
        states = new ArrayList<State>();
        if (elements[6].contains(STARTED)) {
            states.add(State.STARTED);
        }
        if (elements[6].contains(FOREGROUND)) {
            states.add(State.FOREGROUND);
        }
        if (elements[6].contains(PERSISTENT)) {
            states.add(State.PERSISTENT);
        }
        if (elements[6].contains(SYSTEM)) {
            states.add(State.SYSTEM);
        }
        
        foreground = Boolean.valueOf(elements[7]);
        serviceInternetAccess = Boolean.valueOf(elements[8]);
        clientCount = Integer.valueOf(elements[9]);
        crashCount = Integer.valueOf(elements[10]);
        lastActive = Long.valueOf(elements[11]);
    }
    
    /**
     * Returns the timestamp the event is collected
     * @return the timestamp of the event
     */
    public long getTimestamp() {
        return this.timestamp;
    }
    
    /**
     * Returns the name of the service. Full Java class name
     * @return the name of the service
     */
    public String getService() {
        return this.service;
    }
    
    /**
     * Returns the name of the process that started the service
     * @return the name of the process that started the service
     */
    public String getParentProcess() {
        return this.parentProcess;
    }
    
    /**
     * Returns the UID (user ID) of the application the service belongs to. 
     * The UID is an identifier that is assigned to every application at
     * installation time
     * @return the UID of the application the service belongs to
     */
    public String getUID() {
        return this.UID;
    }
    
    /**
     * Returns the PID (process ID) of the parent process of the service
     * @return the PID of the parent process of the service
     */
    public String getPID() {
        return this.PID;
    }
    
    /**
     * Returns the alive time of the service
     * @return the alive time of the service
     */
    public long getActiveSince() {
        return this.activeSince;
    }
    
    /**
     * Returns the list of current states of the service. It is an array 
     * containing zero, one, or more State elements
     * @return the list of States of the service
     */
    public ArrayList<State> getStates() {
        return this.states;
    }
    
    /**
     * Returns if the service is in the foreground or not
     * @return true if the service is in foreground
     */
    public boolean isForeground() {
        return this.foreground;
    }
    
    /**
     * Returns if the service has internet access or not 
     * @return true if the service has internet access
     */
    public boolean hasServiceInternetAccess() {
        return this.serviceInternetAccess;
    }
    
    /**
     * Returns the number of clients connected/bound to the service
     * @return the number of clients connected/bound to the service
     */
    public int getClientCount() {
        return this.clientCount;
    }
    
    /**
     * Returns the number of times the service crashed
     * @return the number of times the service crashed
     */
    public int getCrashCount() {
        return this.crashCount;
    }
    
    /**
     * Returns the time the service was last active
     * @return the time the service was last active
     */
    public long getLastActive() {
        return this.lastActive;
    }
}
