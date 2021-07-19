package com.haulmont.testtask.dao;


import com.haulmont.testtask.entities.Bank;

import java.sql.SQLException;
import java.util.List;

public interface BankDAO {
    public void addBank(Bank bank) throws SQLException;
    public void updateBank(Bank bank) throws SQLException;
    public Bank getBankById(String id) throws SQLException;
    public List getAllBanks() throws SQLException;
    public void deleteBank(Bank bank) throws SQLException;

}
