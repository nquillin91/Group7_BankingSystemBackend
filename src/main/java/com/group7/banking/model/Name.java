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
@Table(name="names")
public class Name  implements Serializable {
	private static final long serialVersionUID = -6724917438603850834L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Column(name="first_name")
	private String firstName;
	
	@Getter
	@Column(name="last_name")
	private String lastName;
	
	@Getter
	@Column(name="created_date")
	private Date createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	protected Name() {}

	public Name(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format(
				"EmailAddress[id=%d, first_name='%s',"
				+ "last_name='%s', created_date='%tD', last_updated_date='%tD']",
				id, firstName, lastName, createdDate, lastUpdatedDate);
	}
}