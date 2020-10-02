package com.group7.banking.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="transactions")
public class Transaction  implements Serializable {
	private static final long serialVersionUID = -665585428167785747L;

	private enum Type {
		CHARGE,
		DEPOSIT
	}
	
	private enum Status {
		PENDING,
		COMPLETE,
		REJECTED
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_account_id", referencedColumnName = "id")
	private Account originAccount;
	
	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id", referencedColumnName = "id")
	private Account targetAccount;
	
	@Getter
	private Type transactionType;
	
	@Getter
	private Double amount;
	
	@Getter
	private String comments;
	
	@Getter
	@Setter
	private Status status;
	
	@Getter
	@Column(name = "created_date")
	private Date createdDate;
	
	@Getter
	@Setter
	@Column(name = "last_updated_date")
	private Date lastUpdatedDate;
	
	protected Transaction() {}

	public Transaction(Account originAccount, Account targetAccount, Type transactionType, 
			Double amount, String comments) {
		this.originAccount = originAccount;
		this.targetAccount = targetAccount;
		this.transactionType = transactionType;
		this.amount = amount;
		this.comments = comments;
		this.status = Status.PENDING;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format(
				"Transaction[id=%d, origin_account='%s',"
				+ "target_account='%s', transactionType='%s', amount=%d,"
				+ "comments='%s', status='%s'"
				+ "created_date='%tD', last_updated_date='%tD']",
				id, originAccount.getId(), targetAccount.getId(),
				transactionType.toString(), amount, comments, status,
				createdDate, lastUpdatedDate);
	}
}