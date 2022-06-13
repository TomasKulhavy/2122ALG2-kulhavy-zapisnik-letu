package app;

import java.util.Comparator;

public class ComparatorByDate implements Comparator<Flight> {
    @Override
    public int compare(Flight o1, Flight o2) {
        int result = o1.getDate().compareTo(o2.getDate());
        result = ((-1) * result);
        if (0 == result) {
            result = o1.getTakeoffTime().compareTo(o2.getTakeoffTime());
        }
        return result;
    }
}