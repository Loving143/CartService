package com.cart.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cart.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long>{

}
