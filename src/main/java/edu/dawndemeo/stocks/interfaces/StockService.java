package edu.dawndemeo.stocks.interfaces;

import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.exceptions.StockServiceException;
import edu.dawndemeo.stocks.utilities.IntervalEnum;

import java.time.LocalDate;
import java.util.List;

/**
 * This is a simple interface class with a single method getQuote
 *
 * @author dawndemeo
 */
public interface StockService {

    /**
     * Return the current price for a share of stock for the given symbol
     *
     * @param symbol the stock symbol of the company yuo want a quote for. e.g. APPL for APPLE
     * @return a <CODE>StockQuoteDao</CODE> instance
     */
    StockQuoteDao getQuote(String symbol) throws StockServiceException;

    /**
     * Get a historical list of stock quotes for the provided symbol
     *
     * @param symbol the stock symbol to search for
     * @param startDate the date of the first stock quote
     * @param endDate the date of the last stock quote
     * @param interval the interval of the stock quotes returned - daily, weekly, or monthly
     * @return a list of StockQuoteDao instances. One for each interval in range specified.
     */
    List<StockQuoteDao> getQuote(String symbol, LocalDate startDate, LocalDate endDate,
                                 IntervalEnum interval) throws StockServiceException;
}
