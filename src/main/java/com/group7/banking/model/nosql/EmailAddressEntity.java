package com.group7.banking.model.nosql;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Document(collection = "email_addresses")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class EmailAddressEntity implements Serializable {
	private static final long serialVersionUID = 6225816862643566384L;
	
	@Id
	@Getter
	private String id;
	
	@Getter
	@Setter
	@Field(name = "email_address")
	private String emailAddress;

	@Getter
	@Field(name = "user_id")
	private Long userId;
	
	@Getter
	@Field(name = "created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Setter
	@Field(name = "last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public EmailAddressEntity(Long userId, String emailAddress) {
        this.userId = userId;
        this.emailAddress = emailAddress;
        this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}