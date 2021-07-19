package com.haulmont.testtask.web;

import com.haulmont.testtask.entities.ClientCredit;
import com.haulmont.testtask.entities.Payments;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PaymentsView extends VerticalLayout {
    public Grid<Payments> grid = new Grid<>(Payments.class);
    RadioButtonGroup<String> radioGroup = new RadioButtonGroup<>();
    public ClientCredit clientCredit;

    private List<Payments> paymentsListDiff = new ArrayList<>();
    private List<Payments> paymentsListAnn = new ArrayList<>();

    public PaymentsView() throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(grid);
        gridLayout.setExpandRatio(grid, 1);
        radioGroup.setItems("Дифференцированный платеж", "Аннуитетный платеж");
        radioGroup.setValue("Дифференцированный платеж");
        layout.addComponents(radioGroup);
        radioGroup.addValueChangeListener(event -> {
            if(event.getValue() == "Дифференцированный платеж") grid.setItems(paymentsListDiff);
            if(event.getValue() == "Аннуитетный платеж") grid.setItems(paymentsListAnn);
        });
        updateList();
        addComponents(layout, gridLayout);
    }


    protected void updateList() throws SQLException {
        grid.setItems(paymentsListDiff);
    }


    private void gridConfigure() throws SQLException {
        grid.setWidth("900");
        grid.setColumns("date", "sumPayment", "sumPaymentBody", "sumPaymentPercents", "balanceOwed");
        grid.getColumn("date").setCaption("Дата");
        grid.getColumn("sumPayment").setCaption("Полная сумма платежа");
        grid.getColumn("sumPaymentBody").setCaption("Платеж по телу кредита");
        grid.getColumn("sumPaymentPercents").setCaption("Платеж по процентам кредита");
        grid.getColumn("balanceOwed").setCaption("Остаток кредита");
        grid.setItems(paymentsListDiff);
    }

    public List<Payments> getPaymentsListDiff() {
        return paymentsListDiff;
    }

    public void setPaymentsListDiff(List<Payments> paymentsListDiff) {
        this.paymentsListDiff = paymentsListDiff;
    }

    public List<Payments> getPaymentsListAnn() {
        return paymentsListAnn;
    }

    public void setPaymentsListAnn(List<Payments> paymentsListAnn) {
        this.paymentsListAnn = paymentsListAnn;
    }
}
