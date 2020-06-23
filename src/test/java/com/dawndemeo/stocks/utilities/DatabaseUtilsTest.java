package com.dawndemeo.stocks.utilities;

import com.dawndemeo.stocks.exceptions.DatabaseConnectionException;
import com.dawndemeo.stocks.exceptions.DatabaseInitializationException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Statement;

import static org.junit.Assert.*;

/**
 * @author dawndemeo
 */
public class DatabaseUtilsTest {

    Connection testConnection;

    @Before
    public void setUp() throws DatabaseConnectionException, DatabaseInitializationException {
        testConnection = DatabaseUtils.getConnection();
        DatabaseUtils.initializeDatabase();
    }

    @After
    public void tearDown() throws Exception {
        DatabaseUtils.clearDataFromDatabase();
    }

    @Test
    public void testGetConnection() {
        assertNotNull("Verify connection created successfully", testConnection);
    }

    @Test
    public void testGetConnectionWorks() throws Exception {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        boolean execute = statement.execute("select * from person");
        assertTrue("Verify that we can execute a statement", execute);
    }
}