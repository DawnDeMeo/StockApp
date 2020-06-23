package com.dawndemeo.stocks.factories;

import com.dawndemeo.stocks.services.WebStockService;
import com.dawndemeo.stocks.interfaces.StockService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class StockServiceFactoryTest {

    @Test
    public void getStockService() {
        StockService stockService = StockServiceFactory.getStockService();
        assertTrue("Verify WebStockService is returned",
                stockService instanceof WebStockService);
    }
}