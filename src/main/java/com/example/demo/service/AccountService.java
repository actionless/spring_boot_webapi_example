package com.example.demo.service;

import java.util.List;
import java.util.HashMap;
import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.AccountQuery;
import com.example.demo.entity.TransactionQuery;
import com.example.demo.service.CustomerService;
import com.example.demo.service.TransactionService;


@Service
public class AccountService {

    // Our internal structure to store accounts
    private static HashMap<Integer, Account> accountStore = new HashMap<Integer, Account>();
    private CustomerService customerService;
    private TransactionService transactionService;

    @Autowired
    public AccountService(@Lazy CustomerService customerService, @Lazy TransactionService transactionService) {
        this.customerService = customerService;
        this.transactionService = transactionService;
    }

    public Account createOrUpdate(AccountQuery newAccount) {
        Customer customer = customerService.getCustomer(newAccount.customerID);
        Account account = new Account(customer);
        accountStore.put(account.getId(), account);
        if (newAccount.initialCredit.compareTo(new BigDecimal(0)) != 0) {
            transactionService.createOrUpdate(new TransactionQuery(account.getId(), newAccount.initialCredit));
            return getAccount(account.getId());
        }
        return account;
    }
    public Account getAccount(int id) {
        return accountStore.get(id);
    }
    public void delete(int id) {
        if(accountStore.containsKey(id))
            accountStore.remove(id);
    }
    public List<Account> getAllAccounts() {
        return accountStore.values().stream().toList();
    }
}
