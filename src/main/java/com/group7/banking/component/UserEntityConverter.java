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

//		
//		NameEntity userName = userEntity.getName();
//		userData.setFirstName(userName.getFirstName());
//		userData.setMiddleName(userName.getMiddleName());
//		userData.setLastName(userName.getLastName());
//		

		return userData;
    }
}