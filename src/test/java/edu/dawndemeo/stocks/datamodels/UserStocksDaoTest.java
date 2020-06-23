package edu.dawndemeo.stocks.datamodels;

import edu.dawndemeo.stocks.exceptions.ParseDateException;
import edu.dawndemeo.stocks.utilities.ParseDate;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class UserStocksDaoTest {

    static StockQuoteDao stockQuote;

    static {
        try {
            stockQuote = new StockQuoteDao(3.14, "STOK", ParseDate.parseDate("3/14/1593"));
        } catch (ParseDateException e) {
            e.printStackTrace();
        }
    }

    /**
     * Testing helper method for generating UserStocksDao test data
     *
     * @return a UserStocksDao object that uses PersonDao and StockQuoteDao
     */
    public static UserStocksDao createUserStocks() {
        PersonDao person = PersonDaoTest.createPerson();
        return new UserStocksDao(person,stockQuote);
    }

    @Test
    public void testUserStocksGetterAndSetters() {
        PersonDao person = PersonDaoTest.createPerson();
        UserStocksDao userStocks = new UserStocksDao();
        int id = 10;
        userStocks.setId(id);
        userStocks.setPerson(person);
        userStocks.setStockQuote(stockQuote);
        assertEquals("person matches", person, userStocks.getPerson());
        assertEquals("stockQuote matches", stockQuote, userStocks.getStockQuote());
        assertEquals("id matches", id, userStocks.getId());
    }

    @Test
    public void testEqualsNegativeDifferentPerson() throws ParseDateException {
        UserStocksDao userStocks = createUserStocks();
        userStocks.setId(10);
        PersonDao person = new PersonDao();
        person.setFirstName(PersonDaoTest.firstName);
        person.setLastName(PersonDaoTest.lastName);
        UserStocksDao userStocks2 = new UserStocksDao(person, stockQuote);
        assertNotEquals("Different person", userStocks, userStocks2);
    }


}