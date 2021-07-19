package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.CreditDB;
import com.haulmont.testtask.entities.Credit;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class CreditView extends VerticalLayout {
    private Grid<Credit> grid = new Grid<>(Credit.class);
    private Button add = new Button("Добавить");
    private CreditDB creditDB = new CreditDB();
    private CreditEditUI addNew = new CreditEditUI(new Credit(), this);

    public CreditView() throws SQLException {
        setSizeFull();
        gridConfigure();
        HorizontalLayout layout = new HorizontalLayout();
        HorizontalLayout gridLayout = new HorizontalLayout();
        gridLayout.setSizeFull();
        gridLayout.addComponents(grid, addNew);
        gridLayout.setExpandRatio(grid, 1);
        layout.addComponents(add);
        add.addClickListener(clickEvent -> {addNew.setVisible(true); addNew.editConfigure(null);});
        updateList();
        addComponents(layout, gridLayout);
    }


    protected void updateList() throws SQLException{
        List<Credit> credits = creditDB.getAllCredits();
        grid.setItems(credits);
    }

    public void updateGrid() throws SQLException{
        grid.setItems(creditDB.getAllCredits());
    }
    private void gridConfigure() throws SQLException {
        grid.setWidth("900");
        grid.setColumns("name", "limit", "percent");
        grid.getColumn("name").setCaption("Название кредита");
        grid.getColumn("limit").setCaption("Лимит");
        grid.getColumn("percent").setCaption("Процентная ставка");
        List<Credit> credits = creditDB.getAllCredits();
        grid.setItems(credits);
        grid.asSingleSelect().addSingleSelectionListener(event -> addNew.editConfigure(event.getValue()));
    }
}
