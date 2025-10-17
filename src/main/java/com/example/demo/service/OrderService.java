package com.example.demo.service;

import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.model.Address;
import com.example.demo.model.Order;
import com.example.demo.model.dto.OrderRequestdto;

public interface OrderService
{
    Order createOrder(OrderRequestdto request) throws CustomerNotFoundException;

    boolean cancelOrder(Long id)throws OrderNotFoundException;

    void updateShippingAddress(Long id, Address newAddress)throws OrderNotFoundException;



}
