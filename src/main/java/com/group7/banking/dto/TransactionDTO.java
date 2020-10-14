package com.group7.banking.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class TransactionDTO {
	   private Long id;
		private AccountDTO originAccountDetails;
		private AccountDTO targetAccountDetails;
		private String transactionType;
		private Double amount;
		private String comments;
		private Status status;
		private LocalDateTime createdDate;
		private LocalDateTime lastUpdatedDate;
		
		private enum Type {
			CHARGE,
			DEPOSIT
		}
		
		private enum Status {
			PENDING,
			COMPLETE,
			REJECTED
		}
	}