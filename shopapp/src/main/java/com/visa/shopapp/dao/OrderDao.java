package com.visa.shopapp.dao;

import com.visa.shopapp.dto.ReportDTO;
import com.visa.shopapp.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Integer> {
    @Query(value = "select * from orders where DATE(order_date) = :od", nativeQuery = true)
    List<Order> getOrder(@Param("od") String date);

//    @Query(value = "SELECT " +
//            "c.first_name, c.email, o.order_date, " +
//            "o.total, p.name, p.price, i.qty, i.amount" +
//            " FROM customers c, orders o, " +
//            "line_items i, products p WHERE " +
//          " c.email = :cust"+
//          " AND o.customer_fk = c.email" +
//        " AND o.oid = i.order_fk " +
//            "  AND i.product_fk = p.id",
//    nativeQuery = true)
//    List<Object[]> getReport(@Param("cust") String email);

    @Query("SELECT " +
            "new com.visa.shopapp.dto.ReportDTO" +
            "(c.firstName, c.email, o.orderDate, " +
            "o.total)" +
            " FROM Order o inner  join o.customer c " +
            " WHERE c.email = :cust")
    List<ReportDTO> getReport(@Param("cust") String email);

}
