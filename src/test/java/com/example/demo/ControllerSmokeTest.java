package com.example.demo;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.demo.controller.AccountController;
import com.example.demo.controller.CustomerController;
import com.example.demo.controller.TransactionController;


@SpringBootTest
class ControllerSmokeTest {

    @Autowired
    private AccountController accountController;
    @Autowired
    private CustomerController customerController;
    @Autowired
    private TransactionController transactionController;

    @Test
    void contextLoadsAccount() throws Exception {
        assertThat(accountController).isNotNull();
    }
    @Test
    void contextLoadsCustomer() throws Exception {
        assertThat(customerController).isNotNull();
    }
    @Test
    void contextLoadsTransaction() throws Exception {
        assertThat(transactionController).isNotNull();
    }
}
