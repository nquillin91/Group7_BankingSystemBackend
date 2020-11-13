package com.group7.banking.dto;

import lombok.Data;

@Data
public class BillingAddressDTO {
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
}