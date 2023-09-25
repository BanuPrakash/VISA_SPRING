package com.visa.shopapp.api;

import com.visa.shopapp.entity.Order;
import com.visa.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody Order o) {
        orderService.placeOrder(o);
        return  "order placed!!!";
    }

    // http://localhost:8080/api/orders
    // http://localhost:8080/api/orders?order-date=20-09-2023
    @GetMapping
    public List<Order> getOrders(@RequestParam(name="order-date", required = false)
                                    String orderDate) {
        if(orderDate != null) {
          //  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
           return orderService.byDate(orderDate);
        } else {
            return orderService.getOrders();
        }
    }


}
