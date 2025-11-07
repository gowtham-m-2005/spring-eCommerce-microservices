package com.frostyfox.ecommerce.userservice.Dto;

import lombok.Data;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String phoneNumber;
    private AddressDto addressDto;
}
