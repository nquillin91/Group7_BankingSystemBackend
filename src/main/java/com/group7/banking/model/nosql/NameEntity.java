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


@Document(collection = "names")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class NameEntity implements Serializable {
	private static final long serialVersionUID = -6724917438603850834L;
	
	@Id
	@Getter
	private String id;
	
	@Getter
	@Setter
	@Field(name="first_name")
	private String firstName;
	
	@Getter
	@Setter
	@Field(name = "middle_name")
	private String middleName;
	
	@Getter
	@Setter
	@Field(name = "last_name")
	private String lastName;
	
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
	
	public NameEntity(Long userId, String firstName, String middleName, String lastName) {
		this.userId = userId;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}