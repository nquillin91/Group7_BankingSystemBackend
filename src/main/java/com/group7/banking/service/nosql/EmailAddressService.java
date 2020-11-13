package com.group7.banking.service.nosql;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.dto.EmailAddressDTO;
import com.group7.banking.model.nosql.EmailAddressEntity;
import com.group7.banking.repository.nosql.EmailAddressRepository;

@Service
public class EmailAddressService {
	Logger logger = LoggerFactory.getLogger(EmailAddressService.class);
	
	@Autowired
	private EmailAddressRepository emailAddressRepository;
	
	public void updateEmailAddress(long userId, EmailAddressDTO dto) {
		List<EmailAddressEntity> emailAddrs = emailAddressRepository.findByUserId(userId);
		
		if (emailAddrs.size() > 0) {
			EmailAddressEntity email = emailAddrs.get(0);
			
			email.setEmailAddress(dto.getEmailAddress());
			email.setLastUpdatedDate(LocalDateTime.now());
			
			emailAddressRepository.save(email);
		}
	}
	
    public void deleteById(Long id) {
    	emailAddressRepository.deleteById(id);
    }
}