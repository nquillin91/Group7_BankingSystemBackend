package com.group7.banking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.group7.banking.config.JwtUserDetails;
import com.group7.banking.model.sql.RoleEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.sql.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		Optional<UserEntity> optionalUser = userRepository.findByUsername(username);

		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException(username + " not found.");
		}
		
		UserEntity user = optionalUser.get();
		
		List<String> roles = new ArrayList<String>();
		for (RoleEntity role : user.getRoles()) {
			roles.add(role.getName().split("ROLE_")[1]);
		}
		
		return new JwtUserDetails(
				user.getUsername(), 
                user.getPassword(),
                user.getId(),
                buildRoles(roles.toArray(new String[roles.size()]))
        );
	}
	
	public UserDetails loadUserByUserId(String userId) {
		Optional<UserEntity> optionalUser = userRepository.findById(Long.parseLong(userId));

		if (!optionalUser.isPresent()) {
			throw new UsernameNotFoundException(userId + " not found.");
		}
		
		UserEntity user = optionalUser.get();
		
		List<String> roles = new ArrayList<String>();
		for (RoleEntity role : user.getRoles()) {
			roles.add(role.getName().split("ROLE_")[1]);
		}
		
		return new JwtUserDetails(
				user.getUsername(), 
                user.getPassword(),
                user.getId(),
                buildRoles(roles.toArray(new String[roles.size()]))
        );
	}
	
	private List<GrantedAuthority> buildRoles(String[] roles) {
		List<GrantedAuthority> authorities = new ArrayList<>(roles.length);
		
		for (String role : roles) {
			Assert.isTrue(!role.startsWith("ROLE_"),
					() -> role + " cannot start with ROLE_ (it is automatically added)");
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}
		
		return new ArrayList<GrantedAuthority>(authorities);
	}
}