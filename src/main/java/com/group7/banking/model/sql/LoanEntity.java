package com.group7.banking.model.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="loans")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class LoanEntity implements Serializable {
	private static final long serialVersionUID = -7748997717805929138L;

	private static enum Status {
		ACTIVE,
		PASTDUE,
		CLOSED
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
	@Setter
	@Column(name = "loan_amount")
	private Double loanAmount;
	
	@Getter
	@Setter
	@Column(name = "remaining_balance")
	private Double remainingBalance;
	
	@Getter
	private Status status;
	
	@Getter
	private int terms;
	
	@Getter
	@Setter
	@Column(name = "current_term")
	private int currentTerm;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public LoanEntity(UserEntity user, Double loanAmount, int terms) {
		this.user = user;
		this.loanAmount = loanAmount;
		this.remainingBalance = loanAmount;
		this.terms = terms;
		this.currentTerm = 0;
		this.status = Status.ACTIVE;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}