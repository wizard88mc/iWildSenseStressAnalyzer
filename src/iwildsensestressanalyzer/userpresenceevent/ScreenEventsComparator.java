package iwildsensestressanalyzer.userpresenceevent;

import java.util.Comparator;

/**
 * Comparator for two Screen events
 * @author Matteo Ciman
 */
public class ScreenEventsComparator implements Comparator<ScreenOnOff> {
    @Override
    public int compare(ScreenOnOff o1, ScreenOnOff o2) {
        return Long.valueOf(o1.getOnTimestamp()).compareTo(o2.getOffTimestamp());
    }
}
