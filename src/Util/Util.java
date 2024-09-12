package Util;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public static List<LocalDate> dateListRange(LocalDate startDate , LocalDate endDate){
        List<LocalDate> dateListRange = new ArrayList<>();
        for(LocalDate dateTest = startDate; !dateTest.isAfter(endDate); dateTest=dateTest.plusDays(1)){
            dateListRange.add(dateTest);

        }
        return dateListRange;
    }



}
