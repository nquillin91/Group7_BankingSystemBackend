package com.group7.banking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.group7.banking.model.sql.RoleEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.sql.UserRepository;

@Service
public class BankingUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException(username + " not found.");
		}

		UserBuilder builder = null;
		UserEntity user = optionalUser.get();
		
		builder = org.springframework.security.core.userdetails.User.withUsername(username);
		builder.password(user.getPassword());
		
		List<String> roles = new ArrayList<String>();
		for (RoleEntity role : user.getRoles()) {
			roles.add(role.getName().split("ROLE_")[1]);
		}
		builder.roles(roles.toArray(new String[roles.size()]));

		return builder.build();
	}
}