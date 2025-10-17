package com.example.demo.repository;

import com.example.demo.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

//all DML stuff happens here in repository layer
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>
{

}
