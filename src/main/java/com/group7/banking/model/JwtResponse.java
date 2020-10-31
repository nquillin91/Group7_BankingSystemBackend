package com.group7.banking.model;

import lombok.Getter;

public class JwtResponse {
	@Getter
	private final String jwtToken;
	
	public JwtResponse(String jwtToken) {
		this.jwtToken = jwtToken;
	}
}