package com.example.demo.entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.Transaction;


class AccountResponseTransaction {
    private int id;
    private BigDecimal amount;

    public AccountResponseTransaction(Transaction transaction) {
        id = transaction.getId();
        amount = transaction.getAmount();
    }

    public int getId() {
        return id;
    }
    public BigDecimal getAmount() {
        return amount;
    }
}

public class AccountResponse {
    private int id;
    private Customer customer;
    private BigDecimal balance;
    private Date createdAt;
    private List<AccountResponseTransaction> transactions;

    public AccountResponse (Account account, List<Transaction> transactions) {
        this.id = account.getId();
        this.customer = account.getCustomer();
        this.balance = account.getBalance();
        this.transactions = transactions.stream().map(tr -> new AccountResponseTransaction(tr)).toList();
    }

    public int getId() {
        return id;
    }
    public BigDecimal getBalance() {
        return balance;
    }
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }
    public Customer getCustomer() {
        return customer;
    }
    public List<AccountResponseTransaction> getTransactions() {
        return transactions;
    }
}
