package edu.dawndemeo.stocks.factories;

import edu.dawndemeo.stocks.interfaces.StockService;
import edu.dawndemeo.stocks.services.WebStockService;
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