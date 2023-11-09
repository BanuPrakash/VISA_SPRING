package com.visa.springmongo.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.visa.springmongo.document.Product;

public interface ProductDao extends MongoRepository<Product, Integer> {

}
