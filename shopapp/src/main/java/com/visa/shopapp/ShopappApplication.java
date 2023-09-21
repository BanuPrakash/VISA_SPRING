package com.visa.shopapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@SpringBootApplication
public class ShopappApplication {

	public static void main(String[] args) {

		long days = ChronoUnit.DAYS.between(LocalDate.of(2023,9,10),LocalDate.of(2023,9,14));
		System.out.println(days);
		SpringApplication.run(ShopappApplication.class, args);
	}

}
