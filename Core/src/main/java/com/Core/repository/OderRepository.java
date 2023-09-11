package com.Core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Core.model.Order;

public interface OderRepository extends JpaRepository<Order, Long>{
	
}
