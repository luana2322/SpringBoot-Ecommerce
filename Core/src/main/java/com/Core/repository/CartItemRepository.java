package com.Core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Core.model.CartItem;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

}
