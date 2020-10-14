package com.group7.banking.model.nosql;
import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Document(collection = "billing_address")
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@ToString
public class BillingAddressEntity implements Serializable {
	private static final long serialVersionUID = 6225816862643566384L;
	
	@Getter
	private String userId;
	
	@Getter
	@Setter
	private String zipCode;

	@Getter
	@Setter
	private String city;
	
	@Getter
	@Setter
	private String state;

    @Getter
    @Setter
	private String addressline2;
	
    @Getter
    @Setter
    private String addressline1 ;
    
    @Getter
	@Setter
	private LocalDateTime createdDate;
	
	@Getter
	@Setter
	private LocalDateTime lastUpdatedDate;
//to change it into dto object

	public BillingAddressEntity(String userId, String zipCode, String city, String state,String addressline2,String addressline1) {
        this.userId = userId;
        this.zipCode = zipCode;
        this.city = city;
        this.state = state;
        this.addressline1 = addressline1;
        this.addressline2 = addressline2;
        this.createdDate = LocalDateTime.now();
		this.lastUpdatedDate = LocalDateTime.now();
	}
}