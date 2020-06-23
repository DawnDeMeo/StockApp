package com.dawndemeo.stocks.factories;

import com.dawndemeo.stocks.interfaces.UserStockService;
import com.dawndemeo.stocks.services.DatabaseUserStockService;

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
