package com.group7.banking.dto;

import java.time.LocalDateTime;
import java.util.Set;

import com.group7.banking.model.sql.TransactionEntity;
import com.group7.banking.model.sql.UserEntity;

import lombok.Data;
@Data
public class AccountDTO {
	private static enum Status {
		ACTIVE,
		INACTIVE
	}
	
	public static enum Type {
		CHECKING,
		SAVINGS
	}
	
	
	private long id;
	UserProfileDTO user;
	private int accountType;
	private Double balance;
	private int status;
	private LocalDateTime createdDate;
	private LocalDateTime lastUpdatedDate;
}