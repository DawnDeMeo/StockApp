package com.dawndemeo.stocks.xml;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class StocksTest {

    Stocks.StockQuote testStock = new Stocks.StockQuote();

    @Test
    public void testStocksReturnsCorrectData() {
        testStock.setPrice("1.00");
        testStock.setQuoteDate("01-01-1999 00:00:00");
        testStock.setSymbol("TEST");
        assertEquals("Verify correct data is returned", "StockQuote{symbol=TEST, price=1.00, quoteDate=01-01-1999 00:00:00}", testStock.toString());
    }
}