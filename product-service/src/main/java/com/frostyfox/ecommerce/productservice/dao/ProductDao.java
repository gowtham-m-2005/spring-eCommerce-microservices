package com.frostyfox.ecommerce.productservice.dao;

import com.frostyfox.ecommerce.productservice.dto.ProductResponse;
import com.frostyfox.ecommerce.productservice.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    @Query("SELECT p from Product p where p.active=true AND p.stockQuantity > 0 AND LOWER(p.name) LIKE lower(concat( '%', :keyword, '%')) ")
    List<Product> searchProducts(@Param("keyword") String keyword);

    Optional<Product> findByIdAndActiveTrue(Long id);
}
