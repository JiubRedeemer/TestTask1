package com.haulmont.testtask;

import com.haulmont.testtask.web.*;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import java.sql.*;

@Theme(ValoTheme.THEME_NAME)
public class MainUI extends UI {
    TabSheet creditsTabSheet = new TabSheet();
    @Override
    protected void init(VaadinRequest request) {

        VerticalLayout layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        setContent(layout);
        TabSheet tabSheet = new TabSheet();
        TabSheet bankTabSheet = new TabSheet();



        try {
            PaymentsView paymentsView = new PaymentsView();
            BankView bankView = new BankView();

            layout.addComponent(tabSheet);

            tabSheet.addTab(creditsTabSheet, "Выдача кредитов");
            tabSheet.addTab(bankTabSheet, "Банк");

            bankTabSheet.addTab(bankView, "База данных банка");
            bankTabSheet.addTab(new ClientView(), "Клиенты");
            bankTabSheet.addTab(new CreditView(), "Кредиты");

            creditsTabSheet.addTab(new CreditGiveView(paymentsView, this), "Выданные кредиты");
            creditsTabSheet.addTab(paymentsView, "График выплат");
            creditsTabSheet.getTab(1).setVisible(false);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        setContent(layout);
    }

    public void editPaymentTabName(String newName){
        creditsTabSheet.getTab(1).setCaption(newName);
    }

    public void visiblePaymentTab(boolean state){
        creditsTabSheet.getTab(1).setVisible(state);

    }
}