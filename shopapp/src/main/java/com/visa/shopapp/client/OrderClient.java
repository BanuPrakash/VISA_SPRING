package com.visa.shopapp.client;

import com.visa.shopapp.entity.Customer;
import com.visa.shopapp.entity.LineItem;
import com.visa.shopapp.entity.Product;
import com.visa.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

//@Component
@RequiredArgsConstructor
//@Order(2)
public class OrderClient implements CommandLineRunner {
    private final OrderService orderService;
    @Override
    public void run(String... args) throws Exception {
        //getOrders();
       // newOrder();
        byDate();
    }

    private void getOrders() {
        List<com.visa.shopapp.entity.Order> orders = orderService.getOrders();
        for(com.visa.shopapp.entity.Order order: orders) {
            System.out.println("Customer :" + order.getCustomer().getFirstName());
            System.out.println("Date: " + order.getOrderDate());
            System.out.println("Total : " + order.getTotal());

            System.out.println("Items:");
            for(LineItem item : order.getItems()) {
                System.out.println("Product " + item.getProduct().getName());
                System.out.println("Qty: " + item.getQty());
                System.out.println("Amount : " + item.getAmount());
            }
            System.out.println("**********");
        }

    }

    private void byDate() {
        try {
            String date = "2023-9-20";
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            List<com.visa.shopapp.entity.Order> orders = orderService.byDate(date);
            for(com.visa.shopapp.entity.Order order: orders) {
                System.out.println(order.getCustomer().getFirstName());
                System.out.println(order.getOrderDate() +", " + order.getTotal());
                List<LineItem> items = order.getItems();
                for(LineItem item: items) {
                    System.out.println(item.getProduct().getName() + ", " + item.getQty() + "," + item.getAmount());
                }
                System.out.println("Total: " + order.getTotal());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void newOrder() {
        com.visa.shopapp.entity.Order order =
                new com.visa.shopapp.entity.Order();
        Customer customer = Customer.builder().email("harry@visa.com").build();
        order.setCustomer(customer);

        List<LineItem> items = new ArrayList<>();
        LineItem i1 = new LineItem();
        Product p1 = new Product();
        p1.setId(1);
        i1.setProduct(p1);
        i1.setQty(3);
        items.add(i1);

//        LineItem i2 = new LineItem();
//        Product p2 = new Product();
//        p2.setId(5);
//        i2.setProduct(p2);
//        i2.setQty(2);
//        items.add(i2);

        order.setItems(items);
        try {
            String date = "2023-9-20";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            order.setOrderDate(sdf.parse(date));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        orderService.placeOrder(order);
    }
}
