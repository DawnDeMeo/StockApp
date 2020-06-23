package edu.dawndemeo.stocks.datamodels;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Type;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * This class creates a StockQuoteDao object containing a double value and String tickerSymbol
 *
 * @author dawndemeo
 */

@Entity
@Table(name = "quotes", schema = "stocks")
public class StockQuoteDao {

    private int id;
    private double value;
    private String tickerSymbol;
    private LocalDate quoteDate;

    /**
     * No-arg constructor with default values.
     */
    public StockQuoteDao() {
        this.value = 0.0;
        this.tickerSymbol = "Unknown Symbol";
        this.quoteDate = LocalDate.now();
    }

    /**
     * This constructor takes the value and tickerSymbol
     *
     * @param value The monetary value of the StockQuoteDao object
     * @param tickerSymbol The stock ticker symbol of the StockQuoteDao object
     */
    public StockQuoteDao(double value, String tickerSymbol) {
        this.value = value;
        this.tickerSymbol = tickerSymbol;
    }

    /**
     * This constructor takes the value, tickerSymbol and quoteDate
     *
     * @param value The monetary value of the StockQuoteDao object
     * @param tickerSymbol The stock ticker symbol of the StockQuoteDao object
     * @param quoteDate The date of the StockQuoteDao object
     */
    public StockQuoteDao(@NotNull double value, @NotNull String tickerSymbol,
                         @NotNull LocalDate quoteDate){
        this.value = value;
        this.tickerSymbol = tickerSymbol;
        this.quoteDate = quoteDate;
    }

    /**
     * This constructor takes a StockQuoteDao object and creates a copy
     *
     * @param stockQuote The StockQuoteDao to be copied
     */
    public StockQuoteDao(StockQuoteDao stockQuote) {
        this.id = stockQuote.getId();
        this.value = stockQuote.getValue();
        this.tickerSymbol = stockQuote.getTickerSymbol();
        this.quoteDate = stockQuote.getQuoteDate();
    }

    /**
     * Primary Key - Unique ID for a particular row in the quote table
     *
     * @return an integer value
     */
    @Id
    @Column(name = "id", nullable = false)
    public int getId() {
        return id;
    }

    /**
     * Set the unique ID for a particular row in the quote table.
     * This method should not be called by client code. The value is managed internally.
     *
     * @param id a unique value
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Returns the value of the StockQuoteDao object
     *
     * @return double value
     */
    @Basic
    @Column(name = "price", nullable = false)
    public double getValue() {
        return value;
    }

    /**
     * Specify the stock's value
     * @param value a double value
     */
    public void setValue(double value) {
        this.value = value;
    }

    /**
     * Returns the tickerSymbol of the StockQuoteDao object
     *
     * @return String tickerSymbol
     */
    @Basic
    @Column(name = "symbol", nullable = false, length = 4)
    public String getTickerSymbol() {
        return tickerSymbol;
    }

    /**
     * Specify the stock's ticker symbol
     * @param tickerSymbol a String value
     */
    public void setTickerSymbol(String tickerSymbol) {
        this.tickerSymbol = tickerSymbol;
    }

    /**
     * Returns the quoteDate of the StockQuoteDao object
     *
     * @return Calendar quoteDate
     */
    @Column(name = "quoteDate", nullable = false)
    @Type(type = "edu.dawndemeo.stocks.datamodels.LocalDateHibernateUserType")
    public LocalDate getQuoteDate() {
        return quoteDate;
    }

    /**
     * Specify the stock's quote date
     * @param quoteDate a LocalDate value
     */
    public void setQuoteDate(LocalDate quoteDate) {
        this.quoteDate = quoteDate;
    }

    /**
     * This method compares the members of two StockQuoteDao objects to determine equality.
     *
     * @param obj the object being compared
     * @return true if members are equal
     */
    @Override
    public boolean equals(Object obj) {

        // If the object is compared with itself then return true
        if (obj == this) {
            return true;
        }

        // Check if obj is an instance of StockQuoteDao
        if (!(obj instanceof StockQuoteDao)) {
            return false;
        }

        // Typecast obj to StockQuoteDao to compare members
        StockQuoteDao sq = (StockQuoteDao) obj;

        // Compare the data members and return accordingly
        return (Double.compare(value, sq.value) == 0 &&
                tickerSymbol.equals(sq.tickerSymbol) &&
                quoteDate.equals(sq.quoteDate));
    }

    /**
     * This method returns the hashCode for a StockQuoteDao object.
     *
     * @return the hashCode
     */
    @Override
    public int hashCode() {
        int result = Short.hashCode((short)value);
        result = 31 * result + tickerSymbol.hashCode();
        result = 31 * result + quoteDate.hashCode();
        return result;
    }

    /**
     * This method takes a LocalDate and returns it as a String in the format M/d/yyyy.
     *
     * @param date the date to convert
     * @return the String representation of the date
     */
    public String dateToString(LocalDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
        String dateString = null;
        if (date != null) {
            dateString = date.format(formatter);
        }
        return dateString;
    }

    /**
     * Returns a String representation of the StockQuoteDao object in JSON format
     *
     * @return String
     */
    @Override
    public String toString() {
        return "{\"StockQuoteDao\":{"
                + "\"value\":\"" + value + "\""
                + ", \"tickerSymbol\":\"" + tickerSymbol + "\""
                + ", \"quoteDate\":\"" + dateToString(quoteDate) + "\""
                + "}}";
    }
}
