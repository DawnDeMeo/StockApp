package edu.dawndemeo.stocks.factories;

import edu.dawndemeo.stocks.interfaces.UserStockService;
import edu.dawndemeo.stocks.services.DatabaseUserStockService;

/**
 * A factory that returns a <CODE>UserStockService</CODE> instance.
 * @author dawndemeo
 */
public class UserStockServiceFactory {

    /**
     * Prevent instantiation
     */
    private UserStockServiceFactory() {}

    public static UserStockService getUserStockService() {
        return new DatabaseUserStockService();
    }

}
