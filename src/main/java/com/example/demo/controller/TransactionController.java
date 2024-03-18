package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Transaction;
import com.example.demo.entity.NewTransaction;
import com.example.demo.service.TransactionService;


@RestController("/api/transaction")
public class TransactionController {

    @Autowired
    private TransactionService transactionService; // inject our service

    @GetMapping("/api/transaction/{id}")
    public Transaction getTransaction(@PathVariable int id){
        return transactionService.getTransaction(id);
    }

    @GetMapping("/api/transactions")
    public List<?> getAllTransactions(){
        return transactionService.getAllTransactions();
    }

    @PostMapping("/api/transaction")
    public Transaction createTransaction(@RequestBody NewTransaction transaction){
        return transactionService.createOrUpdate(transaction);
    }
}
