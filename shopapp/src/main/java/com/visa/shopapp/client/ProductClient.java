package com.visa.shopapp.client;


import java.util.List;

import com.visa.shopapp.entity.Product;
import com.visa.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductClient implements CommandLineRunner {
    private OrderService service;
    // code gets executed automatically once Spring container is created
    @Override
    public void run(String... args) throws Exception {
		addProduct();
        listProducts();
    }
    
    private void addProduct() {
        Product p = service.addProduct(new Product(0, "iPhone 14", 89000.00, 100));
        service.addProduct(p);
        System.out.println("Product added " + p);
    }


    private void listProducts() {
        List<Product> products = service.getProducts();
        for(Product p : products) {
            System.out.println(p); // toString()
        }
    }

}