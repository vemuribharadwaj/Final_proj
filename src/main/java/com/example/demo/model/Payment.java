package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Entity
@Getter
@Setter
public class Payment
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mode;

    private String cardNumber;

    private String cvv;

    private String expirationDate;

    private String confirmationCode;

    //payment to order is a one2one


//    @OneToOne(mappedBy = "payment")
//    private Order order;

}
