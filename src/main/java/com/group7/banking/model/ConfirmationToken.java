package com.group7.banking.model;

import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ConfirmationToken {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String confirmationToken;

	private LocalDate createdDate;

	@Getter
	@Column(name="user_id")
	private Long userId;
	
	protected ConfirmationToken() {}

	public ConfirmationToken(User user) {
		this.userId = user.getId();
		this.createdDate = LocalDate.now();
		this.confirmationToken = UUID.randomUUID().toString();
	}
}