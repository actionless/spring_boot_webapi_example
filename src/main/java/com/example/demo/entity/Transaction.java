package com.example.demo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Date;

import com.example.demo.entity.Account;


@Entity
public class Transaction {

    @Id
    private int id;

    @JoinColumn(name = "accountID", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    private BigDecimal amount = new BigDecimal(0);
    private Date createdAt = Date.from(java.time.Instant.now());

    public Transaction(int id, Account account, BigDecimal amount) {
		this.id = id;
        this.account = account;
        this.amount = amount;
    }

    public int getId() {
        return id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public Account getAccount() {
        return account;
    }
}
