package edu.dawndemeo.stocks.services;

import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.exceptions.StockServiceException;
import edu.dawndemeo.stocks.factories.StockServiceFactory;
import edu.dawndemeo.stocks.interfaces.StockService;
import edu.dawndemeo.stocks.utilities.IntervalEnum;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class WebStockServiceTest {

    StockService stockService;
    String testInvalidSymbol = "XXXX";
    String testValidSymbol = "AAPL";

    @Before
    public void setUp() {
        stockService = StockServiceFactory.getStockService();
    }

    @Test
    public void testInvalidSymbolReturnsDefaultStockQuoteDao() throws StockServiceException {
        StockQuoteDao testQuote = stockService.getQuote(testInvalidSymbol);
        assertEquals("Verify unknown symbol returns default quote", "Unknown Symbol", testQuote.getTickerSymbol());
    }

    @Test
    public void testValidSymbolDoesNotReturnDefaultStockQuoteDao() throws StockServiceException {
        StockQuoteDao testQuote = stockService.getQuote(testValidSymbol);
        assertEquals("Verify known symbol doesn't return default quote", testValidSymbol, testQuote.getTickerSymbol());
    }

    @Test
    public void testGetQuoteListDailyPositive() throws StockServiceException {
        List<StockQuoteDao> dailyQuotes = stockService.getQuote(testValidSymbol, LocalDate.of(2020, 03, 02), LocalDate.of(2020, 03, 06),
                IntervalEnum.DAILY);
        assertNotNull("Verify list contains data", dailyQuotes);
        assertEquals("Verify data is for requested stock", testValidSymbol, dailyQuotes.get(0).getTickerSymbol());
    }

    @Test
    public void testGetQuoteListDailyNegative() throws StockServiceException {
        List<StockQuoteDao> dailyQuotes = stockService.getQuote(testInvalidSymbol, LocalDate.of(2020, 03, 02), LocalDate.of(2020, 03, 06),
                IntervalEnum.DAILY);
        assertNotNull(dailyQuotes);
    }

    @Test
    public void testGetQuoteListWeeklyPositive() throws StockServiceException {
        List<StockQuoteDao> weeklyQuotes = stockService.getQuote(testValidSymbol, LocalDate.of(2020, 02, 02), LocalDate.of(2020, 03, 06),
                IntervalEnum.WEEKLY);
        assertNotNull("Verify list contains data", weeklyQuotes);
        assertEquals("Verify data is for requested stock", testValidSymbol, weeklyQuotes.get(0).getTickerSymbol());
    }

    @Test
    public void testGetQuoteListWeeklyNegative() throws StockServiceException {
        List<StockQuoteDao> weeklyQuotes = stockService.getQuote(testInvalidSymbol, LocalDate.of(2020, 02, 02), LocalDate.of(2020, 03, 06),
                IntervalEnum.WEEKLY);
        assertNotNull(weeklyQuotes);
    }

    @Test
    public void testGetQuoteListMonthlyPositive() throws StockServiceException {
        List<StockQuoteDao> monthlyQuotes = stockService.getQuote(testValidSymbol, LocalDate.of(2020, 01, 02), LocalDate.of(2020, 03, 06),
                IntervalEnum.MONTHLY);
        assertNotNull("Verify list contains data", monthlyQuotes);
        assertEquals("Verify data is for requested stock", testValidSymbol, monthlyQuotes.get(0).getTickerSymbol());
    }

    @Test
    public void testGetQuoteListMonthlyNegative() throws StockServiceException {
        List<StockQuoteDao> monthlyQuotes = stockService.getQuote(testInvalidSymbol, LocalDate.of(2020, 01, 02), LocalDate.of(2020, 03, 06),
                IntervalEnum.MONTHLY);
        assertNotNull(monthlyQuotes);
    }

}