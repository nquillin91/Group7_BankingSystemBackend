package com.group7.banking.dto;

import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

@Data
public class TransactionDTO {
	private String senderName;
	private int accountType;
	private long senderAccountNumber;
	private double accountBalance;
	private String receiverName;
	private long receiverAccountNumber;
	private double transferAmount;
	private Date date;
	private String remarks;
	private int status;
	public enum Status {
		PENDING,
		COMPLETE,
		REJECTED
	}

}