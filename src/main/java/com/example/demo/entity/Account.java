package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Date;

import com.example.demo.entity.Customer;

@Entity
public class Account {

    @Id
    private int id;

    @JoinColumn(name = "customerID", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    private BigDecimal balance = new BigDecimal(0);
    private Date createdAt = Date.from(java.time.Instant.now());

    public Account(int id, Customer customer) {
        this.id = id;
        this.customer = customer;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public int getId() {
        return id;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public Customer getCustomer() {
        return customer;
    }
}
