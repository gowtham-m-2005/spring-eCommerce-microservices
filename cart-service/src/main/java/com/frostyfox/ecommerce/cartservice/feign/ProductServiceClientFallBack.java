package com.frostyfox.ecommerce.cartservice.feign;

import com.frostyfox.ecommerce.cartservice.dto.ProductRequest;
import com.frostyfox.ecommerce.cartservice.dto.ProductResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Component
public class ProductServiceClientFallBack implements ProductServiceClient {

    @Override
    public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest) {
        return null;
    }

    @Override
    public ResponseEntity<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        return null;
    }

    @Override
    public ResponseEntity<String> deleteProduct(Long id) {
        return null;
    }

    @Override
    public ResponseEntity<List<ProductResponse>> searchProducts(String keyword) {
        return null;
    }

    @Override
    public ResponseEntity<ProductResponse> getProductbyId(@PathVariable Long id){
        return ResponseEntity.notFound().build();
    }
}
