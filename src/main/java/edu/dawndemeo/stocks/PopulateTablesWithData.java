package edu.dawndemeo.stocks;

import edu.dawndemeo.stocks.exceptions.DatabaseInitializationException;

import static edu.dawndemeo.stocks.utilities.DatabaseUtils.initializeDatabase;

/**
 * This class will initialize the database and populate the quotes table
 * with sample data.
 *
 * @author dawndemeo
 */
public class PopulateTablesWithData {

    public static void main(String[] args) throws DatabaseInitializationException {

        initializeDatabase();

    }
}
