package Util;

import java.time.LocalDate;
import java.util.List;

public class Util {

    public static boolean verifydates(LocalDate StDate, LocalDate EnDate, List<LocalDate> arageDates) {
        for (LocalDate date = StDate; !date.isAfter(EnDate); date = date.plusDays(1)) {
            if (arageDates.contains(date)) {
                return false;
            }
        }
        return true;
    }


}
