package com.visa.shopapp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name="customers")
public class Customer {
    @Id
    private String email;

    @Column(name="first_name", length = 100)
    private String firstName;

    @Column(name="last_name", length = 100)
    private String lastName;

//    @OneToMany(mappedBy = "customer")
//    private List<Order> orders = new ArrayList<>();
}
