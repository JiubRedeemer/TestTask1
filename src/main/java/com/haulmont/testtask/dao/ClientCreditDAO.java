package com.haulmont.testtask.dao;


import com.haulmont.testtask.entities.ClientCredit;


import java.sql.SQLException;
import java.util.List;

public interface ClientCreditDAO {
    public void addClientCredit(ClientCredit clientCredit) throws SQLException;
    public void updateClientCredit(ClientCredit clientCredit) throws SQLException;
    public ClientCredit getClientCreditById(String id) throws SQLException;
    public List getAllClientCredit() throws SQLException;
    public void deleteClientCredit(ClientCredit clientCredit) throws SQLException;

}
