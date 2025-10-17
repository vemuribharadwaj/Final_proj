package com.example.demo.controller;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.model.Customer;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/customer")
public class CustomerController
{
    @Autowired
    private CustomerService customerService;

    @PostMapping("/register")
    public ResponseEntity registerCustomer(@RequestBody Customer customer)
    {
          Customer saved = customerService.enrollCustomer(customer);
            return ResponseEntity.ok(saved);
    }

    //TODO : update phone number

    @PutMapping("/{id}/updatePhoneNumber")
    public ResponseEntity<String> updatingPhoneNumber(@PathVariable Long id, @RequestBody Map<String, String> newNumber) throws CustomerNotFoundException
    {
            String newPhoneNumber = newNumber.get("phoneNumber");
            customerService.updatePhoneNumber(id,newPhoneNumber);
            return ResponseEntity.ok("Phone number updated successfully.");

    }
}
