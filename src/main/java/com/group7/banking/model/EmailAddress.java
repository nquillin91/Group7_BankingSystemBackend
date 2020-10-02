package com.group7.banking.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name="email_addresses")
public class EmailAddress  implements Serializable {
	private static final long serialVersionUID = -8628826072806156424L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Column(name="email_address")
	private String emailAddress;
	
	@Getter
	@Column(name="created_date")
	private Date createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	protected EmailAddress() {}

	public EmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format(
				"EmailAddress[id=%d, email_address='%s',"
				+ "created_date='%tD', last_updated_date='%tD']",
				id, emailAddress, createdDate, lastUpdatedDate);
	}
}