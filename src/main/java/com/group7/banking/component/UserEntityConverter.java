package com.group7.banking.component;

import org.springframework.stereotype.Component;

import com.group7.banking.dto.UserDTO;
import com.group7.banking.model.sql.NameEntity;
import com.group7.banking.model.sql.UserEntity;

@Component
public class UserEntityConverter {
	public UserDTO toResponse(UserEntity userEntity) {
		UserDTO userData = new UserDTO();
		
		userData.setUserName(userEntity.getUsername());
		
		NameEntity userName = userEntity.getName();
		userData.setFirstName(userName.getFirstName());
		userData.setMiddleName(userName.getMiddleName());
		userData.setLastName(userName.getLastName());
		
		return userData;
    }
}