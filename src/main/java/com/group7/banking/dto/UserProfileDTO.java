package com.group7.banking.dto;

import lombok.Data;

@Data
public class UserProfileDTO {
	private String username;
	private String emailAddress;
	private String firstName;
	private String middleName;
	private String lastName;
	private String birthDate;
	private String phoneNumber;
	private Double providedIncome;
	private String addressLine1;
	private String addressLine2;
	private String city;
	private String state;
	private String zipcode;
	private Long id;
}