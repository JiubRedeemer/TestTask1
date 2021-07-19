package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.BankDB;
import com.haulmont.testtask.entities.Bank;
import com.haulmont.testtask.entities.Client;
import com.haulmont.testtask.entities.Credit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;
import java.util.List;

public class BankView extends VerticalLayout {
    private Grid<Client> gridClients = new Grid<>(Client.class);
    private Grid<Credit> gridCredits = new Grid<>(Credit.class);
    private Bank bank = new Bank();
    private BankDB bankDB = new BankDB();
    private Button update = new Button("Обновить");

    public BankView() throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        update.addClickListener(event -> {
            try {
                updateList();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        layout.addComponents(update);
        gridLayout.addComponents(gridClients, gridCredits);
        gridLayout.setExpandRatio(gridClients, 1);
        gridLayout.setExpandRatio(gridCredits, 1);
        addComponents(layout, gridLayout);
    }


    protected void updateList() throws SQLException {
        if (bankDB.getAllBanks().isEmpty()) bankDB.addBank(new Bank("MyBank"));
        bank = (Bank) bankDB.getAllBanks().get(0);
        List<Client> clients = bank.getClients();
        List<Credit> credits = bank.getCredits();
        if (clients != null)
            gridClients.setItems(clients);
        if (credits != null)
            gridCredits.setItems(credits);
    }

    private void gridConfigure() throws SQLException {
        if (bankDB.getAllBanks().isEmpty()) bankDB.addBank(new Bank("MyBank"));
        bank = (Bank) bankDB.getAllBanks().get(0);
        gridClients.setWidth("900");
        gridClients.setCaption("Клиенты");
        gridClients.setColumns("name", "surname", "patronymic", "phone", "email", "passport");
        gridClients.getColumn("name").setCaption("Имя");
        gridClients.getColumn("surname").setCaption("Фамилия");
        gridClients.getColumn("patronymic").setCaption("Отчество");
        gridClients.getColumn("phone").setCaption("Телефон");
        gridClients.getColumn("email").setCaption("Электронная почта");
        gridClients.getColumn("passport").setCaption("Серия и номер паспорта");

        gridCredits.setCaption("Кредиты");
        gridCredits.setWidth("900");
        gridCredits.setColumns("name", "limit", "percent");
        gridCredits.getColumn("name").setCaption("Название");
        gridCredits.getColumn("limit").setCaption("Лимит");
        gridCredits.getColumn("percent").setCaption("Процентная ставка");
        List<Client> clients = bank.getClients();
        List<Credit> credits = bank.getCredits();
        if (clients != null)
            gridClients.setItems(clients);
        if (credits != null)
            gridCredits.setItems(credits);
    }
}
