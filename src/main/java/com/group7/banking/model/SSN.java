package com.group7.banking.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;

@Entity
public class SSN  implements Serializable {
	private static final long serialVersionUID = 6947361474544046401L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Column(name="ssn")
	private String ssn;
	
	@Getter
	@Column(name="created_date")
	private Date createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	protected SSN() {}

	public SSN(String ssn) {
		this.ssn = ssn;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format(
				"EmailAddress[id=%d, ssn='%s',"
				+ "created_date='%t', last_updated_date='%t']",
				id, ssn, createdDate, lastUpdatedDate);
	}
}