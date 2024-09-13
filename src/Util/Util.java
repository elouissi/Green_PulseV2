package Util;

import domain.Consomation;
import domain.Enum.TypeConsomation;

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

     public static boolean verifyDatesAndType(LocalDate startDate, LocalDate endDate, List<Consomation> consomations, TypeConsomation typeConsomation) {
        for (Consomation consomation : consomations) {
             if (consomation.getTypeConsomation().equals(typeConsomation)) {
                 for (LocalDate date = consomation.getStartDate(); !date.isAfter(consomation.getEndDate()); date = date.plusDays(1)) {
                    if (!startDate.isAfter(date) && !endDate.isBefore(date)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


}
