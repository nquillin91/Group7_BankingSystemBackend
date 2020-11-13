package com.group7.banking.service.nosql;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.dto.PhoneNumberDTO;
import com.group7.banking.model.nosql.PhoneNumberEntity;
import com.group7.banking.repository.nosql.PhoneNumberRepository;

@Service
public class PhoneNumberService {
	Logger logger = LoggerFactory.getLogger(PhoneNumberService.class);
	
	@Autowired
	private PhoneNumberRepository phoneNumberRepository;
	
	public void updatePhoneNumber(long userId, PhoneNumberDTO dto) {
		PhoneNumberEntity phoneNumber = phoneNumberRepository.findByUserId(userId);
		
		if (phoneNumber != null) {
			phoneNumber.setPhoneNumber(dto.getPhoneNumber());
			phoneNumber.setLastUpdatedDate(LocalDateTime.now());
			
			phoneNumberRepository.save(phoneNumber);
		} else {
			PhoneNumberEntity newPhoneNumber = new PhoneNumberEntity(userId, dto.getPhoneNumber());
			
			phoneNumberRepository.save(newPhoneNumber);
		}
	}
	
	public void deleteById(Long id) {
		phoneNumberRepository.deleteById(id);
    }
}