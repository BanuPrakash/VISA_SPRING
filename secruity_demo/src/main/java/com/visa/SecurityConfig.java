package com.visa;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public InMemoryUserDetailsManager users() {
        return new InMemoryUserDetailsManager(
                User.withUsername("banu")
                        .password("{noop}secret")
                        .authorities("ROLE_READ").build(),
                User.withUsername("smitha")
                        .password("{noop}secret")
                        .authorities("ROLE_ADMIN", "ROLE_READ")
                        .build());
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/resources/**", "/login", "/about").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasRole('READ')"))
//                        // .requestMatchers("/db/**").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("DBA")))
//                        .requestMatchers("/").permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
        return http.authorizeRequests()
                 .antMatchers("/api/**").hasAnyRole("READ", "ADMIN")
                 .antMatchers("/admin/**").hasRole("ADMIN")
                 .antMatchers("/").permitAll()
                 .anyRequest().authenticated()
                 .and().formLogin()
                 .and().build();
//        http.authorizeRequests().httpBasic(withDefaults());
    }
}
