package com.visa.shopapp.api;

import com.visa.shopapp.entity.Product;
import com.visa.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final OrderService orderService;

    // http://localhost:8080/api/products
    @GetMapping()
    public List<Product> getProducts() {
        return orderService.getProducts();
    }
    // http://localhost:8080/api/products/4
    @GetMapping("/{id}")
    public Product getProductById(@PathVariable("id") int id) {
        return orderService.getProductById(id);
    }
}
