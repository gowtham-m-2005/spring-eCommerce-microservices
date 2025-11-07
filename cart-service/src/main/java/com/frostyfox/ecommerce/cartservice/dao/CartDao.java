package com.frostyfox.ecommerce.cartservice.dao;

import com.frostyfox.ecommerce.cartservice.models.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartDao extends JpaRepository<CartItem, Long> {
}
