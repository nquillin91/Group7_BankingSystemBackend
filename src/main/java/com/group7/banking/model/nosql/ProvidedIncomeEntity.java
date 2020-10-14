package com.group7.banking.model.nosql;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import org.springframework.data.mongodb.core.mapping.Field;

import com.group7.banking.model.sql.UserEntity;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "provided_incomes")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class ProvidedIncomeEntity implements Serializable {
	private static final long serialVersionUID = -6724917438603850834L;

	@Id
	@Getter
	private Long id;
	
	
	@Getter
	@Setter
	@Field(name="income_amount")
	private double incomeAmount;
	
	@Getter
	@Field(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Field(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public ProvidedIncomeEntity(double incomeAmount) {
		
		this.incomeAmount = incomeAmount;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}