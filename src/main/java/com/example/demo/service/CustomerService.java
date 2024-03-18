package com.example.demo.service;

import java.util.List;
import java.util.HashMap;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Customer;


@Service
public class CustomerService {

    // Our internal structure to store customers
    private static HashMap<Integer, Customer> customerStore = new HashMap<Integer, Customer>();
    private static int idCounter = 0;

    public Customer createOrUpdate(Customer customer) {
        int id = idCounter++;
        customer.setId(id);
        customerStore.put(id, customer);
        return customer;
    }
    public Customer getCustomer(int id) {
        return customerStore.get(id);
    }
    public void delete(int id) {
        if(customerStore.containsKey(id))
            customerStore.remove(id);
    }
    public List<Customer> getAllCustomers() {
        return customerStore.values().stream().toList();
    }

    public void clear() {
        customerStore.clear();
        idCounter = 0;
    }
}
