package com.example.demo.entity;

import java.math.BigDecimal;

public class TransactionQuery {
    public int accountID;
    public BigDecimal amount;

    public TransactionQuery(int accountID, BigDecimal amount) {
        this.accountID = accountID;
        this.amount = amount;
    }
}
