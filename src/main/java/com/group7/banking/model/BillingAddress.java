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
@Table(name="billing_addresses")
public class BillingAddress  implements Serializable {
	private static final long serialVersionUID = -3840052405233002660L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Getter
	private Long id;
	
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
	private Date createdDate;
	
	@Getter
	@Column(name="last_updated_date")
	private Date lastUpdatedDate;

	protected BillingAddress() {}

	public BillingAddress(String addressLine1, String addressLine2
			, String city, String state, String zipcode) {
		this.addressLine1 = addressLine1;
		this.addressLine2 = addressLine2;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.createdDate = new Date();
		this.lastUpdatedDate = new Date();
	}

	@Override
	public String toString() {
		return String.format(
				"BillingAddress[id=%d, address_line_1='%s',"
				+ "address_line_2='%s', city='%s', state='%s',"
				+ "zipcode='%s',"
				+ "created_date='%tD', last_updated_date='%tD']",
				id, addressLine1, addressLine2, city, state, zipcode,
				createdDate, lastUpdatedDate);
	}
}