package com.visa.shopapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int oid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="order_date")
    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date orderDate = new Date(); // system date

    private double total;

    @ManyToOne
    @JoinColumn(name="customer_fk")
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="order_fk")
    private List<LineItem> items = new ArrayList<>();
}
