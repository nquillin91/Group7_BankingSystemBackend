package com.group7.banking.model.sql;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="ssns")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class SsnEntity  implements Serializable {
	private static final long serialVersionUID = 6947361474544046401L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@OneToOne(mappedBy = "ssn")
	@ToString.Exclude private UserEntity user;
	
	@Getter
	@Column(name="ssn")
	private String ssn;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public SsnEntity(UserEntity user, String ssn) {
		this.user = user;
		this.ssn = ssn;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}