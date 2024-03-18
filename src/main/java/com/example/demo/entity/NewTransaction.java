package com.example.demo.entity;

import java.math.BigDecimal;

public class NewTransaction {
    public int accountID;
    public BigDecimal amount;

    public NewTransaction(int accountID, BigDecimal amount) {
        this.accountID = accountID;
        this.amount = amount;
    }
}
