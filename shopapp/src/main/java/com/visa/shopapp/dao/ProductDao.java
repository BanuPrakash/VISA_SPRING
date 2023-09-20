package com.visa.shopapp.dao;

import com.visa.shopapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDao extends JpaRepository<Product, Integer> {
}
