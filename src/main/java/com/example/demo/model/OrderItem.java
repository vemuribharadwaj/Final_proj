package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class OrderItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer orderedQuantity;

    //order 2 orderItem is a one2many as one order can consist multiple order items

    @ManyToOne
    @JoinColumn(name="order_id")
    @JsonIgnore
    private Order order;


    //orderitem to product is a many2one

    @ManyToOne
    @JoinColumn(name="product_id")
    private Product product;
}
