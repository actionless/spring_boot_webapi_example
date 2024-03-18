package com.example.demo.service;

import java.util.List;
import java.util.HashMap;
import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Account;
import com.example.demo.entity.Customer;
import com.example.demo.entity.NewAccount;

import com.example.demo.service.CustomerService;


@Service
public class AccountService {

    // Our internal structure to store accounts
    private static HashMap<Integer, Account> accountStore = new HashMap<Integer, Account>();
    private CustomerService customerService = new CustomerService();

    public Account createOrUpdate(NewAccount newAccount) {
        Customer customer = customerService.getCustomer(newAccount.customerID);
        Account account = new Account(customer, newAccount.initialCredit);
        return accountStore.put(account.getId(), account);
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
