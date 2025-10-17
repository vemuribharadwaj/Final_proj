package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="orders")
@Getter
@Setter
public class Order
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long id;

    private LocalDateTime createdDate;

    private Double subTotal;

    private Double tax;

    private Double shippingCharges;

    private Double total;

    private LocalDateTime PromisedDeliveryDate;

    //order 2 orderitem is one 2 many

    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL)
    private Set<OrderItem> items = new HashSet<>();

    @Enumerated(value=EnumType.STRING)
    private orderStatus status;

    //order 2 address is a one2one here we attaching FK on Order side

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="address_id",unique=true)
    private Address address;

    //customer to order its a one2many

    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

//    @OneToOne(mappedBy = "order")
//    private Payment payment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="payment_id", unique = true)
    private Payment payment;



}
