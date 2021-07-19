package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.Client;

import java.sql.SQLException;
import java.util.List;

public interface ClientDAO {
    public void addClient(Client client) throws SQLException;
    public void updateClient(Client client) throws SQLException;
    public Client getClientById(String id) throws SQLException;
    public List getAllClients() throws SQLException;
    public void deleteClient(Client client) throws SQLException;
}
