package com.visa.shopapp.dao;

import com.visa.shopapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
    @Query(value = "select * from orders where DATE(order_date) = :od", nativeQuery = true)
    List<Order> getOrder(@Param("od") String date);

    @Query(value = "SELECT * FROM customers c, orders o, " +
            "line_items i, products p WHERE " +
          " c.email = :cust"+
          " AND o.customer_fk = c.email" +
        " AND o.oid = i.order_fk " +
            "  AND i.product_fk = p.id",
    nativeQuery = true)
    List<Object[]> getReport(@Param("cust") String email);
}
