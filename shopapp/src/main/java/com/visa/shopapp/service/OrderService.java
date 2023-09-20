package com.visa.shopapp.service;

import com.visa.shopapp.dao.ProductDao;
import com.visa.shopapp.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductDao productDao;
//    public OrderService(ProductDao productDao) {
//        this.productDao = productDao;
//    }

    public List<Product> byRange(double low, double high) {
        return  productDao.getByRange(low, high);
    }

    @Transactional
    public Product updateProduct(int id, double price) {
        productDao.updateProductPrice(id, price);
        return getProductById(id);
    }


    public List<Product> getProducts() {
        return productDao.findAll();
    }
    public Product addProduct(Product p) {
        return productDao.save(p);
    }
    public Product getProductById(int id) {
        Optional<Product> opt = productDao.findById(id);
        if(opt.isPresent()) {
            return opt.get();
        } else {
            return null;
        }
    }
}
