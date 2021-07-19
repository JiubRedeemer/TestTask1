package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.ClientCredit;
import com.haulmont.testtask.hibernate.HibernateUtil;
import org.hibernate.Session;


import java.util.ArrayList;
import java.util.List;

public class ClientCreditDB implements ClientCreditDAO {
    @Override
    public void addClientCredit(ClientCredit clientCredit) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(clientCredit);
            session.getTransaction().commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public void updateClientCredit(ClientCredit clientCredit) {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(clientCredit);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }

    @Override
    public ClientCredit getClientCreditById(String id) {
        Session session = null;
        ClientCredit clientCredit = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clientCredit = session.load(ClientCredit.class, id);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return clientCredit;
    }

    @Override
    public List getAllClientCredit() {
        Session session = null;
        List clientCredits = new ArrayList<Client>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            clientCredits = session.createQuery("SELECT clientCredit FROM ClientCredit clientCredit").list();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return clientCredits;
    }

    @Override
    public void deleteClientCredit(ClientCredit clientCredit) {
        if (clientCredit.getClient() != null) clientCredit.setClient(null);
        if (clientCredit.getCredit() != null) clientCredit.setCredit(null);
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(clientCredit);
            session.getTransaction().commit();

        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (session != null && session.isOpen()) session.close();
        }
    }


}
