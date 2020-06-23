package com.dawndemeo.stocks;

import com.dawndemeo.stocks.utilities.DatabaseUtils;
import com.dawndemeo.stocks.exceptions.DatabaseInitializationException;

/**
 * This class will initialize the database and populate the quotes table
 * with sample data.
 *
 * @author dawndemeo
 */
public class PopulateTablesWithData {

    public static void main(String[] args) throws DatabaseInitializationException {

        DatabaseUtils.initializeDatabase();

    }
}
