package com.visa.api;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	
	@GetMapping("/")
	public String sayHello() {
		return "Spring Security Demo!!!";
	}
	
	@GetMapping("/products") 
	public String getProducts() {
		return "products";
	}
	
	@GetMapping("/orders")
	public String getOrders() {
		return "orders";
	}
	
	
}
