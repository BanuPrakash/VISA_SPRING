package com.visa.shopapp.client;


import java.util.List;

import com.visa.shopapp.entity.Product;
import com.visa.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Order(1)
public class ProductClient implements CommandLineRunner {
    private final OrderService service;
    // code gets executed automatically once Spring container is created
    @Override
    public void run(String... args) throws Exception {
		//addProduct();
        //listProducts();
//        getById();
//        listByRange();
       // updateProduct();
    }

    private void updateProduct() {
        service.updateProduct(1, 9999.00);
    }


    private void listByRange() {
        List<Product> products = service.byRange(500, 50_000);
        for(Product p : products) {
            System.out.println(p); // toString()
        }
    }

    private void getById() {
        Product p = service.getProductById(3);
        System.out.println(p);
    }

    private void addProduct() {
        Product p = service.addProduct(new Product(0, "Reynold Marker", 200.00, 100));
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