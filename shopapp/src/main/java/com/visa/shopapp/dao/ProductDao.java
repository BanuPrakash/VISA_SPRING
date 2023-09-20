package com.visa.shopapp.dao;

import com.visa.shopapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductDao extends JpaRepository<Product, Integer> {
    @Query("from Product where price >= :l and price <= :h")
    //@Query(value = "select * from  products where price >= :l and price <= :h", nativeQuery = true)
    List<Product> getByRange(@Param("l") double low, @Param("h") double high);

    List<Product> findByQuantity(int quantity);

    @Modifying
    @Query("update Product set price = :pr where id = :id")
    void updateProductPrice(@Param("id")  int id, @Param("pr")  double price);
}
