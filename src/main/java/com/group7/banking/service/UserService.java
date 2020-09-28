package com.group7.banking.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.User;
import com.group7.banking.repository.UserRepository;

@Service
public class UserService {
	Logger logger = LoggerFactory.getLogger(UserService.class);
	
    @Autowired
    private UserRepository userRepository;
    
    public List<User> findAll() {
        Iterable<User> it = userRepository.findAll();

        List<User> users = new ArrayList<User>();
        it.forEach(e -> {
        	users.add(e);
        });

        return users;
    }

    public Long count() {
        return userRepository.count();
    }

    public void deleteById(Long userId) {
    	userRepository.deleteById(userId);
    }
}