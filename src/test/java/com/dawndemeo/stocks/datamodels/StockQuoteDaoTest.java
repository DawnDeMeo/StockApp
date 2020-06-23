package com.dawndemeo.stocks.datamodels;

import com.dawndemeo.stocks.utilities.ParseDate;
import com.dawndemeo.stocks.exceptions.ParseDateException;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class StockQuoteDaoTest {

    final double testValue = 12.34;
    final String testSymbol = "ABCD";

    final StockQuoteDao testQuoteNoParameters = new StockQuoteDao();
    final StockQuoteDao testQuoteWithParameters = new StockQuoteDao(testValue, testSymbol);

    @Test
    public void testGetValue() {
        assertEquals("Verify value is 0.0", 0.0, testQuoteNoParameters.getValue(), 0.001);
        assertEquals("Verify value is correct", testValue, testQuoteWithParameters.getValue(),
                0.001);
    }

    @Test
    public void testGetTickerSymbol() {
        assertEquals("Verify ticker symbol is \"Unknown Symbol\"", "Unknown Symbol",
                testQuoteNoParameters.getTickerSymbol());
        assertEquals("Verify ticker symbol is correct", testSymbol,
                testQuoteWithParameters.getTickerSymbol());
    }

    @Test
    public void testToStringAndDateToString() {
        // also tests dateToString since toString calls it
        SimpleDateFormat formatter = new SimpleDateFormat("M/d/yyy");
        assertEquals("Verify toString() is correct", "{\"StockQuoteDao\":{\"value\":\"0.0\", " +
                "\"tickerSymbol\":\"Unknown Symbol\", \"quoteDate\":\"" +
                formatter.format(Calendar.getInstance().getTime()) + "\"}}",
                testQuoteNoParameters.toString());
        assertEquals("Verify toString() is correct", "{\"StockQuoteDao\":{\"value\":\"12.34\", " +
                "\"tickerSymbol\":\"ABCD\", \"quoteDate\":\"null\"}}",
                testQuoteWithParameters.toString());
    }

    @Test
    public void testGetQuoteDate() throws ParseDateException {
        LocalDate quoteDate = ParseDate.parseDate("4/5/2019");
        StockQuoteDao testQuoteWithDate = new StockQuoteDao(testValue, testSymbol, quoteDate);
        assertEquals("Verify quote date is correct", quoteDate, testQuoteWithDate.getQuoteDate());
    }

    @Test
    public void testEqualsAndHashCode() throws ParseDateException {
        LocalDate quoteDate = ParseDate.parseDate("4/5/2019");
        StockQuoteDao quote1 = new StockQuoteDao(testValue, testSymbol, quoteDate);
        StockQuoteDao quote2 = new StockQuoteDao(testValue, testSymbol, quoteDate);
        StockQuoteDao quote3 = new StockQuoteDao(43.21, testSymbol, quoteDate);
        assertEquals("Verify StockQuotes are equal", quote1, quote2);
        assertNotEquals("Verify StockQuotes are not equal", quote1, quote3);
        assertEquals("Verify hashCodes are equal", quote1.hashCode(), quote2.hashCode());
        assertNotEquals("Verify hashCodes are not equal", quote1.hashCode(), quote3.hashCode());
    }
}