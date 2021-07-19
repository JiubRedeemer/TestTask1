package com.haulmont.testtask.web;

import com.haulmont.testtask.dao.ClientDB;
import com.haulmont.testtask.entities.Client;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.util.List;

public class ClientView extends VerticalLayout {
    private Grid<Client> grid = new Grid<>(Client.class);
    private Button add = new Button("Добавить");
    private ClientDB clientDB = new ClientDB();
    private ClientEditUI addNew = new ClientEditUI(new Client(), this);

    public ClientView() throws SQLException{
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
        List<Client> clients = clientDB.getAllClients();
        grid.setItems(clients);
    }

    public void updateGrid() throws SQLException{
        grid.setItems(clientDB.getAllClients());
    }
    private void gridConfigure() throws SQLException {
        grid.setWidth("900");
        grid.setColumns("name", "surname", "patronymic", "phone", "email", "passport");
        grid.getColumn("name").setCaption("Имя");
        grid.getColumn("surname").setCaption("Фамилия");
        grid.getColumn("patronymic").setCaption("Отчество");
        grid.getColumn("phone").setCaption("Номер телефона");
        grid.getColumn("email").setCaption("E-mail");
        grid.getColumn("passport").setCaption("Серия и номер паспорта");
        List<Client> clients = clientDB.getAllClients();
        grid.setItems(clients);
        grid.asSingleSelect().addSingleSelectionListener(event -> addNew.editConfigure(event.getValue()));
    }
}
