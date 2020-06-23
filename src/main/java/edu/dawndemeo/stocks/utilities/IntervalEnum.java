package edu.dawndemeo.stocks.utilities;

import java.time.LocalDate;
import java.time.Period;

/**
 * Stock history can be pulled daily, weekly, or monthly.
 *
 * @author dawndemeo
 */
public enum IntervalEnum {
    DAILY, WEEKLY, MONTHLY;

    IntervalEnum() {
    }

    /**
     * This method determines if a date falls on a given interval of another date, i.e. it is one
     * day, one week, or one month (30 days) later.
     *
     * @param testDate  the date being tested
     * @param startDate the origin date being compared to
     * @param interval  the desired interval - daily, weekly, monthly (30 days)
     * @return true if test date falls on interval
     */
    public static boolean isOfInterval(LocalDate testDate, LocalDate startDate,
                                       IntervalEnum interval) {

        switch (interval) {
            case WEEKLY:
                return testDate.getDayOfWeek().equals(startDate.getDayOfWeek());
            case MONTHLY:
                // monthly is considered to be 30 days,
                // since not all months have the same number of days
                if (testDate == startDate) {
                    return true;
                } else {
                    return Period.between(testDate, startDate).getDays() % 30 == 0;
                }
            case DAILY:
            default:
                return true;
        }
    }
}
