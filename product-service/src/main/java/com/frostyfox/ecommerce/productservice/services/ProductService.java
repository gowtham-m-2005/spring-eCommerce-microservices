package com.frostyfox.ecommerce.productservice.services;

import com.frostyfox.ecommerce.productservice.dao.ProductDao;
import com.frostyfox.ecommerce.productservice.dto.ProductRequest;
import com.frostyfox.ecommerce.productservice.dto.ProductResponse;
import com.frostyfox.ecommerce.productservice.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;

    public ResponseEntity<ProductResponse> createProduct(ProductRequest productRequest) {
        Product product = new Product();
        updateProductFromProductRequest(product, productRequest);

        Product savedProduct = productDao.save(product);

        ProductResponse productResponse = mapToProductResponse(savedProduct);

        return ResponseEntity.ok(productResponse);
    }

    private ProductResponse mapToProductResponse(Product savedProduct) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setId(savedProduct.getId());
        productResponse.setName(savedProduct.getName());
        productResponse.setPrice(savedProduct.getPrice());
        productResponse.setCategory(savedProduct.getCategory());
        productResponse.setDescription(savedProduct.getDescription());
        productResponse.setStockQuantity(savedProduct.getStockQuantity());
        productResponse.setImageUrl(savedProduct.getImageUrl());
        productResponse.setActive(savedProduct.getActive());
        return productResponse;
    }

    private void updateProductFromProductRequest(Product product, ProductRequest productRequest) {
        product.setName(productRequest.getName());
        product.setPrice(productRequest.getPrice());
        product.setCategory(productRequest.getCategory());
        product.setDescription(productRequest.getDescription());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setImageUrl(productRequest.getImageUrl());
    }

    public Optional<ProductResponse> updateProduct(Long id, ProductRequest productRequest) {
        return productDao.findById(id)
                .map(existingProduct -> {
                    updateProductFromProductRequest(existingProduct, productRequest);

                    Product product = productDao.save(existingProduct);

                    return mapToProductResponse(product);
                });
    }

    public List<ProductResponse> getAllProducts() {
        return productDao.findByActiveTrue().stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public boolean deleteProduct(Long id) {
        return productDao.findById(id)
                .map(product ->  {
                    product.setActive(false);
                    productDao.save(product);
                    return true;
                }).orElse(false);
    }

    public List<ProductResponse> searchProducts(String keyword) {
        return productDao.searchProducts(keyword).stream()
                .map(this::mapToProductResponse)
                .collect(Collectors.toList());
    }

    public ResponseEntity<ProductResponse> getProductById(Long id) {
        return productDao.findByIdAndActiveTrue(id)
                .map(product -> ResponseEntity.ok(mapToProductResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }
}
