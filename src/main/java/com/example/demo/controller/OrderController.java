package com.example.demo.controller;


import com.example.demo.exception.CustomerNotFoundException;
import com.example.demo.exception.OrderNotFoundException;
import com.example.demo.model.Order;
import com.example.demo.model.dto.OrderRequestdto;
import com.example.demo.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order")
public class OrderController
{
    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public Order createOrder(@RequestBody OrderRequestdto requestdto) throws CustomerNotFoundException {

        Order created = orderService.createOrder(requestdto);
        return created;

    }

    //Todo: to cancel an order
    @PostMapping("/cancel/{orderId}")
    public String cancelOrder(@PathVariable Long orderId) throws OrderNotFoundException {
        boolean isCanceled = orderService.cancelOrder(orderId);
        if (isCanceled) {
            return "Order canceled successfully";
        } else {
            return "Order could not be canceled";
        }


    }
}
