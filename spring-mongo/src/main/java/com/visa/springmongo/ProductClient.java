package com.visa.springmongo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.visa.springmongo.dao.ProductDao;
import com.visa.springmongo.document.Product;

@Component
public class ProductClient implements CommandLineRunner {
	@Autowired
	ProductDao productDao;
	@Override
	public void run(String... args) throws Exception {
		List<Product> products =  productDao.findAll();
		System.out.println(products);
	}

}
