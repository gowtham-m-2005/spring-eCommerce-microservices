package com.frostyfox.ecommerce.cartservice.services;


import com.frostyfox.ecommerce.cartservice.dto.CartItemRequest;
import com.frostyfox.ecommerce.cartservice.dto.ProductResponse;
import com.frostyfox.ecommerce.cartservice.dto.UserResponse;
import com.frostyfox.ecommerce.cartservice.feign.ProductServiceClient;
import com.frostyfox.ecommerce.cartservice.feign.UserServiceClient;
import com.frostyfox.ecommerce.cartservice.models.User;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CartService {

    private final ProductServiceClient productServiceClient;
    private final UserServiceClient userServiceClient;

    public boolean addToCart(Long userId, CartItemRequest cartItemRequest) {
        ProductResponse productResponse = null;
        UserResponse user = null;

        try {
            productResponse = productServiceClient.getProductbyId(cartItemRequest.getProductId()).getBody();
        }catch (FeignException.NotFound e) {
            return false; //product not found
        }

        if (productResponse == null || productResponse.getStockQuantity() < cartItemRequest.getQuantity()) {
            return false;
        }

        try {
            user = userServiceClient.getUserById(Math.toIntExact(userId)).getBody();
        }catch(FeignException.NotFound e){
            return false; //user not found
        }
        return user != null ? true : false;
    }
}
