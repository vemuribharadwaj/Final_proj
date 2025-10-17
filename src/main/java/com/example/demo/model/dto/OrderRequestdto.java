package com.example.demo.model.dto;
import com.example.demo.model.Address;
import com.example.demo.model.Payment;
import com.example.demo.model.dto.OrderItemDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrderRequestdto {
        private Long customerId;
        private Address shippingAddress;
        private Payment payment;
        private List<OrderItemDto> orderItems;
}
