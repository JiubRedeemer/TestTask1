package com.haulmont.testtask.web;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.dao.ClientCreditDB;
import com.haulmont.testtask.entities.ClientCredit;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.VerticalLayout;

import java.sql.SQLException;
import java.util.List;

public class CreditGiveView extends VerticalLayout {
    private Grid<ClientCredit> grid = new Grid<>(ClientCredit.class);
    private Button add = new Button("Добавить");
    private ClientCreditDB clientCreditDB = new ClientCreditDB();
    private CreditGiveEditUI addNew = new CreditGiveEditUI(new ClientCredit(), this);

    public CreditGiveView(PaymentsView paymentsView, MainUI mainUI) throws SQLException{

        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(grid, addNew);
        gridLayout.setExpandRatio(grid, 1);
        layout.addComponents(add);
        addNew.setPaymentsView(paymentsView);
        addNew.setMainUI(mainUI);
        add.addClickListener(clickEvent -> {addNew.setVisible(true); addNew.editConfigure(null);});
        updateList();
        addComponents(layout, gridLayout);
    }


    protected void updateList() throws SQLException{
        List<ClientCredit> clientCredits = clientCreditDB.getAllClientCredit();
        grid.setItems(clientCredits);
    }

    public void updateGrid() throws SQLException{
        grid.setItems(clientCreditDB.getAllClientCredit());
    }
    private void gridConfigure() throws SQLException {
        grid.setWidth("900");
        grid.setColumns("clientFullName", "creditName", "creditSum", "time");
        grid.getColumn("clientFullName").setCaption("ФИО");
        grid.getColumn("creditName").setCaption("Название кредита");
        grid.getColumn("creditSum").setCaption("Сумма кредита");
        grid.getColumn("time").setCaption("Время выплат");
        List<ClientCredit> clientCredits = clientCreditDB.getAllClientCredit();
        grid.setItems(clientCredits);
        grid.asSingleSelect().addSingleSelectionListener(event -> addNew.editConfigure(event.getValue()));
    }
}
