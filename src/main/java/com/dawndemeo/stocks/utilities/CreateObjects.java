package com.dawndemeo.stocks.utilities;

import com.dawndemeo.stocks.datamodels.StockQuoteDao;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dawndemeo
 */
public class CreateObjects {
    /**
     * This helper method creates a set fake historical data for demonstrating the getQuote method.
     * It creates a stock quote for each day by incrementing the day value and the price value.
     *
     * Valid dates: 04/01/2019 through 06/30/2019
     * Valid ticker symbols: APPL, GOOG, AMZN
     *
     * @return stockHistory containing fake data for APPL
     */
    public static List<StockQuoteDao> createObjects() {
        List<StockQuoteDao> stockHistory = new ArrayList<>();

        LocalDate startDate = LocalDate.of(2019, Month.APRIL, 1);
        LocalDate endDate = LocalDate.of(2019, Month.JULY, 1);
        double applePrice = 100.34;
        double googlePrice = 80.79;
        double amazonPrice = 212.13;

        for (LocalDate date = startDate; date.isBefore(endDate); date = date.plusDays(1)) {
            stockHistory.add(new StockQuoteDao(applePrice, "APPL", date));
            stockHistory.add(new StockQuoteDao(googlePrice, "GOOG", date));
            stockHistory.add(new StockQuoteDao(amazonPrice, "AMZN", date));
            applePrice += 1.25;
            googlePrice += 0.83;
            amazonPrice += 1.02;
        }

        return stockHistory;
    }
}
