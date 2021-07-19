package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.Credit;
import com.haulmont.testtask.hibernate.HibernateUtil;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CreditDB implements CreditDAO{
    @Override
    public void addCredit(Credit credit) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(credit);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updateCredit(Credit credit) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(credit);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public Credit getCreditById(String id) {
        Session session = null;
        Credit credit = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            credit = (Credit) session.load(Credit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return credit;
    }

    @Override
    public List getAllCredits() {
        Session session = null;
        List credits = new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            credits = session.createQuery("SELECT credit FROM Credit credit").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return credits;
    }

    @Override
    public void deleteCredit(Credit credit) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(credit);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }
}
