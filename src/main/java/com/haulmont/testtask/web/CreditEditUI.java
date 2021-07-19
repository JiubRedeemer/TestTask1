package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.BankDB;
import com.haulmont.testtask.dao.ClientCreditDB;
import com.haulmont.testtask.dao.CreditDB;
import com.haulmont.testtask.entities.Bank;
import com.haulmont.testtask.entities.Credit;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;

public class CreditEditUI extends VerticalLayout {

    CreditView creditView;

    private CreditDB creditDB = new CreditDB();
    private ClientCreditDB clientCreditDB = new ClientCreditDB();
    private TextField tfName = new TextField("Название");
    private TextField tfLimit = new TextField("Лимит");
    private TextField tfPercent = new TextField("Процентная ставка");


    private Button add = new Button("Добавить");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Обновить");
    private Button cancel = new Button("Отмена");
    private Credit credit;

    private Bank bank;
    private BankDB bankDB = new BankDB();

    public CreditEditUI(Credit credit, CreditView creditView) {
        try {
            this.bank = (Bank) bankDB.getAllBanks().get(0);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        this.credit = credit;
        setVisible(false);
        setWidthUndefined();
        HorizontalLayout layout = new HorizontalLayout();
        cancel.addClickListener(event -> this.setVisible(false));
        addClickListeners(creditView);
        layout.addComponents(add, delete, update, cancel);
        addComponents(tfName, tfLimit, tfPercent, layout);
    }

    public void editConfigure(Credit credit) {
        setVisible(true);
        if (credit == null) {
            clear();
            add.setVisible(true);
            delete.setVisible(false);
            update.setVisible(false);
        } else {
            this.credit = credit;
            setCredit(credit);
            add.setVisible(false);
            delete.setVisible(true);
            update.setVisible(true);
        }
        tfName.setPlaceholder("Введите название");
        tfLimit.setPlaceholder("Введите лимит");
        tfPercent.setPlaceholder("Введите процентную ставку");

    }

    private void clear() {
        tfName.clear();
        tfLimit.clear();
        tfPercent.clear();
    }

    private void addClickListeners(CreditView creditView) {
        add.addClickListener(event -> {
            try {
                if (fieldCheck()) {
                    addCredit();
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        update.addClickListener(event -> {
            try {
                if (fieldCheck()) {
                    updateCredit(credit);
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });

        delete.addClickListener(event -> {
            try {
                if (credit.getClientCredits().isEmpty()) {
                    creditDB.deleteCredit(credit);
                    creditView.updateGrid();
                    this.setVisible(false);
                    clear();

                } else delete.setComponentError(new UserError("Данный кредит используется"));

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private boolean fieldCheck() {
        String rgxLimit = "^-?\\d+$";
        String rgxPercent = "^[0-9]*[.,]?[0-9]+$";
        if (tfName.isEmpty() || tfLimit.isEmpty() || tfPercent.isEmpty()) {
            add.setComponentError(new UserError("Не все поля введены"));
            return false;
        } else {
            add.setComponentError(null);
            tfLimit.setComponentError(null);
            tfPercent.setComponentError(null);

            if (!tfLimit.getValue().matches(rgxLimit)) {
                tfLimit.setComponentError(
                        new UserError("Введите максимальную сумму кредита, например: 1000000"));
                return false;
            }
            if (!tfPercent.getValue().matches(rgxPercent)) {
                tfPercent.setComponentError(
                        new UserError("Введите процентную ставку, например: 12.4"));
                return false;
            }
            if (Long.parseLong(tfLimit.getValue())<1) {
                tfLimit.setComponentError(
                        new UserError("Введите лимит более 1 рубля"));
                return false;
            }


            return true;
        }
    }

    private void setCredit(Credit credit) {
        tfName.setValue(credit.getName());
        tfLimit.setValue(Long.toString(credit.getLimit()));
        tfPercent.setValue(Float.toString(credit.getPercent() * 100));

    }

    private void addCredit() throws SQLException {
        Credit credit = new Credit(tfName.getValue(),
                Long.parseLong(tfLimit.getValue()),
                (float)((Math.round(Float.parseFloat(tfPercent.getValue()) * 10d) / 10d) / 100));
        credit.setBank(bank);
        bank.getCredits().add(credit);
        creditDB.addCredit(credit);

    }

    private void updateCredit(Credit credit) throws SQLException {
        bank.getCredits().remove(credit);
        bankDB.updateBank(credit.getBank());

        credit.setBank(bank);
        bank.getCredits().add(credit);
        bankDB.updateBank(credit.getBank());

        credit.setName(tfName.getValue());
        credit.setLimit(Long.parseLong(tfLimit.getValue()));
        credit.setPercent((float)((Math.round(Float.parseFloat(tfPercent.getValue()) * 10d) / 10d) / 100));

        creditDB.updateCredit(credit);
        Page.getCurrent().reload();

    }
}

