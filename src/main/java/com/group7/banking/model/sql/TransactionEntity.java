package com.group7.banking.model.sql;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

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
	
	public enum Status {
		PENDING,
		COMPLETE,
		REJECTED
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Setter
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "origin_account_id", referencedColumnName = "id")
	private AccountEntity originAccount;
	
	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
	@Setter
    @JoinColumn(name = "target_account_id", referencedColumnName = "id")
	private AccountEntity targetAccount;
	
	@Getter
	@Setter
	private String transactionType;
	
	@Getter
	@Setter
	private Double amount;
	
	@Setter
	@Getter
	private String comments;
	
	@Getter
	@Setter
	private int status;
	
	@Getter
	@Setter
	@Column(name = "created_date")
	private Date createdDate;
	
	@Getter
	@Setter
	@Column(name = "last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public TransactionEntity(AccountEntity originAccount, AccountEntity targetAccount, String transactionType, 
			Double amount, String comments,Date date) {
		this.originAccount = originAccount;
		this.targetAccount = targetAccount;
		this.transactionType = transactionType;
		this.amount = amount;
		this.comments = comments;
		this.status = 0;
		this.createdDate =date;
		this.lastUpdatedDate = LocalDateTime.now();
	}
}
