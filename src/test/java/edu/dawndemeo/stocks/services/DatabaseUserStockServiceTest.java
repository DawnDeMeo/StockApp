package edu.dawndemeo.stocks.services;

import edu.dawndemeo.stocks.datamodels.PersonDao;
import edu.dawndemeo.stocks.datamodels.PersonDaoTest;
import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.exceptions.UserStockServiceException;
import edu.dawndemeo.stocks.factories.UserStockServiceFactory;
import edu.dawndemeo.stocks.interfaces.UserStockService;
import edu.dawndemeo.stocks.utilities.DatabaseUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class DatabaseUserStockServiceTest {

    private UserStockService userStockService;

    Connection connection;

    @Before
    public void setUp() throws Exception {
        connection = DatabaseUtils.getConnection();
        DatabaseUtils.initializeDatabase();
        userStockService = UserStockServiceFactory.getUserStockService();
    }

    @After
    public void tearDown() throws Exception {
        DatabaseUtils.clearDataFromDatabase();
    }

    @Test
    public void testGetInstance() {
        assertNotNull("Make sure activitiesService is available", userStockService);
    }

    @Test
    public void testGetPerson() throws UserStockServiceException {
        List<PersonDao> personList = userStockService.getPerson();
        assertFalse("Make sure we get some PersonDao objects from service", personList.isEmpty());
    }

    @Test
    public void testAddOrUpdatePerson() throws UserStockServiceException {
        PersonDao newPerson = PersonDaoTest.createPerson();
        userStockService.addOrUpdatePerson(newPerson);
        List<PersonDao> personList = userStockService.getPerson();
        boolean found = false;
        for (PersonDao person : personList) {
            if (person.getFirstName().equals(PersonDaoTest.firstName)
                && person.getLastName().equals(PersonDaoTest.lastName)) {

                found = true;
                break;
            }
        }
        assertTrue("Found the person we added", found);
    }

    @Test
    public void testGetUserStocksByPerson() throws UserStockServiceException {
        PersonDao person = PersonDaoTest.createPerson();
        List<StockQuoteDao> stockQuotes = userStockService.getStockQuotes(person);

        // make the person have all the stockQuotes
        for (StockQuoteDao stockQuote : stockQuotes) {
            userStockService.addStockQuoteToPerson(stockQuote, person);
        }

        List<StockQuoteDao> stockQuoteList = userStockService.getStockQuotes(person);

        for (StockQuoteDao stockQuote : stockQuotes) {
            boolean removed = stockQuoteList.remove(stockQuote);
            assertTrue("Verify that the stockQuote was found in the list", removed);
        }
        // if stockQuoteList is empty, then we know the lists matched
        assertTrue("Verify the list of returned hobbies matches what was expected", stockQuoteList.isEmpty());
    }
}