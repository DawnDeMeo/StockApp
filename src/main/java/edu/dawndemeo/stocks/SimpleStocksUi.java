package edu.dawndemeo.stocks;

import edu.dawndemeo.stocks.datamodels.PersonDao;
import edu.dawndemeo.stocks.datamodels.StockQuoteDao;
import edu.dawndemeo.stocks.exceptions.StockServiceException;
import edu.dawndemeo.stocks.interfaces.StockService;
import edu.dawndemeo.stocks.interfaces.UserStockService;
import edu.dawndemeo.stocks.services.DatabaseStockService;
import edu.dawndemeo.stocks.services.DatabaseUserStockService;
import edu.dawndemeo.stocks.services.WebStockService;
import edu.dawndemeo.stocks.utilities.DatabaseUtils;
import edu.dawndemeo.stocks.xml.Stocks;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.Scanner;

import static edu.dawndemeo.stocks.utilities.DatabaseUtils.getSessionFactory;

/**
 * This program presents the user with a menu of options to perform with stock data. They may
 * search for a stock quote from either the local database or an online stock service. They may
 * add a new person to the Person table of the local database. They may upload an XML file
 * containing stock quotes to the local database. Finally, they may query the online stock service
 * and have the results of the REST call saved to the local database.
 *
 * @author dawndemeo
 */
public class SimpleStocksUi {

    static final StockService dbStockService = new DatabaseStockService();  // local
    static final StockService webStockService = new WebStockService();      // online

    public static void main(String[] args) {

        // Prompt with menu. After completing operation it will prompt again until the user decides
        // to exit the program.
        boolean notDoneYet = Boolean.TRUE;

        while (notDoneYet) {
            int choice = displayMenu();
            switch (choice) {
                case 1: // Search the local database
                    stockQuery(dbStockService);
                    break;
                case 2: // Search the online stock service
                    stockQuery(webStockService);
                    break;
                case 3: // Add a Person to the local database
                    addPersonToDatabase();
                    break;
                case 4: // Upload an XML file of stock quotes
                    uploadXmlFileToDatabase();
                    break;
                case 5: // Save online query results to local db
                    saveOnlineQueryToLocalDb();
                    break;
                case 0: // Exit the program
                    notDoneYet = Boolean.FALSE;
                    break;
            }
        }
    }

    /**
     * This private helper method prompts the user for a menu selection and returns that choice as
     * an integer. If the user enters something other than an integer or an integer out of range of
     * the menu options, the user will be re-prompted.
     *
     * @return the menu selection as an integer
     */
    private static int displayMenu() {

        String input = "";
        Scanner scanner = new Scanner(System.in);

        // while not an option keep prompting, because I can be a pest sometimes
        while (!input.matches("(^|\\W)[0-5]($|\\W)")) {
            System.out.println("\nPlease select from the following options:");
            System.out.println("(1) Query the local stocks database");
            System.out.println("(2) Query the online stocks service");
            System.out.println("(3) Add a user to the person table of the local database");
            System.out.println("(4) Upload an XML file containing stock quotes to the local database");
            System.out.println("(5) Query online stocks service and save result to local database");
            System.out.println("(0) Exit");

            input = scanner.nextLine();
        }

        return Integer.parseInt(input);
    }

    /**
     * This private helper method prompts the user for the ticker symbol they want to query and
     * searches the appropriate stock service for the data. If the symbol is not found, a message
     * is displayed indicating as much. Otherwise the price and date of the quote is displayed.
     *
     * @param stockService the stock service to query
     */
    private static void stockQuery(StockService stockService) {
        System.out.println("Enter the ticker symbol: ");
        Scanner scanner = new Scanner(System.in);
        String tickerSymbol = scanner.nextLine();
        StockQuoteDao quote;
        try {
            quote = stockService.getQuote(tickerSymbol);
            if (quote.getTickerSymbol().contains("Unknown")) {
                System.out.println("There is no stock data for: " + tickerSymbol);
            } else {
                System.out.printf("The most recent price for %s is $%.2f on %s\n", quote.getTickerSymbol(), quote.getValue(), quote.getQuoteDate());
            }
        } catch (StockServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This helper method prompts the user for a first and last name and adds that person to the
     * Person table of the local database.
     */
    private static void addPersonToDatabase() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the first name: ");
        String firstName = scanner.nextLine();
        System.out.print("Enter the last name: ");
        String lastName = scanner.nextLine();

        PersonDao person = new PersonDao();
        person.setFirstName(firstName);
        person.setLastName(lastName);

        UserStockService userStockService = new DatabaseUserStockService();
        userStockService.addOrUpdatePerson(person);
    }

    /**
     * This private helper method allows a user to upload an xml file to populate the stocks table
     * of the local database. It assumes the database already exists and contains the appropriate
     * table.
     */
    private static void uploadXmlFileToDatabase() {
        // read in xml file
        System.out.println("Enter path to XML file: ");
        Scanner scanner = new Scanner(System.in);
        String filePath = scanner.nextLine();
        String extension = getExtensionByStringHandling(filePath).toString();
        File xmlFile = new File(filePath);

        if (xmlFile.exists() && xmlFile.isFile() && extension.contains("xml")) {
            // parse stock_info.xml into JAXB classes
            JAXBContext jaxbContext;
            Stocks stocks = null;

            try {
                jaxbContext = JAXBContext.newInstance(Stocks.class);
                Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
                stocks = (Stocks) unmarshaller.unmarshal(xmlFile);
            } catch (JAXBException e) {
                System.err.println(e.getMessage());
            }

            if (stocks != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
                StockQuoteDao stockQuoteDao = new StockQuoteDao();

                try {
                    for (Stocks.StockQuote quote : stocks.getStockQuote()) {
                        Session session = getSessionFactory().openSession();
                        session.beginTransaction();

                        stockQuoteDao.setQuoteDate(LocalDate.parse(quote.getQuoteDate(), formatter));
                        stockQuoteDao.setTickerSymbol(quote.getSymbol());
                        stockQuoteDao.setValue(Double.parseDouble(quote.getPrice()));

                        session.save(stockQuoteDao);
                        session.getTransaction().commit();
                        session.close();
                    }
                    System.out.println("The data has been successfully written to the local database.");

                } catch (HibernateException e) {
                    System.err.println("Unable to execute query " + e);
                }
            } else {
                System.out.println(filePath + " is not a supported format");
            }
        } else {
            System.out.println(filePath + " is not a valid xml file");
        }
    }

    private static void saveOnlineQueryToLocalDb() {
        System.out.println("Enter the ticker symbol: ");
        Scanner scanner = new Scanner(System.in);
        String tickerSymbol = scanner.nextLine();
        StockQuoteDao quote;
        try {
            quote = webStockService.getQuote(tickerSymbol);
            if (quote.getTickerSymbol().contains("Unknown")) {
                System.out.println("There is no stock data for: " + tickerSymbol);
            } else {
                Session session = DatabaseUtils.getSessionFactory().openSession();
                Transaction transaction = null;

                try {
                    transaction = session.beginTransaction();
                    session.saveOrUpdate(quote);
                    transaction.commit();
                    System.out.println("Result saved to local database.");
                } catch (HibernateException e) {
                    if (transaction != null && transaction.isActive()) {
                        transaction.rollback(); // close transaction
                        System.out.println("Result could not be saved to local database.");
                    }
                } finally {
                    if (transaction != null && transaction.isActive()) {
                        transaction.commit();
                    }
                }
            }
        } catch (StockServiceException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * This private helper function takes a file name or path and returns the file's extension
     * by searching for the last occurrence of the '.' character.
     *
     * @param filename the name or full path of the file
     * @return the file's extension
     */
    private static Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }
}
