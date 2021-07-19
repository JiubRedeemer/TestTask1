package com.haulmont.testtask.entities;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity
public class Credit {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid")
    @Column(name = "idCredit")
    private String idCredit;

    @Column(name = "name")
    private String name;

    @Column(name = "limit")
    private long limit;

    @Column(name = "percent")
    private float percent;

    @ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "idBankCredit")
    private Bank bank;

    @OneToMany(fetch = FetchType.EAGER, orphanRemoval=true)
    @JoinColumn(name = "idCredit")
    private List<ClientCredit> clientCredits;

    public Credit() {
    }

    public Credit(String name, long limit, float percent) {
        this.name = name;
        this.limit = limit;
        this.percent = percent;
    }

    public String getIdCredit() {
        return idCredit;
    }

    public void setIdCredit(String idCredit) {
        this.idCredit = idCredit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getLimit() {
        return limit;
    }

    public void setLimit(long limit) {
        this.limit = limit;
    }

    public float getPercent() {
        return percent;
    }

    public void setPercent(float percent) {
        this.percent = percent;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public List<ClientCredit> getClientCredits() {
        return clientCredits;
    }

    public void setClientCredits(List<ClientCredit> clientCredits) {
        for (ClientCredit clientCredit:
                clientCredits) {
            clientCredit.setCreditName(name);
            clientCredit.setCredit(this);
            clientCredit.setPercent(this.percent);
        }
        this.clientCredits = clientCredits;
    }

    @Override
    public String toString() {
        return "Credit{" +
                "idCredit='" + idCredit + '\'' +
                ", name='" + name + '\'' +
                ", limit=" + limit +
                ", percent=" + percent +
                ", bank=" + bank +
                ", clientCredits=" + clientCredits +
                '}';
    }
}
