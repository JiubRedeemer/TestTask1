package com.haulmont.testtask.web;

import com.haulmont.testtask.MainUI;
import com.haulmont.testtask.dao.BankDB;
import com.haulmont.testtask.dao.ClientCreditDB;
import com.haulmont.testtask.dao.ClientDB;
import com.haulmont.testtask.dao.CreditDB;
import com.haulmont.testtask.entities.*;
import com.vaadin.server.Page;
import com.vaadin.server.UserError;
import com.vaadin.ui.*;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CreditGiveEditUI extends VerticalLayout {

    private ComboBox<Client> clientComboBox = new ComboBox<>("Клиент");
    private ComboBox<Credit> creditComboBox = new ComboBox<>("Кредит");
    private TextField tfCreditSum = new TextField("Сумма кредита");
    private TextField tfTimeOfCredit = new TextField("Длительность в месяцах");
    private DateField dateField = new DateField("Дата начала платежей");

    private Button add = new Button("Добавить");
    private Button cancel = new Button("Отмена");
    private Button delete = new Button("Удалить");
    private Button update = new Button("Обновить");
    private Button calculate = new Button("Составить график платежей");
    private Label paymentSumDiff = new Label();
    private Label overpayDiff = new Label();
    private Label paymentSumAnn = new Label();
    private Label overpayAnn = new Label();

    private CreditGiveView creditGiveView;
    private ClientCredit clientCredit;
    private PaymentsView paymentsView;
    private MainUI mainUI;

    private ClientCreditDB clientCreditDB = new ClientCreditDB();
    private ClientDB clientDB = new ClientDB();
    private CreditDB creditDB = new CreditDB();
    private List<Payments> paymentsListDiff = new ArrayList<>();
    private List<Payments> paymentsListAnn = new ArrayList<>();
    BankDB bankDB = new BankDB();
    Bank bank = new Bank();
    public CreditGiveEditUI(ClientCredit clientCredit, CreditGiveView creditGiveView) throws SQLException {
        bank = (Bank) bankDB.getAllBanks().get(0);
        this.clientCredit = clientCredit;
        this.creditGiveView = creditGiveView;
        setVisible(false);
        setWidthUndefined();
        HorizontalLayout layout = new HorizontalLayout();
        cancel.addClickListener(event -> {this.setVisible(false);
            try {
                updateSelects();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        addClickListeners(creditGiveView);
        updateSelects();
        layout.addComponents(add, update, delete, cancel, calculate);
        addComponents(clientComboBox, creditComboBox, tfCreditSum, tfTimeOfCredit, dateField, paymentSumDiff, overpayDiff, paymentSumAnn,  overpayAnn, layout);
    }

    public void editConfigure(ClientCredit clientCredit) {
        setVisible(true);
        paymentSumDiff.setVisible(false);
        overpayDiff.setVisible(false);
        paymentSumAnn.setVisible(false);
        overpayAnn.setVisible(false);
        try {
            updateSelects();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if (clientCredit == null) {
            clear();
            add.setVisible(true);
            delete.setVisible(false);
            update.setVisible(false);
            calculate.setVisible(false);

        } else {
            clear();
            this.clientCredit = clientCredit;
            add.setVisible(false);
            delete.setVisible(true);
            update.setVisible(true);
            calculate.setVisible(true);
        }
        overpayAnn.setCaption("Сумма выплат процентов по аннуитетный платежам");
        overpayDiff.setCaption("Сумма выплат процентов по дифференцированным платежам");
        paymentSumDiff.setCaption("Сумма выплат по дифференцированным платежам");
        paymentSumAnn.setCaption("Сумма выплат по аннуитетный платежам");
        tfCreditSum.setPlaceholder("Введите сумму кредита");
        tfTimeOfCredit.setPlaceholder("Введите длительность выплат в месяцах");
        clientComboBox.setPlaceholder("Выберите клиента");
        creditComboBox.setPlaceholder("Выберите кредит");

    }


    private void clear() {
        clientComboBox.clear();
        creditComboBox.clear();
        tfTimeOfCredit.clear();
        tfCreditSum.clear();
        dateField.clear();
    }

    private void addClickListeners(CreditGiveView creditGiveView) {

        calculate.addClickListener(clickEvent -> {
            try {
                mainUI.editPaymentTabName("График выплат: " + clientCredit.getClient().getFIO());
                mainUI.visiblePaymentTab(true);
                createDiffPayment(clientCredit);
                createAnnPayment(clientCredit);
                paymentsView.grid.setItems(paymentsListDiff);

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
        add.addClickListener(clickEvent -> {
            if (filedCheck()) {
                try {
                    addClientCredit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    creditGiveView.updateGrid();
                    this.setVisible(false);
                    clear();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });

        update.addClickListener(clickEvent -> {
            if (filedCheck()) {
                try {
                    updateClientCredit(clientCredit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                try {
                    creditGiveView.updateGrid();
                    this.setVisible(false);
                    clear();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }

        });

        delete.addClickListener(clickEvent -> {
            try {
                deleteClientCredit(clientCredit);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            try {
                creditGiveView.updateGrid();
                this.setVisible(false);
                clear();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void addClientCredit() throws Exception {
        ClientCredit clientCredit = new ClientCredit(Long.parseLong(tfCreditSum.getValue()),
                Long.parseLong(tfTimeOfCredit.getValue()));
        clientCredit.setClient(clientComboBox.getValue());
        clientCredit.setCredit(creditComboBox.getValue());
        clientCredit.setStart(dateField.getValue());
        clientComboBox.getValue().getClientCredits().add(clientCredit);
        creditComboBox.getValue().getClientCredits().add(clientCredit);
        clientCreditDB.addClientCredit(clientCredit);
    }

    private void updateClientCredit(ClientCredit clientCredit) throws Exception {

        clientCredit.getClient().getClientCredits().remove(clientCredit);
        clientCredit.getCredit().getClientCredits().remove(clientCredit);

        clientCredit.setClient(clientComboBox.getValue());
        clientCredit.setCredit(creditComboBox.getValue());
        clientCredit.getClient().getClientCredits().add(clientCredit);
        clientCredit.getCredit().getClientCredits().add(clientCredit);



        clientCredit.setCreditSum(Long.parseLong(tfCreditSum.getValue()));
        clientCredit.setTime(Long.parseLong(tfTimeOfCredit.getValue()));
        clientCredit.setStart(dateField.getValue());
        clientCreditDB.updateClientCredit(clientCredit);


        Page.getCurrent().reload();
    }

    private void deleteClientCredit(ClientCredit clientCredit) throws SQLException {
        bank.getClients().remove(clientCredit.getClient());

        clientCredit.getClient().getClientCredits().remove(clientCredit);
        clientCredit.getCredit().getClientCredits().remove(clientCredit);


        bank.getClients().add(clientCredit.getClient());
        bankDB.updateBank(bank);
        clientCreditDB.deleteClientCredit(clientCredit);
        creditGiveView.updateGrid();

        this.setVisible(false);
        clear();
        Page.getCurrent().reload();
    }

    private boolean filedCheck() {
        String rgxTimeOfCredit = "^-?\\d+$";
        String rgxCreditSum = "^-?\\d+$";
        if (clientComboBox.isEmpty() || creditComboBox.isEmpty() || tfCreditSum.isEmpty() || tfTimeOfCredit.isEmpty() || dateField.isEmpty()) {
            add.setComponentError(new UserError("Не все поля заполнены"));
            return false;
        } else {
            add.setComponentError(null);
            tfTimeOfCredit.setComponentError(null);
            tfCreditSum.setComponentError(null);

            if (!tfTimeOfCredit.getValue().matches(rgxTimeOfCredit)) {
                tfTimeOfCredit.setComponentError(
                        new UserError("Введите количество месяцев, например: 24"));
                return false;
            }
            if (!tfCreditSum.getValue().matches(rgxCreditSum)) {
                tfCreditSum.setComponentError(
                        new UserError("Введите сумму кредита, например: 100000"));
                return false;
            }

            if(Long.parseLong(tfCreditSum.getValue())<1){
                tfCreditSum.setComponentError(
                        new UserError("Введите сумму кредита более 1 рубля"));
                return false;
            }

            if(Long.parseLong(tfCreditSum.getValue())>creditComboBox.getValue().getLimit()){
                tfCreditSum.setComponentError(
                        new UserError("Сумма кредита не может превышать лимит кредита"));
                return false;
            }
            if(Long.parseLong(tfTimeOfCredit.getValue())>1000){
                tfTimeOfCredit.setComponentError(
                        new UserError("Введите действительную длительность выплат"));
                return false;
            }

            return true;
        }
    }

    private void updateSelects() throws SQLException {
        bank = (Bank) bankDB.getAllBanks().get(0);
        List<Client> clients = bank.getClients();
        List<Credit> credits = bank.getCredits();
        clientComboBox.setItems(clients);
        clientComboBox.setItemCaptionGenerator(Client::getFIO);
        creditComboBox.setItems(credits);
        creditComboBox.setItemCaptionGenerator(Credit::getName);
    }

    private void createDiffPayment(ClientCredit clientCredit) throws SQLException {
        long sumLabel = 0;
        long overpayLabel = 0;
        paymentsListDiff = new ArrayList<>();
        LocalDate currentDate = clientCredit.getStart();
        long owed = clientCredit.getCreditSum();
        long timePaid = 1L;
        for (int i = 0; i < clientCredit.getTime(); i++) {
            Payments payment = new Payments();
            payment.setDate(currentDate);
            currentDate = currentDate.plusMonths(1);
            payment.setSumPaymentBody(clientCredit.getCreditSum() / clientCredit.getTime());
            payment.setSumPaymentPercents((long) (owed * (clientCredit.getPercent() / 12)));
            owed = clientCredit.getCreditSum() - (payment.getSumPaymentBody() * timePaid);
            timePaid++;
            payment.setSumPayment(payment.getSumPaymentBody() + payment.getSumPaymentPercents());
            payment.setBalanceOwed(owed);

            sumLabel += payment.getSumPayment();
            overpayLabel += payment.getSumPaymentPercents();

            paymentsListDiff.add(payment);

        }

        paymentsListDiff.get(paymentsListDiff.size() - 1).setSumPayment(paymentsListDiff.get(paymentsListDiff.size() - 1).getSumPayment() + owed);
        paymentsListDiff.get(paymentsListDiff.size() - 1).setBalanceOwed(0);
        paymentsView.setPaymentsListDiff(paymentsListDiff);

        paymentSumDiff.setValue(sumLabel+"");
        overpayDiff.setValue(overpayLabel+"");
        paymentSumDiff.setVisible(true);
        overpayDiff.setVisible(true);
    }

    private void createAnnPayment(ClientCredit clientCredit) throws SQLException {
        long sumLabel = 0;
        long overpayLabel = 0;
        paymentsListAnn = new ArrayList<>();
        LocalDate currentDate = clientCredit.getStart();
        long owed = clientCredit.getCreditSum();
        long timePaid = 0L;
        for (int i = 0; i < clientCredit.getTime(); i++) {
            Payments payment = new Payments();
            payment.setDate(currentDate);
            currentDate = currentDate.plusMonths(1);

            payment.setSumPayment((long) (clientCredit.getCreditSum() * ((clientCredit.getPercent() / 12.0f) +
                    ((clientCredit.getPercent() / 12.0f) /
                            ((Math.pow(1 + clientCredit.getPercent() / 12, clientCredit.getTime()) - 1))))));
            payment.setSumPaymentPercents((long) (owed * clientCredit.getPercent() / 12));
            payment.setSumPaymentBody(payment.getSumPayment() - payment.getSumPaymentPercents());
            owed -= payment.getSumPaymentBody();
            payment.setBalanceOwed(owed);

            sumLabel += payment.getSumPayment();
            overpayLabel += payment.getSumPaymentPercents();

            paymentsListAnn.add(payment);

        }
        paymentsListAnn.get(paymentsListAnn.size() - 1).setSumPayment(paymentsListAnn.get(paymentsListAnn.size() - 1).getSumPayment() + owed);
        paymentsListAnn.get(paymentsListAnn.size() - 1).setBalanceOwed(0);
        paymentsView.setPaymentsListAnn(paymentsListAnn);

        paymentSumAnn.setValue(sumLabel+"");
        overpayAnn.setValue(overpayLabel+"");
        paymentSumAnn.setVisible(true);
        overpayAnn.setVisible(true);
    }

    public PaymentsView getPaymentsView() {
        return paymentsView;
    }

    public void setPaymentsView(PaymentsView paymentsView) {
        this.paymentsView = paymentsView;
    }

    public MainUI getMainUI() {
        return mainUI;
    }

    public void setMainUI(MainUI mainUI) {
        this.mainUI = mainUI;
    }
}

