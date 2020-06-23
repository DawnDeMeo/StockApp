package edu.dawndemeo.stocks.services;

import com.google.gson.*;
import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.interfaces.StockService;
import edu.dawndemeo.stocks.utilities.IntervalEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.google.gson.JsonParser.parseString;

/**
 * This is a web service implementation of the StockService interface
 *
 * @author dawndemeo
 */
public class WebStockService implements StockService {

    // UniBit
    final String UB_KEY = "RAM95l26_6Rv5iNFKtO_TVWNBKf_nMSA";
    final String UB_BASE_URL_LATEST = "https://api.unibit.ai/api/realtimestock/";
    final String UB_BASE_URL_INTERVAL = "https://api.unibit.ai/v2/stock/historical/";

    /**
     * Return the most recent price for a share of stock for the given symbol.
     *
     * @param symbol the stock symbol of the company yuo want a quote for. e.g. AAPL for APPLE
     * @return a <CODE>StockQuoteDao</CODE> instance
     */
    @Override
    public StockQuoteDao getQuote(String symbol) {

        StockQuoteDao stockQuote = new StockQuoteDao();

        // Build the query string
        String queryString = UB_BASE_URL_LATEST + symbol +
                "?size=1&AccessKey=" + UB_KEY;

        // Set up the connection to the online service
        try {
            JsonArray resultData = getWebQueryResultsAsJsonObject(symbol, queryString)
                    .getAsJsonArray("Realtime Stock price");

            if (resultData.size() > 0) {
                JsonElement jsonElement = resultData.get(0);
                JsonElement jsonPrice = jsonElement.getAsJsonObject().get("price");
                JsonElement jsonDate = jsonElement.getAsJsonObject().get("date");

                // Use those parsed fields to set the stockQuote parameters
                stockQuote.setTickerSymbol(symbol.toUpperCase());
                stockQuote.setValue(jsonPrice.getAsDouble());

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                stockQuote.setQuoteDate(LocalDate.parse(jsonDate.getAsString(), formatter));
            } // else default stockQuote data is returned

        } catch (IOException e) {
            return new StockQuoteDao();
        }
        return stockQuote;
    }


    /**
     * Get a historical list of stock quotes for the provided symbol
     *
     * @param symbol the stock symbol to search for
     * @param startDate the date of the first stock quote
     * @param endDate the date of the last stock quote
     * @param interval the interval of the stock quotes returned - daily, weekly, or monthly
     * @return a list of StockQuoteDao instances. One for each interval in the range specified.
     */
    @Override
    public List<StockQuoteDao> getQuote(String symbol, LocalDate startDate, LocalDate endDate, IntervalEnum interval) {

        // Convert interval to number as String
        String quoteInterval;
        switch (interval) {
            case WEEKLY:
                quoteInterval = "7";
                break;
            case MONTHLY:
                quoteInterval = "30";
                break;
            case DAILY:
            default: // defaults to daily if not specified
                quoteInterval = "1";
        }

        // Build the query string
        String queryString = UB_BASE_URL_INTERVAL + "?tickers=" + symbol +
                "&interval=" + quoteInterval +
                "&startDate=" + startDate.toString() +
                "&endDate=" + endDate.toString() +
                "&selectedFields=date,close&dataType=json" +
                "&accessKey=" + UB_KEY;

        List<StockQuoteDao> stockQuotesList = new ArrayList<>();

        try {
            JsonArray resultData = getWebQueryResultsAsJsonObject(symbol, queryString)
                    .getAsJsonObject("result_data").getAsJsonArray(symbol.toUpperCase());

            if (!(resultData == null)) {

                JsonElement jsonPrice;
                JsonElement jsonDate;

                for (JsonElement jsonElement : resultData) {

                    jsonPrice = jsonElement.getAsJsonObject().get("close");
                    jsonDate = jsonElement.getAsJsonObject().get("date");

                    // Use those parsed fields to set the stockQuote parameters
                    StockQuoteDao stockQuote = new StockQuoteDao();
                    stockQuote.setTickerSymbol(symbol.toUpperCase());
                    stockQuote.setValue(jsonPrice.getAsDouble());
                    stockQuote.setQuoteDate(LocalDate.parse(jsonDate.getAsString()));
                    stockQuotesList.add(stockQuote);
                }
            } else {
                // Populate list with single instance of default stockQuote
                stockQuotesList.add(new StockQuoteDao());
            }

        } catch (IOException e) {
            return stockQuotesList;
        }

        return stockQuotesList;
    }


    /**
     * This helper method fetches the data from the URL query of the most recent data and returns
     * the results as a JsonArray
     *
     * @param symbol the ticker symbol
     * @param queryString the URL being queried
     * @return a JsonArray with query results
     * @throws IOException if using method causes error
     */
    private JsonObject getWebQueryResultsAsJsonObject(String symbol, String queryString) throws IOException {
        java.net.URL connectionUrl = new java.net.URL(queryString);
        InputStream stream = connectionUrl.openStream();

        // Stream the results
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        // Store results in a string
        StringBuffer resultStringBuffer = new StringBuffer();
        String line = reader.readLine();
        while (line != null) {
            resultStringBuffer.append(line);
            line = reader.readLine();
        }

        String resultString = resultStringBuffer.toString();


        // Parse relevant fields of JSON object
        JsonObject jsonObject = parseString(resultString).getAsJsonObject();
        return jsonObject;
//        return jsonObject.getAsJsonArray("Realtime Stock price");
    }
}
