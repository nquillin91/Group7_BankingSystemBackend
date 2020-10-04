package com.group7.banking.component;

import org.springframework.stereotype.Component;

import com.group7.banking.model.NameEntity;
import com.group7.banking.model.UserData;
import com.group7.banking.model.UserEntity;

@Component
public class UserEntityConverter {
	public UserData toResponse(UserEntity userEntity) {
		UserData userData = new UserData();
		
		userData.setUserName(userEntity.getUsername());
		
		NameEntity userName = userEntity.getName();
		userData.setFirstName(userName.getFirstName());
		userData.setMiddleName(userName.getMiddleName());
		userData.setLastName(userName.getLastName());
		
		return userData;
    }
}