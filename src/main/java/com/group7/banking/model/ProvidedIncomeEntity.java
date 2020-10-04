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
@Table(name="provided_incomes")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class ProvidedIncomeEntity  implements Serializable {
	private static final long serialVersionUID = -6724917438603850834L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	@ToString.Exclude private UserEntity user;
	
	@Getter
	@Setter
	@Column(name="income_amount")
	private double incomeAmount;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public ProvidedIncomeEntity(UserEntity user, double incomeAmount) {
		this.user = user;
		this.incomeAmount = incomeAmount;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}