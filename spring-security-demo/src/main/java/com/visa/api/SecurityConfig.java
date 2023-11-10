package com.visa.api;

import javax.sql.DataSource;

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
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	 
	  @Bean
	  public SecurityFilterChain configure(HttpSecurity http) throws Exception {
	    http.authorizeHttpRequests((requests) -> requests
	    		.requestMatchers("/products").hasAnyRole("READ", "ADMIN")
	    		.requestMatchers("/orders").hasRole("ADMIN")
	            .requestMatchers("/").permitAll())
	        .formLogin(Customizer.withDefaults());

	    return http.build();
	  }
	  
//	  @Bean
//	  public InMemoryUserDetailsManager users() {
//		  return new InMemoryUserDetailsManager(
//				  User.withUsername("Sam")
//				  .password("{noop}secret")
//				  .authorities("ROLE_READ").build(),
//				  
//				   User.withUsername("Rita")
//				  .password("{noop}secret")
//				  .authorities("ROLE_ADMIN", "ROLE_READ").build());
//	  }
	  
	  @Bean
	  public PasswordEncoder passwordEncoder() {
		  return new BCryptPasswordEncoder();
	  }
	  
	  @Autowired
	  DataSource ds; // pool of database connection --> application.properties
	  
	  
	  public void configureJdbcAuth(AuthenticationManagerBuilder builder) throws Exception {
		builder.jdbcAuthentication().dataSource(ds);  
		//https://docs.spring.io/spring-security/site/docs/5.0.x/reference/html/appendix-schema.html
	  }
}
