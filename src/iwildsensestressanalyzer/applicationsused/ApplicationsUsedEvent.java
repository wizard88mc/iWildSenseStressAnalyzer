package iwildsensestressanalyzer.applicationsused;

import iwildsensestressanalyzer.event.Event;
import iwildsensestressanalyzer.filereader.AllFilesReader;

/**
 *
 * @author Matteo Ciman
 * @version 0.1
 */
public class ApplicationsUsedEvent extends Event {
    
    private static enum Importance {
        FOREGROUND, 
        VISIBLE, 
        PERCEPTIBLE, 
        BACKGROUND, 
        SERVICE, 
        EMPTY
    };
    
    private static final String FOREGROUND = "FOREGROUND", 
            VISIBLE  = "VISIBLE", 
            PERCEPTIBLE = "PERCEPTIBLE",
            BACKGROUND = "BACKGROUND",
            SERVICE = "SERVICE",
            EMPTY = "EMPTY";
    
    private final String app; // App name (Java package name)
    private final String UID; // UID assigned to the app at installation time
    private final String PID; // PID (process ID) of the main process of the app
    private final boolean hasInternetAccess; // true if the app has internet access
    private final Importance importance; // new state of the app
    private boolean lastOfDay = false;
    
    public ApplicationsUsedEvent(String line) {
        
        String[] elements = line.split(",");
        /**
         * [0]: timestamp
         * [1]: app name
         * [2]: uid
         * [3]: pid
         * [4]: app_internet_access
         * [5]: importance
         */
        
        timestamp = Long.valueOf(elements[0]);
        app = elements[1]; UID = elements[2]; PID = elements[3];
        if (elements[4].equals("true")) {
            hasInternetAccess = true;
        }
        else {
            hasInternetAccess = false;
        }
        
        if (elements[5].equals(FOREGROUND)) {
            importance = Importance.FOREGROUND;
        }
        else if (elements[5].equals(VISIBLE)) {
            importance = Importance.VISIBLE;
        }
        else if (elements[5].equals(PERCEPTIBLE)) {
            importance = Importance.PERCEPTIBLE;
        }
        else if (elements[5].equals(BACKGROUND)) {
            importance = Importance.BACKGROUND;
        }
        else if (elements[5].equals(SERVICE)) {
            importance = Importance.SERVICE;
        }
        else if (elements[5].equals(EMPTY)) {
            importance = Importance.EMPTY;
        }
        else  {
            importance = null;
        }
        
        /**
         * Checking if the current event is the last one of the day
         */
        if (line.contains(AllFilesReader.LAST_LINE_MARKER)) {
            lastOfDay = true;
        }
    }
    
    public void setLastOfDay() {
        this.lastOfDay = true;
    }
    
    /**
     * Returns the Java package name of the app
     * @return the app name
     */
    public String getApp() {
        return this.app;
    }
    
    /**
     * Returns the UID (User ID) of the app. It is the identifier assigned to every
     * application at installation time
     * @return the UID of the app
     */
    public String getUID() {
        return this.UID;
    }
    
    /**
     * Returns the PID (process ID) of the main process of the app
     * @return the PID of the app
     */
    public String getPID() {
        return this.PID;
    }
    
    /**
     * Returns if the app has internet access or not
     * @return true if the app has internet access
     */
    public boolean hasInternetAccess() {
        return this.hasInternetAccess;
    }
    
    /**
     * Returns if the current event corresponds to a FOREGROUND importance event
     * @return true if importance is FOREGROUND
     */
    public boolean isFOREGROUND() {
        return this.importance == Importance.FOREGROUND;
    }
    
    /**
     * Returns if the current event corresponds to a VISIBLE importance event
     * @return true if importance is VISIBLE
     */
    public boolean isVISIBLE() {
        return this.importance == Importance.VISIBLE;
    }
    
    /**
     * Returns if the current event corresponds to a PERCEPTIBLE importance event
     * @return true if importance is PERCEPTIBLE
     */
    public boolean isPERCEPTIBLE() {
        return this.importance == Importance.PERCEPTIBLE;
    }
    
    /**
     * Returns if the current event corresponds to a BACKGROUND importance event
     * @return true if importance is BACKGROUND
     */
    public boolean isBACKGROUND() {
        return this.importance == Importance.BACKGROUND;
    }
    
    /**
     * Returns if the current event corresponds to a SERVICE importance event
     * @return true if importance is SERVICE
     */
    public boolean isSERVICE() {
        return this.importance == Importance.SERVICE;
    }
    
    /**
     * Returns if the current event corresponds to a EMPTY importance event
     * @return true if importance is EMPTY
     */
    public boolean isEMPTY() {
        return this.importance == Importance.EMPTY;
    }
    
    /**
     * Returns if the current event is the last recorded event of the day
     * @return 
     */
    public boolean isLastDay() {
        return this.lastOfDay;
    }
}
