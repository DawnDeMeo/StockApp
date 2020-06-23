package edu.dawndemeo.stocks.utilities;

import edu.dawndemeo.stocks.exceptions.ParseDateException;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class ParseDateTest {

    @Test
    public void parseDate() throws ParseDateException {
        LocalDate parsedDate = ParseDate.parseDate("1/1/2000");
        LocalDate expectedDate = LocalDate.of(2000, Month.JANUARY, 1);
        assertEquals("Verify dates match", expectedDate, parsedDate);
    }
}