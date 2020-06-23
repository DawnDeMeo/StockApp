package edu.dawndemeo.stocks.interfaces;

import edu.dawndemeo.stocks.datamodels.PersonDao;
import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.exceptions.UserStockServiceException;

import java.util.List;

/**
 * @author dawndemeo
 */
public interface UserStockService {

    /**
     * Get a list of all people
     *
     * @return a list of PersonDao instances
     * @throws UserStockServiceException if a service can not read or write the requested data or
     *      otherwise perform the requested operation.
     */
    List<PersonDao> getPerson() throws UserStockServiceException;

    /**
     * Add a new person or update an existing PersonDao's data
     *
     * @param person a person object to either update or create
     */
    void addOrUpdatePerson(PersonDao person);

    /**
     * Get a list of all oa person's stocks.
     *
     * @return a list of stockQuote instances
     */
    List<StockQuoteDao> getStockQuotes(PersonDao person);

    /**
     * Assign a stockQuote to a person.
     *
     * @param stockQuote the stockQuote to assign
     * @param person the person to assign the stockQuote to
     */
    void addStockQuoteToPerson(StockQuoteDao stockQuote, PersonDao person)
    ;
}
