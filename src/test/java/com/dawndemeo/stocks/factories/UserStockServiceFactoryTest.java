package com.dawndemeo.stocks.factories;

import com.dawndemeo.stocks.interfaces.UserStockService;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class UserStockServiceFactoryTest {

    @Test
    public void testFactory() {
        UserStockService instance = UserStockServiceFactory.getUserStockService();
        assertNotNull("Make sure factory works", instance);
    }

}