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
import lombok.ToString;

@Entity
@Table(name="names")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class NameEntity  implements Serializable {
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
	@Column(name="first_name")
	private String firstName;
	
	@Getter
	@Column(name="middle_name")
	private String middleName;
	
	@Getter
	@Column(name="last_name")
	private String lastName;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public NameEntity(UserEntity user, String firstName, String middleName, String lastName) {
		this.user = user;
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}