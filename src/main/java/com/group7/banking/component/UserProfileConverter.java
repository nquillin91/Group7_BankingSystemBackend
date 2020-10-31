package com.group7.banking.component;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group7.banking.dto.UserProfileDTO;
import com.group7.banking.model.nosql.EmailAddressEntity;
import com.group7.banking.model.nosql.NameEntity;
import com.group7.banking.model.nosql.PhoneNumberEntity;
import com.group7.banking.model.nosql.ProvidedIncomeEntity;
import com.group7.banking.model.sql.BillingAddressEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.nosql.EmailAddressRepository;
import com.group7.banking.repository.nosql.NameRepository;
import com.group7.banking.repository.nosql.PhoneNumberRepository;
import com.group7.banking.repository.nosql.ProvidedIncomeRepository;

@Component
public class UserProfileConverter {
	
	@Autowired
	private NameRepository nameRepository;
	
	@Autowired
	private EmailAddressRepository emailAddressRepository;
	
	@Autowired
	private PhoneNumberRepository phoneNumberRepository;
	
	@Autowired
	private ProvidedIncomeRepository providedIncomeRepository;
	
	public UserProfileDTO toResponse(UserEntity userEntity) {
		UserProfileDTO userProfile = new UserProfileDTO();
		
		// Make various no sql repo calls to get the name entity, the email address entity,
		// the phone number entity, and the provided income entity
		NameEntity name = nameRepository.findByUserId(userEntity.getId());
		List<EmailAddressEntity> emailAddressList = emailAddressRepository.findByUserId(userEntity.getId());
		PhoneNumberEntity phoneNumber = phoneNumberRepository.findByUserId(userEntity.getId());
		ProvidedIncomeEntity providedIncome = providedIncomeRepository.findByUserId(userEntity.getId());
		BillingAddressEntity billingAddress = userEntity.getBillingAddress();
		
		// Set username on the profile
		userProfile.setUsername(userEntity.getUsername());
		
		// Pull email address from the first entity in the list (supposed to be primary email)
		userProfile.setEmailAddress(emailAddressList.get(0).getEmailAddress());
		
		// Pull first, middle, and last name from name entity
		if (name != null) {
			userProfile.setFirstName(name.getFirstName());
			userProfile.setMiddleName(name.getMiddleName());
			userProfile.setLastName(name.getLastName());	
		}
		
		// Pull birthdate and then format it to a string for the profile
		if (userEntity.getBirthdate() != null) {
			userProfile.setBirthDate(userEntity.getBirthdate().toString());	
		}
		
		// Pull phone number from phone number entity
		if (phoneNumber != null) {
			userProfile.setPhoneNumber(phoneNumber.getPhoneNumber());
		}
		
		if (providedIncome != null) {
			// Pull amount from provided income entity
			userProfile.setProvidedIncome(providedIncome.getIncomeAmount());
		}
		
		if (billingAddress != null) {
			// Parse out the individual pieces from the billing address entity
			userProfile.setAddressLine1(billingAddress.getAddressLine1());
			userProfile.setAddressLine2(billingAddress.getAddressLine2());
			userProfile.setCity(billingAddress.getCity());
			userProfile.setState(billingAddress.getState());
			userProfile.setZipCode(billingAddress.getZipcode());
		}
		
		return userProfile;
    }
}