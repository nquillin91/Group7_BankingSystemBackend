package com.group7.banking.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

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
@Table(name="confirmation_tokens")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class ConfirmationTokenEntity implements Serializable {
	private static final long serialVersionUID = 6225816862643566384L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Getter
	@Setter
	private String confirmationToken;

	@Getter
	private LocalDateTime createdDate;
	
	@Getter
	@Setter
	private LocalDateTime expirationDate;

	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	@ToString.Exclude private UserEntity user;

	public ConfirmationTokenEntity(UserEntity user) {
		this.user = user;
		this.createdDate = LocalDateTime.now();
		this.confirmationToken = UUID.randomUUID().toString();
	}
}