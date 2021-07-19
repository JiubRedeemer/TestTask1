package com.haulmont.testtask.entities;

import javax.persistence.*;
import java.time.LocalDate;


@Entity
public class Payments {

    @Id
    @Column(name = "idClientCredit")
    private String idClientCredit;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "sumPayment")
    private long sumPayment;
    @Column(name = "sumPaymentBody")
    private long sumPaymentBody;
    @Column(name = "sumPaymentPercents")
    private long sumPaymentPercents;
    @Column(name = "balanceOwed")
    private long balanceOwed;

    public Payments() {
    }


    public Payments(LocalDate date, long sumPayment, long sumPaymentBody, long sumPaymentPercents, long balanceOwed) {
        this.date = date;
        this.sumPayment = sumPayment;
        this.sumPaymentBody = sumPaymentBody;
        this.sumPaymentPercents = sumPaymentPercents;
        this.balanceOwed = balanceOwed;
    }

    public String getIdClientCredit() {
        return idClientCredit;
    }

    public void setIdClientCredit(String idClientCredit) {
        this.idClientCredit = idClientCredit;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public long getSumPayment() {
        return sumPayment;
    }

    public void setSumPayment(long sumPayment) {
        this.sumPayment = sumPayment;
    }

    public long getSumPaymentBody() {
        return sumPaymentBody;
    }

    public void setSumPaymentBody(long sumPaymentBody) {
        this.sumPaymentBody = sumPaymentBody;
    }

    public long getSumPaymentPercents() {
        return sumPaymentPercents;
    }

    public void setSumPaymentPercents(long sumPaymentPercents) {
        this.sumPaymentPercents = sumPaymentPercents;
    }

    public long getBalanceOwed() {
        return balanceOwed;
    }

    public void setBalanceOwed(long balanceOwed) {
        this.balanceOwed = balanceOwed;
    }

    @Override
    public String toString() {
        return "Payments{" +
                "idClientCredit='" + idClientCredit + '\'' +
                ", date=" + date +
                ", sumPayment=" + sumPayment +
                ", sumPaymentBody=" + sumPaymentBody +
                ", sumPaymentPercents=" + sumPaymentPercents +
                ", balanceOwed=" + balanceOwed +
                '}';
    }
}
