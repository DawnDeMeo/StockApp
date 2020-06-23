package com.dawndemeo.stocks.services;

import com.dawndemeo.stocks.utilities.DatabaseUtils;
import com.dawndemeo.stocks.datamodels.PersonDao;
import com.dawndemeo.stocks.datamodels.StockQuoteDao;
import com.dawndemeo.stocks.datamodels.UserStocksDao;
import com.dawndemeo.stocks.exceptions.UserStockServiceException;
import com.dawndemeo.stocks.interfaces.UserStockService;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.ArrayList;
import java.util.List;

/**
 * @author dawndemeo
 */
public class DatabaseUserStockService implements UserStockService {

    @Override
    @SuppressWarnings("unchecked")
    public List<PersonDao> getPerson() throws UserStockServiceException {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        List<PersonDao> returnValue;
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(PersonDao.class);

            /*
              NOTE criteria.list(); generates unchecked warning so SuppressWarnings
             */
            returnValue = criteria.list();

        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();  // close transaction
            }
            throw new UserStockServiceException("Could not get PersonDao data." + e.getMessage(), e);
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }

        return returnValue;
    }

    /**
     * Add a new person or update an existing PersonDao's data
     *
     * @param person a person object to either update or create
     */
    @Override
    public void addOrUpdatePerson(PersonDao person) {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.saveOrUpdate(person);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }

    }

    /**
     * Get a list of all a person's stockQuotes
     *
     * @param person the person
     * @return a list of stockQuote instances
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<StockQuoteDao> getStockQuotes(PersonDao person) {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;
        List<StockQuoteDao> stockQuotes = new ArrayList<>();

        try {
            transaction = session.beginTransaction();
            Criteria criteria = session.createCriteria(UserStocksDao.class);
            criteria.add(Restrictions.eq("person", person));

            /*
              NOTE criteria.list(); generates unchecked warning so SuppressWarnings is used
             */
            List<UserStocksDao> list = criteria.list();

            for (UserStocksDao userStocks : list) {
                stockQuotes.add(userStocks.getStockQuote());
            }

            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback(); // close transaction
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
        return stockQuotes;
    }

    /**
     * Assign a stockQuote to a person.
     *
     * @param stockQuote the stockQuote to assign
     * @param person the person to assign the stockQuote to
     */
    @Override
    public void addStockQuoteToPerson(StockQuoteDao stockQuote, PersonDao person) {
        Session session = DatabaseUtils.getSessionFactory().openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            UserStocksDao userStocks = new UserStocksDao();
            userStocks.setStockQuote(stockQuote);
            userStocks.setPerson(person);
            session.saveOrUpdate(userStocks);
            transaction.commit();

        } catch (HibernateException e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
        } finally {
            if (transaction != null && transaction.isActive()) {
                transaction.commit();
            }
        }
    }
}
