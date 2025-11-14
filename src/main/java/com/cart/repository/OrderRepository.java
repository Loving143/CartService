package com.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cart.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{

}
