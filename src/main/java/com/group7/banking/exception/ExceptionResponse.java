package com.group7.banking.exception;

import java.util.Date;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExceptionResponse {
	@Getter
	@NonNull
	private String message;
	
	@Getter
	@NonNull
	private String detail;
	
	@Getter
	@NonNull
	private Date timestamp;
}