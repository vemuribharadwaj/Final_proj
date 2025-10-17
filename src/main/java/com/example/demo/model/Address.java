package com.example.demo.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Address
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //order to address can be a one2one

    @Column(nullable = false)
    private String lineOne;

    private String lineTwo;
    private String city;
    private String state;

    @Column(nullable = false)
    private String zipCode;
}
