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
@Table(name="phone_numbers")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class PhoneNumberEntity  implements Serializable {
	private static final long serialVersionUID = 7165149108409522461L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	@ToString.Exclude private UserEntity user;
	
	@Getter
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public PhoneNumberEntity(UserEntity user, String phoneNumber) {
		this.user = user;
		this.phoneNumber = phoneNumber;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}