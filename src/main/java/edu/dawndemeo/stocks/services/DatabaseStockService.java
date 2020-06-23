package edu.dawndemeo.stocks.services;

import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.exceptions.DatabaseConnectionException;
import edu.dawndemeo.stocks.exceptions.StockServiceException;
import edu.dawndemeo.stocks.interfaces.StockService;
import edu.dawndemeo.stocks.utilities.DatabaseUtils;
import edu.dawndemeo.stocks.utilities.IntervalEnum;

import javax.validation.constraints.NotNull;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is a database implementation of the StockService interface.
 *
 * @author dawndemeo
 */
public class DatabaseStockService implements StockService {

    /**
     * no-args constructor
     */
    public DatabaseStockService() {
    }

    /**
     * Return the most recent price for a share of stock for the given symbol.
     *
     * @param symbol the stock symbol of the company yuo want a quote for. e.g. APPL for APPLE
     * @return a <CODE>StockQuoteDao</CODE> instance
     * @throws StockServiceException if using the service generates an exception.
     */
    @Override
    @NotNull
    public StockQuoteDao getQuote(String symbol) throws StockServiceException {

        List<StockQuoteDao> stockQuotes;

        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            String queryString = "SELECT symbol, quoteDate, price " +
                    "FROM quotes " +
                    "WHERE symbol = '" + symbol + "' " +
                    "ORDER BY quoteDate DESC " +
                    "LIMIT 1";

            stockQuotes = fetchStockQuoteData(statement, queryString);

        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for: " + symbol);
        }
        return stockQuotes.get(0);
    }

    /**
     * Get a historical list of stock quotes for the provided symbol
     *
     * @param symbol the stock symbol to search for
     * @param startDate the date of the first stock quote
     * @param endDate the date of the last stock quote
     * @param interval the interval of the stock quotes returned - daily, weekly, or monthly
     * @return a list of StockQuoteDao instances. One for each interval in the range specified.
     * @throws StockServiceException if using the service generates an exception.
     */
    @Override
    @NotNull
    public List<StockQuoteDao> getQuote(String symbol, LocalDate startDate, LocalDate endDate,
                                        IntervalEnum interval) throws StockServiceException {

        List<StockQuoteDao> stockQuotes;
        List<StockQuoteDao> intervalStockQuotes;

        try {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            Date sqlStartDate = Date.valueOf(startDate);
            Date sqlEndDate = Date.valueOf(endDate);
            String queryString = "SELECT symbol, quoteDate, price " +
                    "FROM quotes " +
                    "WHERE symbol = '" + symbol + "' " +
                    "and quoteDate BETWEEN '" + sqlStartDate + "' and '" + sqlEndDate + "'";

            stockQuotes = fetchStockQuoteData(statement, queryString);

        } catch (DatabaseConnectionException | SQLException e) {
            throw new StockServiceException(e.getMessage(), e);
        }
        if (stockQuotes.isEmpty()) {
            throw new StockServiceException("There is no stock data for: " + symbol);
        } else if (interval == IntervalEnum.DAILY) {
            return stockQuotes;
        } else {
            intervalStockQuotes = stockQuotes.stream()
                    .filter(quote -> IntervalEnum.isOfInterval(quote.getQuoteDate(), startDate,
                            interval))
                    .collect(Collectors.toList());
        }
        return intervalStockQuotes;
    }

    /**
     * This helper method retrieves the requested data from the MySQL database
     *
     * @param statement the statement instance
     * @param queryString the database query
     * @return a list of matching StockQuotes
     * @throws SQLException if using the method generates an exception
     */
    private List<StockQuoteDao> fetchStockQuoteData(Statement statement, String queryString)
            throws SQLException {

        List<StockQuoteDao> stockQuotes;
        ResultSet resultSet = statement.executeQuery(queryString);
        stockQuotes = new ArrayList<>(resultSet.getFetchSize());
        while (resultSet.next()) {
            String symbolValue = resultSet.getString("symbol");
            LocalDate date = resultSet.getDate("quoteDate",Calendar.getInstance()).toLocalDate();
            double price = resultSet.getDouble("price");
            stockQuotes.add(new StockQuoteDao(price, symbolValue, date));
        }
        return stockQuotes;
    }
}
