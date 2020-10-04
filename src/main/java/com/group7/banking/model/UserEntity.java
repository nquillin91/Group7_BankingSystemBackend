package com.group7.banking.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class UserEntity implements Serializable {
	private static final long serialVersionUID = -1229223520906444433L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Column(name="user_name")
	private String username;
	
	@Getter
	@Setter
	@Column(name="password")
	private String password;
	
	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "name_id", referencedColumnName = "id")
	private NameEntity name;
	
	@Getter
	@Column(name="birthdate")
	private LocalDate birthdate;
	
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "provided_income_id", referencedColumnName = "id")
	private ProvidedIncomeEntity providedIncome;
	
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id", referencedColumnName = "id")
	private BillingAddressEntity billingAddress;
	
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "email_address_id", referencedColumnName = "id")
	private EmailAddressEntity emailAddress;
	
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "phone_number_id", referencedColumnName = "id")
	private PhoneNumberEntity phoneNumber;
	
	@Getter
	@Setter
	@OneToOne(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ssn_id", referencedColumnName = "id")
	private SsnEntity ssn;
	
	@Getter
	@Setter
	@Column(name="accounts")
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<AccountEntity> accounts = new ArrayList<AccountEntity>();
	
	@Getter
	@Setter
	@Column(name="enabled")
	private Boolean enabled;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Setter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public UserEntity(String username, String password, LocalDate birthDate) {
		this.username = username;
		this.password = password;
		this.birthdate = birthDate;
		this.enabled = false;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
	
	public void addAccount(AccountEntity account) {
		this.accounts.add(account);
	}
}