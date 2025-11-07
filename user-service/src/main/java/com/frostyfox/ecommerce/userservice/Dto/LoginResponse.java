package com.frostyfox.ecommerce.userservice.Dto;

import com.frostyfox.ecommerce.userservice.models.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole userRole;
}
