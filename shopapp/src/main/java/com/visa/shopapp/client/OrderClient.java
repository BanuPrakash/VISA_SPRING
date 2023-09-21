package com.visa.shopapp.client;

import com.visa.shopapp.entity.Customer;
import com.visa.shopapp.entity.LineItem;
import com.visa.shopapp.entity.Product;
import com.visa.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Order(2)
public class OrderClient implements CommandLineRunner {
    private final OrderService orderService;
    @Override
    public void run(String... args) throws Exception {
        newOrder();
    }

    private void newOrder() {
        com.visa.shopapp.entity.Order order =
                new com.visa.shopapp.entity.Order();
        Customer customer = Customer.builder().email("harry@visa.com").build();
        order.setCustomer(customer);

        List<LineItem> items = new ArrayList<>();
        LineItem i1 = new LineItem();
        Product p1 = new Product();
        p1.setId(4);
        i1.setProduct(p1);
        i1.setQty(2);
        items.add(i1);

        LineItem i2 = new LineItem();
        Product p2 = new Product();
        p2.setId(2);
        i2.setProduct(p2);
        i2.setQty(1);
        items.add(i2);

        order.setItems(items);

        orderService.placeOrder(order);
    }
}
