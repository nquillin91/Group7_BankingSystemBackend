package com.group7.banking.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access=AccessLevel.PROTECTED)
public class LoginRequest {
	private String username;
	private String password;
}