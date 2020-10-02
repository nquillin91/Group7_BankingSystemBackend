package com.group7.banking.model;

import lombok.Data;

@Data
public class SignUpRequest {
	private String username;
	private String password;
	private String firstName;
	private String middleName;
	private String lastName;
	private String phoneNumber;
	private String emailAddress;
	private String ssn;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
}