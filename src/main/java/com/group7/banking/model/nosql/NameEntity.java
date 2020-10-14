package com.group7.banking.model.nosql;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "names")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class NameEntity implements Serializable {
	private static final long serialVersionUID = 6225816862643566384L;
	
	@Id
	@Getter
	private String id;
	
	@Getter
	@Setter
	private String firstName;

	@Getter
	@Setter
	private String middleName;
	
	@Getter
	@Setter
	private String lastName;

	@Getter
	private Long userId;
	
	@Getter
	private LocalDateTime createdDate;
	
	@Getter
	@Setter
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