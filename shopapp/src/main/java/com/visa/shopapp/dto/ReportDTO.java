package com.visa.shopapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReportDTO {
    String firstName;
    String email;
    Date orderDate;
    double total;
    String name;
    double price;
    int quantity;
    double amount;
}
