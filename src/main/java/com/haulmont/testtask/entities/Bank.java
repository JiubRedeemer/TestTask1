package com.haulmont.testtask.entities;

import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.transaction.Transactional;
import java.util.List;

@Entity
@Table(name = "BANK")
public class Bank {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name ="uuid", strategy = "uuid")
    @Column(name = "idBank")
    private String idBank;
    @Column(name = "name")
    private String name;

    @OneToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "idBankClient")
    private List<Client> clients;

    @OneToMany()
    @LazyCollection(LazyCollectionOption.FALSE)
    @JoinColumn(name = "idBankCredit")
    private List<Credit> credits;

    public Bank() {
    }

    public Bank(String name) {
        this.name = name;
    }

    public String getIdBank() {
        return idBank;
    }

    public void setIdBank(String idBank) {
        this.idBank = idBank;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Transactional
    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }

    @Transactional
    public List<Credit> getCredits() {
        return credits;
    }

    public void setCredits(List<Credit> credits) {
        this.credits = credits;
    }

    @Override
    public String toString() {
        return "Bank{" +
                "idBank='" + idBank + '\'' +
                ", name='" + name + '\'' +
                ", clients=" + clients +
                ", credits=" + credits +
                '}';
    }
}
