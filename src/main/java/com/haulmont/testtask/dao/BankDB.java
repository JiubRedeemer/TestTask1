package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.Bank;
import com.haulmont.testtask.hibernate.HibernateUtil;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankDB implements BankDAO {

    @Override
    public void addBank(Bank bank) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(bank);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updateBank(Bank bank) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(bank);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Bank getBankById(String id) {
        Session session = null;
        Bank bank = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            bank = session.load(Bank.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return bank;
    }

    @Override
    public List getAllBanks() throws SQLException {
        Session session = null;
        List banks = new ArrayList<Bank>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            banks = session.createQuery("SELECT bank FROM Bank bank").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return banks;
    }

    @Override
    public void deleteBank(Bank bank) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(bank);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}
