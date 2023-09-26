package com.visa;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final DataSource dataSource;

    @Autowired
    public void configureJdbcAuth(AuthenticationManagerBuilder authBuilder) throws Exception {
        authBuilder.jdbcAuthentication().dataSource(dataSource);
    }

    @Bean
    JdbcUserDetailsManager jdbcUserDetailsManager() {
        return new JdbcUserDetailsManager(dataSource);
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public InMemoryUserDetailsManager users() {
//        return new InMemoryUserDetailsManager(
//                User.withUsername("banu")
//                        .password("{noop}secret")
//                        .authorities("ROLE_READ").build(),
//                User.withUsername("smitha")
//                        .password("{noop}secret")
//                        .authorities("ROLE_ADMIN", "ROLE_READ")
//                        .build());
//    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .formLogin(Customizer.withDefaults())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/**").hasAnyRole("ADMIN", "READ")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated())
                .build();
    }

//    @Bean
//    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/resources/**", "/login", "/about").permitAll()
//                        .requestMatchers("/admin/**").hasRole("ADMIN")
//                        .requestMatchers("/api/**").access(new WebExpressionAuthorizationManager("hasRole('ADMIN') and hasRole('READ')"))
//                        // .requestMatchers("/db/**").access(AuthorizationManagers.allOf(AuthorityAuthorizationManager.hasRole("ADMIN"), AuthorityAuthorizationManager.hasRole("DBA")))
//                        .requestMatchers("/").permitAll()
//                        .anyRequest().authenticated()
//                );
//        return http.build();
//        return http.authorizeRequests()
//                 .antMatchers("/api/**").hasAnyRole("READ", "ADMIN")
//                 .antMatchers("/admin/**").hasRole("ADMIN")
//                 .antMatchers("/").permitAll()
//                 .anyRequest().authenticated()
//                 .and().formLogin()
//                 .and().build();
//        http.authorizeRequests().httpBasic(withDefaults());
//    }
}
