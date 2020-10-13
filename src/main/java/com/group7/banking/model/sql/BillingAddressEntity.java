package com.group7.banking.model.sql;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="billing_addresses")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class BillingAddressEntity  implements Serializable {
	private static final long serialVersionUID = -3840052405233002660L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;

	@Getter
	@Setter
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "billingAddress")
	@ToString.Exclude private Set<UserEntity> users;
	
	@Getter
	@Column(name="address_line_1")
	private String addressLine1;
	
	@Getter
	@Column(name="address_line_2")
	private String addressLine2;
	
	@Getter
	@Column(name="city")
	private String city;
	
	@Getter
	@Column(name="state")
	private String state;
	
	@Getter
	@Column(name="zipcode")
	private String zipcode;
	
	@Getter
	@Column(name="created_date")
	private LocalDateTime createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private LocalDateTime lastUpdatedDate;

	public BillingAddressEntity(String addressLine1, String addressLine2,
			String city, String state, String zipcode) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}