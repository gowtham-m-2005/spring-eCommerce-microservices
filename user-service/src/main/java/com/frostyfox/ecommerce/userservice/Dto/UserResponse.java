package com.frostyfox.ecommerce.userservice.Dto;

import com.frostyfox.ecommerce.userservice.models.UserRole;
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
