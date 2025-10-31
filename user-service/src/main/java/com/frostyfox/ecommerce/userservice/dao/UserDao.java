package com.frostyfox.ecommerce.userservice.dao;

import com.frostyfox.ecommerce.userservice.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Integer> {
}
