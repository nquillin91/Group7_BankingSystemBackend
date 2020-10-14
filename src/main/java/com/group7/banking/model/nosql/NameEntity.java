package com.group7.banking.model.nosql;


import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.group7.banking.model.sql.UserEntity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
	@Field(name="first_name")
	private String firstName;
	
	@Getter
	@Field(name="middle_name")
	private String middleName;
	
	@Getter
	@Field(name="last_name")
	private String lastName;
	
	@Getter
	@Field(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Field(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;
	
	public NameEntity(String firstName, String middleName, String lastName) {
		
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}