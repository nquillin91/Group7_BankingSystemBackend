package com.group7.banking.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "accounts")
public class Account implements Serializable {
	private static final long serialVersionUID = 6396870377766385323L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Getter
	private Long id;
	
	@Getter
	@Column(name="first_name")
	private String firstName;
	
	@Getter
	@Column(name="last_name")
	private String lastName;

	protected Account() {}

	public Account(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString() {
		return String.format(
				"Account[id=%d, firstName='%s', lastName='%s']",
				id, firstName, lastName);
	}
}