package com.group7.banking.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="transactions")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class TransactionEntity  implements Serializable {
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
	private AccountEntity originAccount;
	
	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "target_account_id", referencedColumnName = "id")
	private AccountEntity targetAccount;
	
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
	private LocalDateTime createdDate;
	
	@Getter
	@Setter
	@Column(name = "last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public TransactionEntity(AccountEntity originAccount, AccountEntity targetAccount, Type transactionType, 
			Double amount, String comments) {
		this.originAccount = originAccount;
		this.targetAccount = targetAccount;
		this.transactionType = transactionType;
		this.amount = amount;
		this.comments = comments;
		this.status = Status.PENDING;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}