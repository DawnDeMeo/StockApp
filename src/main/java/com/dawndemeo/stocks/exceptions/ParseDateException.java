package com.dawndemeo.stocks.exceptions;

/**
 * This class is used to signal a problem parsing a String for the date.
 * Generally caused by incorrect date format.
 *
 * @author dawndemeo
 */
public class ParseDateException extends Exception {
    public ParseDateException(String message) {
        super(message);
    }
}
