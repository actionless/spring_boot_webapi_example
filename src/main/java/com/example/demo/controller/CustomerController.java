package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Customer;
import com.example.demo.service.CustomerService;


@RestController("/api")
public class CustomerController {

    @Autowired
    private CustomerService customerService; // inject our service

    @GetMapping("/api/customer/{id}")
    public Customer getCustomer(@PathVariable int id){
        return customerService.getCustomer(id);
    }

    @GetMapping("/api/customers")
    public List<?> getAllCustomers(){
        return customerService.getAllCustomers();
    }

    @DeleteMapping("/api/customer/{id}")
    public void deleteCustomer(@PathVariable int id){
        customerService.delete(id);
    }

    @PostMapping("/api/customer")
    public Customer createCustomer(@RequestBody Customer customer){
        return customerService.createOrUpdate(customer);
    }
}
