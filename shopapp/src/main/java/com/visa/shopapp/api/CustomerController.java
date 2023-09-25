package com.visa.shopapp.api;


import com.visa.shopapp.entity.Customer;
import com.visa.shopapp.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {
    private final OrderService orderService;

    @GetMapping("/{email}")
    public Customer getCustomer(@PathVariable("email") String email) {
        System.out.println(email);
        return orderService.getByEmail(email);
    }
}
