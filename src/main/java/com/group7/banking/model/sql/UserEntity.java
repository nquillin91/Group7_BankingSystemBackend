package com.group7.banking.model.sql;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class UserEntity implements UserDetails {
	private static final long serialVersionUID = -1229223520906444433L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
	@Getter
	@Setter
	@Column(name="user_name")
	private String username;
	
	@Getter
	@Setter
	@Column(name="password")
	private String password;
	
	@Getter
	@Column(name="birthdate")
	private LocalDate birthdate;
	
	
	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "billing_address_id")
	private BillingAddressEntity billingAddress;
	
	@Getter
	@Setter
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "ssn_id")
	private SsnEntity ssn;
	
	@Getter
	@Setter
	@Column(name="accounts")
	@OneToMany(mappedBy = "user")
	private Set<AccountEntity> accounts;
	
	@Getter
	@Setter
	@Column(name="loans")
	@OneToMany(mappedBy = "user")
	private Set<LoanEntity> loans;
	
	@Getter
	@Setter
	@ManyToMany(fetch=FetchType.EAGER)
    @JoinTable( 
        name = "users_roles", 
        joinColumns = @JoinColumn(
          name = "user_id", referencedColumnName = "id"), 
        inverseJoinColumns = @JoinColumn(
          name = "role_id", referencedColumnName = "id")) 
    private Set<RoleEntity> roles;
	
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
		if (this.getAccounts() == null) {
			Set<AccountEntity> accounts = new HashSet<AccountEntity>();
			accounts.add(account);
			this.setAccounts(accounts);
		} else {
			if (!this.getAccounts().contains(account)) {
				this.getAccounts().add(account);
			}
		}
	}
	
	public void addLoan(LoanEntity loan) {
		if (this.getLoans() == null) {
			Set<LoanEntity> loans = new HashSet<LoanEntity>();
			loans.add(loan);
			this.setLoans(loans);
		} else {
			if (!this.getLoans().contains(loan)) {
				this.getLoans().add(loan);
			}
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<RoleEntity> roles = this.getRoles();
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
         
        for (RoleEntity role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
         
        return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return this.getEnabled();
	}
}