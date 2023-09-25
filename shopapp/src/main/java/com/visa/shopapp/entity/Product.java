package com.visa.shopapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotBlank(message = "Name is required")
    private String name;
    @Min(message = "Price ${validatedValue} should be greater than equal to {value}", value = 10)
    private double price;

    @Min(message = "Qty ${validatedValue} should be greater than equal to {value}", value = 50)
    private int quantity;
}
