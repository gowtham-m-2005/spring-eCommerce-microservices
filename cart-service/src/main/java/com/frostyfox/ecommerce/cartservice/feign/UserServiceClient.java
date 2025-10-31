package com.frostyfox.ecommerce.cartservice.feign;

import com.frostyfox.ecommerce.cartservice.dto.UserRequest;
import com.frostyfox.ecommerce.cartservice.dto.UserResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient("USER-SERVICE")
public interface UserServiceClient {
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers();

    @GetMapping("/api/users/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id);

    @PostMapping("/api/cart")
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest);

    @PutMapping("/api/cart/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest);
}
