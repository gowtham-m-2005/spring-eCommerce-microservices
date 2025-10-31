package com.frostyfox.ecommerce.userservice.controller;

import com.frostyfox.ecommerce.userservice.Dto.UserRequest;
import com.frostyfox.ecommerce.userservice.Dto.UserResponse;
import com.frostyfox.ecommerce.userservice.models.User;
import com.frostyfox.ecommerce.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequiredArgsConstructor
@RequestMapping("api/users")
public class UserController {

    @Autowired
    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers(){
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Integer id){
        return userService.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<String> addUser(@RequestBody UserRequest userRequest){
        return userService.addUser(userRequest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable Integer id, @RequestBody UserRequest userRequest){
        return userService.updateUser(id, userRequest);
    }
}
