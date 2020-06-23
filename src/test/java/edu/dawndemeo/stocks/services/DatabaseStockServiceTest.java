package edu.dawndemeo.stocks.services;

import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.exceptions.DatabaseConnectionException;
import edu.dawndemeo.stocks.exceptions.DatabaseInitializationException;
import edu.dawndemeo.stocks.exceptions.ParseDateException;
import edu.dawndemeo.stocks.exceptions.StockServiceException;
import edu.dawndemeo.stocks.interfaces.StockService;
import edu.dawndemeo.stocks.utilities.DatabaseUtils;
import edu.dawndemeo.stocks.utilities.IntervalEnum;
import edu.dawndemeo.stocks.utilities.ParseDate;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class DatabaseStockServiceTest {

    public static final String SYMBOL = "APPL";
    final StockService testStockService = new DatabaseStockService();
    Connection testConnection;

    @Before
    public void setUp() throws DatabaseConnectionException, DatabaseInitializationException {
        testConnection = DatabaseUtils.getConnection();
        DatabaseUtils.initializeDatabase();
    }

    @After
    public void tearDown() throws Exception {
        DatabaseUtils.clearDataFromDatabase();
    }

    @Test
    public void testGetQuoteValidArg() throws StockServiceException, ParseDateException {
        StockQuoteDao stockQuoteValidArg = testStockService.getQuote(SYMBOL);
        LocalDate expectedDate = ParseDate.parseDate("6/30/2019");
        LocalDate actualDate = stockQuoteValidArg.getQuoteDate();

        assertEquals("Verify quote date is correct", expectedDate, actualDate);
        assertEquals("Verify ticker symbol is correct", SYMBOL,
                stockQuoteValidArg.getTickerSymbol());
        assertEquals("Verify price is correct", 212.84, stockQuoteValidArg.getValue(), 0.001);
    }

    @Test (expected = StockServiceException.class)
    public void testGetQuoteInvalidArg() throws StockServiceException {
        StockQuoteDao stockQuoteInvalidArg = testStockService.getQuote("XXXX");
    }

    @Test
    public void testGetQuoteListValidArgs() throws StockServiceException, ParseDateException {
        LocalDate startDate = ParseDate.parseDate("4/3/2019");
        LocalDate endDate = ParseDate.parseDate("4/4/2019");

        List<StockQuoteDao> dailyQuotes = testStockService.getQuote(SYMBOL, startDate, endDate,
                IntervalEnum.DAILY);
        assertEquals("Verify correct data is added to List",
                "[{\"StockQuoteDao\":{\"value\":\"102.84\", \"tickerSymbol\":\"APPL\"," +
                        " \"quoteDate\":\"4/3/2019\"}}, {\"StockQuoteDao\":{\"value\":\"104.09\"," +
                        " \"tickerSymbol\":\"APPL\", \"quoteDate\":\"4/4/2019\"}}]",
                dailyQuotes.toString());

        endDate = ParseDate.parseDate("4/16/2019");
        List<StockQuoteDao> weeklyQuotes = testStockService.getQuote(SYMBOL, startDate, endDate,
                IntervalEnum.WEEKLY);
        assertEquals("Verify correct data is added to List",
                "[{\"StockQuoteDao\":{\"value\":\"102.84\", \"tickerSymbol\":\"APPL\"," +
                        " \"quoteDate\":\"4/3/2019\"}}, {\"StockQuoteDao\":{\"value\":\"111.59\"," +
                        " \"tickerSymbol\":\"APPL\", \"quoteDate\":\"4/10/2019\"}}]",
                weeklyQuotes.toString());

        endDate = ParseDate.parseDate("5/16/2019");
        List<StockQuoteDao> monthlyQuotes = testStockService.getQuote(SYMBOL, startDate, endDate,
                IntervalEnum.MONTHLY);
        assertEquals("Verify correct data is added to List",
                "[{\"StockQuoteDao\":{\"value\":\"102.84\", \"tickerSymbol\":\"APPL\"," +
                        " \"quoteDate\":\"4/3/2019\"}}, {\"StockQuoteDao\":{\"value\":\"140.34\"," +
                        " \"tickerSymbol\":\"APPL\", \"quoteDate\":\"5/3/2019\"}}]",
                monthlyQuotes.toString());
    }

    @Test (expected = StockServiceException.class)
    public void testGetQuoteListInvalidArgs() throws StockServiceException, ParseDateException {
        LocalDate startDate = ParseDate.parseDate("4/3/2019");
        LocalDate endDate = ParseDate.parseDate("4/4/2019");
        List<StockQuoteDao> invalidSymbol = testStockService.getQuote("XXXX", startDate, endDate,
                IntervalEnum.DAILY);
    }

    @Test (expected = StockServiceException.class)
    public void testGetQuoteListInvalidDateRange()
            throws StockServiceException, ParseDateException {
        LocalDate startDate = ParseDate.parseDate("1/1/1999");
        LocalDate endDate = ParseDate.parseDate("1/4/1999");
        List<StockQuoteDao> invalidDateRange = testStockService.getQuote(SYMBOL, startDate, endDate,
                IntervalEnum.DAILY);
    }

    @Test
    public void testGetQuoteExtremes() throws StockServiceException, ParseDateException {
        LocalDate lowerLimitPositive = ParseDate.parseDate("4/1/2019");
        LocalDate upperLimitPositive = ParseDate.parseDate("6/30/2019");

        List<StockQuoteDao> atLowerLimit = testStockService.getQuote(SYMBOL, lowerLimitPositive,
                lowerLimitPositive, IntervalEnum.DAILY);
        assertEquals("Verify correct data is added to List",
                "[{\"StockQuoteDao\":{\"value\":\"100.34\", \"tickerSymbol\":\"APPL\"," +
                        " \"quoteDate\":\"4/1/2019\"}}]", atLowerLimit.toString());

        List<StockQuoteDao> atUpperLimit = testStockService.getQuote(SYMBOL, upperLimitPositive,
                upperLimitPositive, IntervalEnum.DAILY);
        assertEquals("Verify correct data is added to List",
                "[{\"StockQuoteDao\":{\"value\":\"212.84\", \"tickerSymbol\":\"APPL\"," +
                        " \"quoteDate\":\"6/30/2019\"}}]", atUpperLimit.toString());
    }

    @Test (expected = StockServiceException.class)
    public void testGetQuoteLowerExtremeNegative()
            throws StockServiceException, ParseDateException {
        LocalDate lowerLimitNegative = ParseDate.parseDate("3/30/2019");
        List<StockQuoteDao> beforeLowerLimit = testStockService.getQuote(SYMBOL, lowerLimitNegative,
                lowerLimitNegative, IntervalEnum.DAILY);
    }

    @Test (expected = StockServiceException.class)
    public void testGetQuoteUpperExtremeNegative()
            throws StockServiceException, ParseDateException {
        LocalDate upperLimitNegative = ParseDate.parseDate("7/1/2019");
        List<StockQuoteDao> afterUpperLimit = testStockService.getQuote(SYMBOL, upperLimitNegative,
                upperLimitNegative, IntervalEnum.DAILY);    }
}