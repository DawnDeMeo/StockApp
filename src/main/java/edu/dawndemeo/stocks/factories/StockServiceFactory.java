package edu.dawndemeo.stocks.factories;

import edu.dawndemeo.stocks.interfaces.StockService;
import edu.dawndemeo.stocks.services.WebStockService;

/**
 * This class implements the Factory Method design pattern. It contains the logic to decide which
 * implementation of StockService to use. Currently it is set to return a WebStockService.
 *
 * @author dawndemeo
 */
public class StockServiceFactory {

    public static StockService getStockService() {
        return new WebStockService();
    }
}
