package com.frostyfox.ecommerce.cartservice.dto;

import lombok.Data;

@Data
public class CartItemRequest {
    private long productId;
    private long quantity;
}
