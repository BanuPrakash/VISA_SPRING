package com.visa.springmongo.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.visa.springmongo.dao.ProductDao;
import com.visa.springmongo.document.Product;

@RestController
@RequestMapping("api/products")
public class ProductController {
	@Autowired
	private ProductDao productDao;
	
	@GetMapping()
	public List<Product> getProducts() {
		return productDao.findAll();
	}
	
	@PostMapping
	public Product addProduct(@RequestBody Product p) {
		return productDao.save(p);
	}
}
