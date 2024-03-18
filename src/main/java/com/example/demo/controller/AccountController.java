package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Account;
import com.example.demo.entity.AccountResponse;
import com.example.demo.entity.AccountQuery;
import com.example.demo.service.AccountService;
import com.example.demo.service.TransactionService;


@RestController("/api/account")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("/api/account/{id}")
    public AccountResponse getAccount(@PathVariable int id){
        return new AccountResponse(
				accountService.getAccount(id),
				transactionService.getTransactionsForAccount(id)
		);
    }

    @GetMapping("/api/accounts")
    public List<?> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @DeleteMapping("/api/account/{id}")
    public void deleteAccount(@PathVariable int id){
        accountService.delete(id);
    }

    @PostMapping("/api/account")
    public Account createAccount(@RequestBody AccountQuery account){
        return accountService.createOrUpdate(account);
    }
}
