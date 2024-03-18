package com.example.demo.service;

import java.util.List;
import java.util.HashMap;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.TransactionQuery;
import com.example.demo.entity.Transaction;

import com.example.demo.service.AccountService;


@Service
public class TransactionService {

    // Our internal structure to store transactions
    private static HashMap<Integer, Transaction> transactionStore = new HashMap<Integer, Transaction>();
    private static int idCounter = 0;

    private AccountService accountService;

    @Autowired
    public TransactionService(@Lazy AccountService accountService) {
        this.accountService = accountService;
    }

    public Transaction createOrUpdate(TransactionQuery newTransaction) {
		int id = idCounter++;
        Account account = accountService.getAccount(newTransaction.accountID);
        Transaction transaction = new Transaction(id, account, newTransaction.amount);
        account.setBalance(account.getBalance().add(transaction.getAmount()));
        transactionStore.put(id, transaction);
        return transaction;
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
    public List<Transaction> getTransactionsForAccount(int id) {
        return transactionStore.values().stream().filter(tr -> tr.getAccount().getId() == id).toList();
    }

	public void clear() {
		transactionStore.clear();
		idCounter = 0;
	}
}
