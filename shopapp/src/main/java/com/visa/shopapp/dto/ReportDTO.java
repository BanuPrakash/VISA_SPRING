package com.visa.shopapp.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ReportDTO {
	@JsonFormat(pattern="dd-MMM-yyyy HH:mm:ss")
	Date orderDate;
	double total;
	String firstName;
	String lastName;
	String email;
}
