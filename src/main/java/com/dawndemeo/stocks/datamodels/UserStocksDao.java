package com.dawndemeo.stocks.datamodels;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author dawndemeo
 */
@Entity
@Table(name = "user_stocks", schema = "stocks")
public class UserStocksDao {
    private int id;
    private PersonDao person;
    private StockQuoteDao stockQuote;

    /**
     * Create a UserStocksDao that needs to be initialized
     */
    public UserStocksDao() {
        // this empty constructor is required by hibernate framework
    }

    /**
     * Create a valid UserStocksDao
     *
     * @param person the person to assign the stockQuote to
     * @param stockQuote the stockQuote to associate the person with
     */
    public UserStocksDao(PersonDao person, StockQuoteDao stockQuote) {
        setStockQuote(stockQuote);
        setPerson(person);
    }

    /**
     * Primary Key - Unique ID for a particular row in the user_stocks table.
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the user_stocks table.
     * This method should not be called by client code. The value is managed in internally.
     *
     * @param id a unique value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return get the PersonDao associated with this stockQuote
     */
    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "id", nullable = false)
    public PersonDao getPerson() {
        return person;
    }

    /**
     * Specify the PersonDao associated with the stockQuote
     *
     * @param person a person instance
     */
    public void setPerson(PersonDao person) {
        this.person = person;
    }

    /**
     *
     * @return get the stockQuote associated with this PersonDao
     */
    @ManyToOne
    @JoinColumn(name = "quote_id", referencedColumnName = "id", nullable = false)
    public StockQuoteDao getStockQuote() {
        return stockQuote;
    }

    /**
     * Specify the stockQuote associated with the PersonDao
     *
     * @param stockQuote a stockQuote instance
     */
    public void setStockQuote(StockQuoteDao stockQuote) {
        this.stockQuote = stockQuote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserStocksDao that = (UserStocksDao) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "{\"UserStocksDao\":{"
                + "\"id\":\"" + id + "\""
                + ", \"person\":" + person
                + ", \"stockQuote\":" + stockQuote
                + "}}";
    }
}
