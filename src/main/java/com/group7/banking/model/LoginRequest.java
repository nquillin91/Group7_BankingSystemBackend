package com.group7.banking.model;

import lombok.Data;

@Data
public class LoginRequest {
	private String username;
	private String password;
}