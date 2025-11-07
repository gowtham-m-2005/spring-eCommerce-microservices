package com.frostyfox.ecommerce.cartservice.feign;

import com.frostyfox.ecommerce.cartservice.dto.ProductRequest;
import com.frostyfox.ecommerce.cartservice.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "PRODUCT-SERVICE", fallback = ProductServiceClientFallBack.class)
public interface ProductServiceClient {
    @PostMapping("/api/products")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest);

    @PutMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable Long id, @RequestBody ProductRequest productRequest);

    @GetMapping("/api/products")
    public ResponseEntity<List<ProductResponse>> getAllProducts();

    @DeleteMapping("/api/products/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id);

    @GetMapping("/api/products/search")
    public ResponseEntity<List<ProductResponse>> searchProducts(@RequestParam String keyword);

    @GetMapping("/api/products/{id}")
    public ResponseEntity<ProductResponse> getProductbyId(@PathVariable Long id);
}
