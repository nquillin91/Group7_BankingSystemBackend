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
@Table(name="email_addresses")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class EmailAddressEntity  implements Serializable {
	private static final long serialVersionUID = -8628826072806156424L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	@ToString.Exclude private UserEntity user;
	
	@Getter
	@Column(name="email_address")
	private String emailAddress;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public EmailAddressEntity(UserEntity user, String emailAddress) {
		this.user = user;
		this.emailAddress = emailAddress;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}