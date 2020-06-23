package edu.dawndemeo.stocks.utilities;

import com.ibatis.common.jdbc.ScriptRunner;
import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.exceptions.DatabaseConnectionException;
import edu.dawndemeo.stocks.exceptions.DatabaseInitializationException;
import edu.dawndemeo.stocks.interfaces.UserStockService;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.Calendar;
import java.util.List;

/**
 * A class that contains database related utility methods.
 */
public class DatabaseUtils {

    private static SessionFactory sessionFactory;
    private static Configuration configuration;

    /**
     *
     * @return SessionFactory for use with database transactions
     */
    public static SessionFactory getSessionFactory() {

        // singleton pattern
        synchronized (UserStockService.class) {
            if (sessionFactory == null) {
                Configuration configuration = getConfiguration();

                ServiceRegistry serviceRegistry = new ServiceRegistryBuilder()
                        .applySettings(configuration.getProperties())
                        .buildServiceRegistry();

                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            }
        }
        return sessionFactory;
    }

    /**
     * Create a new or return an existing database configuration object.
     *
     * @return a Hibernate Configuration instance.
     */
    private static Configuration getConfiguration() {
        synchronized (DatabaseUtils.class) {
            if (configuration == null) {
                configuration = new Configuration();
                configuration.configure("hibernate.cfg.xml");
            }
        }
        return configuration;
    }

    public static Connection getConnection() throws DatabaseConnectionException {
        Connection connection;
        Configuration configuration = getConfiguration();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String databaseUrl = configuration.getProperty("connection.url");
            String username = configuration.getProperty("hibernate.connection.username");
            String password = configuration.getProperty("hibernate.connection.password");
            connection =   DriverManager.getConnection(databaseUrl, username, password);

            // an example of throwing an exception appropriate to the abstraction.
        } catch (ClassNotFoundException  | SQLException e)  {
           throw new  DatabaseConnectionException("Could not connection to database."
                   + e.getMessage(), e);
        }
        return connection;
    }

    /**
     * A utility method that runs a db initialize script, generates sample data for the quotes
     * table, and runs another script to populate the other two tables.
     * @throws DatabaseInitializationException if unable to run
     */
    public static void initializeDatabase() throws DatabaseInitializationException {

        createEmptyTables();

        // Create prepared statement and run loop to fill with generated stock data
        Connection connection;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            // Create prepared statement and run loop to fill with generated stock data
            PreparedStatement loadDataStatement = connection.prepareStatement("INSERT INTO " +
                    "quotes (symbol,quoteDate,price) VALUES (?, ?, ?)");

            List<StockQuoteDao> fakeStockHistory = CreateObjects.createObjects();
            for (StockQuoteDao quote : fakeStockHistory) {
                loadDataStatement.setString(1, quote.getTickerSymbol());
                loadDataStatement.setDate(2, Date.valueOf(quote.getQuoteDate()),
                        Calendar.getInstance());
                loadDataStatement.setDouble(3, quote.getValue());
                loadDataStatement.executeUpdate();
            }

            // Runs script to populate user_stocks and person tables
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new FileInputStream("src/main/resources/sql/db_populate_tables.sql");
            InputStreamReader reader = new InputStreamReader(inputStream);
            runner.runScript(reader);
            reader.close();

            connection.commit();
            connection.close();

        } catch (DatabaseConnectionException | SQLException | IOException e) {
            throw new DatabaseInitializationException("Could not initialize db because of:"
                    + e.getMessage(),e);
        }

    }

    /**
     * This method drops the stocks table from the database
     *
     * @throws DatabaseInitializationException if unable to run
     */
    public static void clearDataFromDatabase() throws DatabaseInitializationException {

        Connection connection;
        try {
            connection = getConnection();

            // Drop table if it exists
            Statement statement = connection.createStatement();
            String dropUserStocksTable = "DROP TABLE IF EXISTS user_stocks CASCADE";
            statement.executeUpdate(dropUserStocksTable);
            String dropPersonTable = "DROP TABLE IF EXISTS person CASCADE";
            statement.executeUpdate(dropPersonTable);
            String dropQuotesTable = "DROP TABLE IF EXISTS quotes CASCADE";
            statement.executeUpdate(dropQuotesTable);

            connection.close();

        } catch (DatabaseConnectionException | SQLException e) {
            throw new DatabaseInitializationException("Could not initialize db because of:"
                    + e.getMessage(),e);
        }
    }

    /**
     * This method initializes the database with empty tables.
     *
     * @throws DatabaseInitializationException if unable to initialize database
     */
    public static void createEmptyTables() throws DatabaseInitializationException {

        Connection connection;
        try {
            connection = getConnection();
            connection.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(connection, false, false);
            InputStream inputStream = new FileInputStream("src/main/resources/sql/db_create_tables.sql");
            InputStreamReader reader = new InputStreamReader(inputStream);
            runner.runScript(reader);
            reader.close();
            connection.commit();
            connection.close();

        } catch (DatabaseConnectionException | SQLException | IOException e) {
            throw new DatabaseInitializationException("Could not initialize db because of:"
                    + e.getMessage(),e);
        }
    }

}
