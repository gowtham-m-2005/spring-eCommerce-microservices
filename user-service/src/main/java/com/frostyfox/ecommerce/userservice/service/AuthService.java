package com.frostyfox.ecommerce.userservice.service;

import com.frostyfox.ecommerce.userservice.Dto.LoginRequest;
import com.frostyfox.ecommerce.userservice.Dto.LoginResponse;
import com.frostyfox.ecommerce.userservice.Dto.RegisterRequest;
import com.frostyfox.ecommerce.userservice.dao.UserDao;
import com.frostyfox.ecommerce.userservice.models.Address;
import com.frostyfox.ecommerce.userservice.models.User;
import com.frostyfox.ecommerce.userservice.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserDao userDao;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public LoginResponse register(RegisterRequest request) {
        // Check if user already exists
        if (userDao.existsByEmail(request.getEmail())) {
            throw new RuntimeException("User with email " + request.getEmail() + " already exists");
        }

        // Create new user
        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setPhoneNumber(request.getPhoneNumber());

        // Set address if provided
        if (request.getAddressDto() != null) {
            Address address = new Address();
            address.setStreet(request.getAddressDto().getStreet());
            address.setCity(request.getAddressDto().getCity());
            address.setState(request.getAddressDto().getState());
            address.setZip(request.getAddressDto().getZip());
            address.setCountry(request.getAddressDto().getCountry());
            user.setAddress(address);
        }

        User savedUser = userDao.save(user);

        // Generate JWT token
        String token = jwtUtil.generateToken(savedUser);

        return LoginResponse.builder()
                .token(token)
                .email(savedUser.getEmail())
                .firstName(savedUser.getFirstName())
                .lastName(savedUser.getLastName())
                .userRole(savedUser.getUserRole())
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        // Authenticate user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Get user details
        User user = userDao.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Generate JWT token
        String token = jwtUtil.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userRole(user.getUserRole())
                .build();
    }
}
