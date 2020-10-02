package com.group7.banking.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name="accounts")
public class Account implements Serializable {
	private static final long serialVersionUID = 6396870377766385323L;
	
	private static enum Status {
		ACTIVE,
		INACTIVE
	}
	
	public static enum Type {
		CHECKING,
		SAVINGS
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Column(name="user_id")
	private Long userId;
	
	@Getter
	@Column(name="account_type")
	private Type accountType;
	
	@Getter
	private Double balance;
	
	@Getter
	private Status status;
	
	@Getter
	@Column(name="outgoing_transactions")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "originAccount")
	private List<Transaction> outgoingTransactions;
	
	@Getter
	@Column(name="incoming_transactions")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "targetAccount")
	private List<Transaction> incomingTransactions;
	
	@Getter
	@Column(name="created_date")
	private Date createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	protected Account() {}

	public Account(User user, Type accountType, Double startingBalance) {
		this.userId = user.getId();
		this.accountType = accountType;
		this.balance = startingBalance;
		this.status = Status.ACTIVE;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format(
				"Account[id=%d, user_id=%d, account_type='%s', balance=%d,"
				+ "status='%s', created_date='%tD', last_updated_date='%tD']",
				id, accountType, balance, status, createdDate, lastUpdatedDate);
	}
}