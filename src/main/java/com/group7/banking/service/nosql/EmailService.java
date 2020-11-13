package com.group7.banking.service.nosql;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.dto.EmailAddressDTO;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.nosql.EmailAddressRepository;
import com.group7.banking.repository.sql.UserRepository;

@Service
public class EmailService {
	
	@Autowired UserRepository userRepository;
	
	@Autowired
	private EmailAddressRepository emailAddressRepository;
	
	public void saveConfirmationToken(String username, EmailAddressDTO emailAddressDTO) {
		Optional<UserEntity> optionalUser = userRepository.findByUsername(username);
		
		if (optionalUser.isPresent()) {
			UserEntity user = optionalUser.get();
			
			//emailAddressRepository.save(user);
		}
	}
}