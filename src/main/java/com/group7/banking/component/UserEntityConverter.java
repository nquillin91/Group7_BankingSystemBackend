package com.group7.banking.component;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group7.banking.dto.UserDTO;
import com.group7.banking.model.nosql.NameEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.nosql.NameRepository;

@Component
public class UserEntityConverter {
	
	@Autowired
	private NameRepository nameRepository;
	
	public UserDTO toResponse(UserEntity userEntity) {
		UserDTO userData = new UserDTO();
		NameEntity name = nameRepository.findByUserId(userEntity.getId());
		
		userData.setUserName(userEntity.getUsername());
		userData.setFirstName(name.getFirstName());
		userData.setMiddleName(name.getMiddleName());
		userData.setLastName(name.getLastName());
		
		return userData;
    }
}