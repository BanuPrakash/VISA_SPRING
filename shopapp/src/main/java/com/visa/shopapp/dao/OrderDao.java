package com.visa.shopapp.dao;

import com.visa.shopapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
    @Query(value = "select * from orders where DATE(order_date) = :od", nativeQuery = true)
    List<Order> getOrder(@Param("od") String date);
}
