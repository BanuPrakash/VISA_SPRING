package com.visa.shopapp.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="line_items")
public class LineItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="item_id")
    private int itemId;

    private  int qty;

    private double amount;

    @ManyToOne
    @JoinColumn(name="product_fk")
    private Product product;
}
