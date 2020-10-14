package com.group7.banking.dto;

import lombok.Data;

@Data
public class SignUpDTO {
	private String username;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String birthDate;
	private String ssn;
	private Double providedIncome;
	private String phoneNumber;
	private String emailAddress;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
}