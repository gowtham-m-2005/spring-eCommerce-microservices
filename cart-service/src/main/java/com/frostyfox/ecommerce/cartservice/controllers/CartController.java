package com.frostyfox.ecommerce.cartservice.controllers;

import com.frostyfox.ecommerce.cartservice.dto.CartItemRequest;
import com.frostyfox.ecommerce.cartservice.services.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<String> addToCart(@RequestHeader("X-User-Id") Long userId, @RequestBody CartItemRequest cartItemRequest){
        if(!cartService.addToCart(userId, cartItemRequest)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User not found or Product out of stock or Product not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Successfully added to cart");
    }
}
