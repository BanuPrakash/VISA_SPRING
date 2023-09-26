package com.visa;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/hello")
public class HelloController {

    @GetMapping
    public String sayHello() {
        return "Welcome to Spring Security!!!";
    }

    @GetMapping("/admin")
    public String sayHelloAdmin() {
        return "Welcome to Admin Page in Spring Security!!!";
    }
}
