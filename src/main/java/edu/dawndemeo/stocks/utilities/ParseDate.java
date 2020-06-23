package edu.dawndemeo.stocks.utilities;

import edu.dawndemeo.stocks.exceptions.ParseDateException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class ParseDate {
    /**
     * This helper method takes date as a String with the format M/d/yyyy and parses it to a
     * LocalDate object
     *
     * @param dateString a String representation of the date in the format M/d/yyyy
     * @return date as LocalDate object
     */
    public static LocalDate parseDate(String dateString) throws ParseDateException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("M/d/yyyy");
            return LocalDate.parse(dateString ,formatter);
        } catch (DateTimeParseException e) {
            throw new ParseDateException(e.getMessage());
        }
    }
}