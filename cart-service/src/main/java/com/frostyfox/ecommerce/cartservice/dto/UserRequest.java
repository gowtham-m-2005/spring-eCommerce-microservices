package com.frostyfox.ecommerce.cartservice.dto;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private AddressDto addressDto;
}
