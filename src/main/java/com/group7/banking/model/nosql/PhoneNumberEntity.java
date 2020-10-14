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

@Document(collection = "phone_numbers")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString
public class PhoneNumberEntity implements Serializable {
	private static final long serialVersionUID = 7165149108409522461L;

	@Id
	@Getter
	private String id;

	@Getter
	@Setter
	@Field(name = "phone_number")
	private String phoneNumber;

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

	public PhoneNumberEntity(Long userId, String phoneNumber) {
		this.userId = userId;
		this.phoneNumber = phoneNumber;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}