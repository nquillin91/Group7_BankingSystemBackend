package com.group7.banking.model.sql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="accounts")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class AccountEntity implements Serializable {
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
	@ManyToOne
    @JoinColumn(name = "user_id")
	@ToString.Exclude private UserEntity user;
	
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
	private Set<TransactionEntity> outgoingTransactions;
	
	@Getter
	@Column(name="incoming_transactions")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "targetAccount")
	private Set<TransactionEntity> incomingTransactions;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public AccountEntity(UserEntity user, Type accountType, Double startingBalance) {
		this.user = user;
		this.accountType = accountType;
		this.balance = startingBalance;
		this.status = Status.ACTIVE;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}