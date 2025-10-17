package com.example.demo.service.implementations;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.InvalidInputException;
import com.example.demo.model.Customer;
import com.example.demo.repository.CustomerRepository;
import com.example.demo.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class CustomerServiceImpl implements CustomerService
{
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Customer enrollCustomer(Customer customer)
    {
        String emailInput = customer.getEmail();
        //let's validate email,phone number
        if(!emailInput.contains("@"))
        {
            throw new IllegalArgumentException("Invalid email address");
        }
        if(customer.getPhoneNumber().length() != 10)
        {
            throw new IllegalArgumentException("Invalid phone number");
        }
        Customer saved = customerRepository.save(customer);
        return saved;
    }


    //TODO implement service to update customers phone number

    @Override
    public void updatePhoneNumber(Long id, String phoneNumber) throws CustomerNotFoundException
    {
        Optional<Customer> fetchedCustomer = customerRepository.findById(id);
        if(fetchedCustomer.isPresent())
        {
            Customer existingCustomer = fetchedCustomer.get();

            //let's validate phone num before we update into DB
            validatePhoneNumber(phoneNumber);
            existingCustomer.setPhoneNumber(phoneNumber);

            //saving into DB
            customerRepository.save(existingCustomer);
        }
        else
        {
            throw new CustomerNotFoundException("Customer with id " + id + " not found");
        }
    }


    private void validatePhoneNumber(String phoneNumber) throws InvalidInputException {
        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new InvalidInputException("Phone number must be exactly 10 digits");
        }

    }
}
