package com.visa.shopapp.api;

import com.visa.shopapp.aop.ValidateInput;
import com.visa.shopapp.entity.Product;
import com.visa.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/products")
@RequiredArgsConstructor
public class ProductController {
    private final OrderService orderService;

    // http://localhost:8080/api/products
    // http://localhost:8080/api/products?low=500&high=50000
    @GetMapping()
    public List<Product> getProducts(@RequestParam(name="low", defaultValue = "0.0") double low,
                                     @RequestParam(name="high", defaultValue = "0.0") double high
                                     ) {
        if(low == 0.0 && high == 0.0) {
            return orderService.getProducts();
        } else {
            return orderService.byRange(low,high);
        }
    }
    // http://localhost:8080/api/products/4
    @GetMapping("/{id}")
    @ValidateInput(min = 3)
    public Product getProductById(@PathVariable("id") int id) {
        return orderService.getProductById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product addProduct(@RequestBody Product p) {
        return orderService.addProduct(p);
    }

    // http://localhost:8080/api/products/3
    // {"price": 45344.22} payload
    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable("id") int id, @RequestBody Product p) {
        orderService.updateProduct(id, p.getPrice());
        return orderService.getProductById(id);
    }
}
