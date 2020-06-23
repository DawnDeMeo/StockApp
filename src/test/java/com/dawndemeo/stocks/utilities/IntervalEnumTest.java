package com.dawndemeo.stocks.utilities;

import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class IntervalEnumTest {

    @Test
    public void testIsOfDailyInterval() {
        LocalDate firstDate = LocalDate.of(2019, Month.APRIL, 30);
        LocalDate secondDate = LocalDate.of(2019, Month.MAY, 1);
        assertTrue("Verify consecutive dates are daily interval",
                IntervalEnum.isOfInterval(firstDate, secondDate, IntervalEnum.DAILY));
    }

    @Test
    public void testIsOfWeeklyInterval() {
        LocalDate firstDate = LocalDate.of(2019, Month.APRIL, 1);
        LocalDate secondDate = LocalDate.of(2019, Month.APRIL, 8);
        assertTrue("Verify two dates are weekly interval",
                IntervalEnum.isOfInterval(firstDate, secondDate, IntervalEnum.WEEKLY));
    }

    @Test
    public void testIsNotOfWeeklyInterval() {
        LocalDate firstDate = LocalDate.of(2019, Month.APRIL, 1);
        LocalDate secondDate = LocalDate.of(2019, Month.APRIL, 9);
        assertFalse("Verify two dates are not weekly interval",
                IntervalEnum.isOfInterval(firstDate, secondDate, IntervalEnum.WEEKLY));
    }

    @Test
    public void testIsOfMonthlyInterval() {
        LocalDate firstDate = LocalDate.of(2019, Month.APRIL, 1);
        LocalDate secondDate = LocalDate.of(2019, Month.MAY, 1);
        assertTrue("Verify two dates are monthly interval",
                IntervalEnum.isOfInterval(firstDate, secondDate, IntervalEnum.MONTHLY));
    }

    @Test
    public void testIsNotOfMonthlyInterval() {
        LocalDate firstDate = LocalDate.of(2019, Month.APRIL, 1);
        LocalDate secondDate = LocalDate.of(2019, Month.APRIL, 30);
        assertFalse("Verify two dates are not monthly interval",
                IntervalEnum.isOfInterval(firstDate, secondDate, IntervalEnum.MONTHLY));
    }
}