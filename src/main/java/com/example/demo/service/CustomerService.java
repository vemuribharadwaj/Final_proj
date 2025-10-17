package com.example.demo.service;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Customer;

public interface CustomerService
{
    Customer enrollCustomer(Customer customer);

    //TODO below

    void updatePhoneNumber(Long id, String phoneNumber) throws CustomerNotFoundException;
}
