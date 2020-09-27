package com.group7.banking.springboot;

import java.util.Date;

public class Transactions {

	private String senderAccountNumber;
	private String receiverAccountNumber;
	private double amountToBeTransfered;
	private double account;
	private Date dateOfTransfer;
	private String transferMode;
	private String remarks;

	public void initiateTransaction() {
	}

	public void validateTransaction() {
	}

	public void transactionSummary() {
	}

	public String getSenderAccountNumber() {
		return senderAccountNumber;
	}

	public void setSenderAccountNumber(String senderAccountNumber) {
		this.senderAccountNumber = senderAccountNumber;
	}

	public String getReceiverAccountNumber() {
		return receiverAccountNumber;
	}

	public void setReceiverAccountNumber(String receiverAccountNumber) {
		this.receiverAccountNumber = receiverAccountNumber;
	}

	public double getAmountToBeTransfered() {
		return amountToBeTransfered;
	}

	public void setAmountToBeTransfered(double amountToBeTransfered) {
		this.amountToBeTransfered = amountToBeTransfered;
	}

	public double getAccount() {
		return account;
	}

	public void setAccount(double account) {
		this.account = account;
	}

	public Date getDateOfTransfer() {
		return dateOfTransfer;
	}

	public void setDateOfTransfer(Date dateOfTransfer) {
		this.dateOfTransfer = dateOfTransfer;
	}

	public String getTransferMode() {
		return transferMode;
	}

	public void setTransferMode(String transferMode) {
		transferMode = transferMode;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
