package com.frostyfox.ecommerce.userservice.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "addresses_table")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String street;
    private String city;
    private String state;
    private String zip;
    private String country;
}
