package com.example.demo.service;

import java.util.List;
import java.util.HashMap;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.NewTransaction;
import com.example.demo.entity.Transaction;

import com.example.demo.service.AccountService;


@Service
public class TransactionService {

    // Our internal structure to store transactions
    private static HashMap<Integer, Transaction> transactionStore = new HashMap<Integer, Transaction>();
    private AccountService accountService = new AccountService();

    public Transaction createOrUpdate(NewTransaction newTransaction) {
        Account account = accountService.getAccount(newTransaction.accountID);
        Transaction transaction = new Transaction(account, newTransaction.amount);
        return transactionStore.put(transaction.getId(), transaction);
    }
    public Transaction getTransaction(int id) {
        return transactionStore.get(id);
    }
    public void delete(int id) {
        if(transactionStore.containsKey(id))
            transactionStore.remove(id);
    }
    public List<Transaction> getAllTransactions() {
        return transactionStore.values().stream().toList();
    }
}
