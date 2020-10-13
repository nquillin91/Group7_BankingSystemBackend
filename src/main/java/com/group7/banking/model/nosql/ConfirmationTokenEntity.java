package com.group7.banking.model.nosql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "confirmation_tokens")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class ConfirmationTokenEntity implements Serializable {
	private static final long serialVersionUID = 6225816862643566384L;

	@Id
	@Getter
	private String id;

	@Getter
	@Setter
	private String confirmationToken;

	@Getter
	private LocalDateTime createdDate;
	
	@Getter
	@Setter
	private LocalDateTime expirationDate;

	@Getter
	private Long userId;

	public ConfirmationTokenEntity(Long userId) {
		this.userId = userId;
		this.createdDate = LocalDateTime.now();
		this.confirmationToken = UUID.randomUUID().toString();
	}
}