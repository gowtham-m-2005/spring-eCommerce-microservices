package com.frostyfox.ecommerce.cartservice.dto;

import com.frostyfox.ecommerce.cartservice.models.UserRole;
import lombok.Data;

@Data
public class UserResponse {
    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private UserRole userRole;
    private AddressDto addressDto;
}
