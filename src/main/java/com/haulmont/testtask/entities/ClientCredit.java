package com.haulmont.testtask.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class ClientCredit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "idClientCredit")
    private String idClientCredit;

    @Column(name = "clientFullName")
    private String clientFullName;

    @Column(name = "creditName")
    private String creditName;

    @Column(name = "creditSum")
    private long creditSum;

    @Column(name = "percent")
    private float percent;

    @Column(name = "time")
    private long time;

    @Column(name = "start")
    private LocalDate start;

    @ManyToOne()
    @JoinColumn(name = "idСlient")
    private Client client;


    @ManyToOne()
    @JoinColumn(name = "idCredit")
    private Credit credit;

    public ClientCredit() {
    }

    public ClientCredit(long creditSum, long time) {
        this.creditSum = creditSum;
        this.time = time;
    }

    public String getIdClientCredit() {
        return idClientCredit;
    }

    public void setIdClientCredit(String idClientCredit) {
        this.idClientCredit = idClientCredit;
    }

    public String getClientFullName() {
        return clientFullName;
    }

    public void setClientFullName(String clientFullName) {
        this.clientFullName = clientFullName;
    }

    public String getCreditName() {
        return creditName;
    }

    public void setCreditName(String creditName) {
        this.creditName = creditName;
    }

    public long getCreditSum() {
        return creditSum;
    }

    public void setCreditSum(long creditSum) {
        this.creditSum = creditSum;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }


    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        if (client != null)
            this.clientFullName = client.getFIO();
        else this.clientFullName = null;
        this.client = client;
    }

    public Credit getCredit() {
        return credit;
    }

    public void setCredit(Credit credit) {
        if (credit != null) {
            this.creditName = credit.getName();
            this.percent = credit.getPercent();
        } else this.creditName = null;
        this.credit = credit;

    }

    public LocalDate getStart() {
        return start;
    }

    public void setStart(LocalDate date) {
        this.start = date;
    }

    @Override
    public String toString() {
        return "ClientCredit{" +
                "idClientCredit='" + idClientCredit + '\'' +
                ", clientFullName='" + clientFullName + '\'' +
                ", creditName='" + creditName + '\'' +
                ", creditSum=" + creditSum +
                ", percent='" + percent + '\'' +
                ", time=" + time +
                ", client=" + client +
                ", credit=" + credit +
                '}';
    }
}
