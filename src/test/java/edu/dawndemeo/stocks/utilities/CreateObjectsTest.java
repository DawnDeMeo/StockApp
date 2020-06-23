package edu.dawndemeo.stocks.utilities;

import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class CreateObjectsTest {

    @Test
    public void testCreateObjects() {
        List<StockQuoteDao> testList = CreateObjects.createObjects();
        assertNotNull("Verify list of StockQuoteDao objects is created", testList.get(0));
    }
}