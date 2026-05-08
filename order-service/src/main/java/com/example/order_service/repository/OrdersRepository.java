package com.example.order_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.order_service.entity.Orders;
@Repository
public interface OrdersRepository extends JpaRepository<Orders, Long>{

}
