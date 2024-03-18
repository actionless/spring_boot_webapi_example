package com.example.demo.entity;

import java.math.BigDecimal;

import com.example.demo.entity.Transaction;


public class TransactionResponse {
    private int id;
    private BigDecimal amount;
    private int accountID;

    public TransactionResponse(Transaction transaction) {
        id = transaction.getId();
        amount = transaction.getAmount();
        accountID = transaction.getAccount().getId();
    }

    public int getId() {
        return id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public int getAccountID() {
        return accountID;
    }
}
