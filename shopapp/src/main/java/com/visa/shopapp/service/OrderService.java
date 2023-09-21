package com.visa.shopapp.service;

import com.visa.shopapp.dao.CustomerDao;
import com.visa.shopapp.dao.OrderDao;
import com.visa.shopapp.dao.ProductDao;
import com.visa.shopapp.entity.LineItem;
import com.visa.shopapp.entity.Order;
import com.visa.shopapp.entity.Product;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final ProductDao productDao;
    private final OrderDao orderDao;
    private final CustomerDao customerDao;
//    public OrderService(ProductDao productDao) {
//        this.productDao = productDao;
//    }

    /*
        {
            "customer": {"email": "harry@visa.com"},
            "items": [
                {"product": {id: 3}, "qty": 2},
                {"product": {id: 1}, "qty": 3}
            ]
        }

     */
    @Transactional
    public  void placeOrder(Order order) {
        double total = 0.0;
        List<LineItem> items = order.getItems();
        for(LineItem item : items) {
            Product p = productDao.findById(item.getProduct().getId()).get();
            if(p.getQuantity() < item.getQty()) {
                throw new IllegalArgumentException("No Sufficient Product "
                        + p.getName() + " in stock");
            }
            item.setAmount(p.getPrice() * item.getQty());

            p.setQuantity(p.getQuantity() - item.getQty()); // Dirty Checking
            total += item.getAmount();
        }
        order.setTotal(total);
        orderDao.save(order); // cascade takes care of persisting LineItems
    }

    public List<Order> getOrders() {
        return orderDao.findAll();
    }
    public List<Order> byDate(String orderDate) {
        return orderDao.getOrder(orderDate);
    }
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
