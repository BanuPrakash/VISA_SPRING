package com.visa.shopapp.dao;

import com.visa.shopapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<Order, Integer> {
}
