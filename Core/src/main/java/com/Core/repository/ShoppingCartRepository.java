package com.Core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Core.model.ShoppingCart;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

}
