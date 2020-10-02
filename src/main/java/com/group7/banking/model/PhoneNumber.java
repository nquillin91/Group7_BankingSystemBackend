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
@Table(name="phone_numbers")
public class PhoneNumber  implements Serializable {
	private static final long serialVersionUID = 7165149108409522461L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Column(name="phone_number")
	private String phoneNumber;
	
	@Getter
	@Column(name="created_date")
	private Date createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	protected PhoneNumber() {}

	public PhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format(
				"EmailAddress[id=%d, phone_number='%s',"
				+ "created_date='%tD', last_updated_date='%tD']",
				id, phoneNumber, createdDate, lastUpdatedDate);
	}
}