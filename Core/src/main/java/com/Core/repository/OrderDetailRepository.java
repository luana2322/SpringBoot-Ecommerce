package com.Core.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Core.model.OrderDetail;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long>{

}
