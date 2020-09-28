package com.group7.banking.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name="users")
public class User implements Serializable {
	private static final long serialVersionUID = -1229223520906444433L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Column(name="user_name")
	private String userName;
	
	@Getter
	@Column(name="password")
	private String password;
	
	@Getter
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "name_id", referencedColumnName = "id")
	private Name name;
	
	@Getter
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	private BillingAddress billingAddress;
	
	@Getter
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "email_address_id", referencedColumnName = "id")
	private EmailAddress emailAddress;
	
	@Getter
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_number_id", referencedColumnName = "id")
	private PhoneNumber phoneNumber;
	
	@Getter
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ssn_id", referencedColumnName = "id")
	private SSN ssn;
	
	@Getter
	@Column(name="accounts")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "userId")
	private List<Account> accounts = new ArrayList<Account>();
	
	@Getter
	@Column(name="created_date")
	private Date createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	protected User() {}

	public User(String userName, String password, Name name, BillingAddress billingAddr,
			EmailAddress emailAddr, PhoneNumber phoneNum, SSN ssn) {
		this.userName = userName;
		this.password = password;
		this.name = name;
		this.billingAddress = billingAddr;
		this.emailAddress = emailAddr;
		this.phoneNumber = phoneNum;
		this.ssn = ssn;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}
	
	public void addAccount(Account account) {
		this.accounts.add(account);
	}

	@Override
	public String toString() {
		return String.format(
				"User[id=%d, userName='%s',"
				+ "name='%s', billing_address='%s', email_address='%s', phone_number='%s',"
				+ "created_date='%t', last_updated_date='%t']",
				id, userName, name.toString(), billingAddress.toString(), emailAddress.toString(),
				phoneNumber.toString(), createdDate, lastUpdatedDate);
	}
}