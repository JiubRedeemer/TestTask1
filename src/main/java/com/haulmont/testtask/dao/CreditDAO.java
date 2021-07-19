package com.haulmont.testtask.dao;

import com.haulmont.testtask.entities.Credit;

import java.sql.SQLException;
import java.util.List;

public interface CreditDAO {
    public void addCredit(Credit credit) throws SQLException;
    public void updateCredit(Credit credit) throws SQLException;
    public Credit getCreditById(String id) throws SQLException;
    public List getAllCredits() throws SQLException;
    public void deleteCredit(Credit credit) throws SQLException;
}
