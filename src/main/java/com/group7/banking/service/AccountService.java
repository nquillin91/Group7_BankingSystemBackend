package com.group7.banking.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.Account;
import com.group7.banking.repository.AccountRepository;

@Service
public class AccountService {
	Logger logger = LoggerFactory.getLogger(AccountService.class);
	
    @Autowired
    private AccountRepository accountRepository;
    
    public String init() {
    	logger.info("initializing users");

        Account u1 = new Account("Paul", "Smith");
        accountRepository.save(u1);

        Account u2 = new Account("Robert", "Black");
        accountRepository.save(u2);

        Account u3 = new Account("John", "Doe");
        accountRepository.save(u3);
        
        return "Init completed";
    }
    
    public List<Account> findAll() {

        Iterable<Account> it = accountRepository.findAll();

        ArrayList<Account> users = new ArrayList<Account>();
        it.forEach(e -> {
        	logger.debug(e.getId().toString());
        	logger.debug(e.getFirstName());
        	logger.debug(e.getLastName());
        	users.add(e);
        });

        return users;
    }

    public Long count() {

        return accountRepository.count();
    }

    public void deleteById(Long userId) {

    	accountRepository.deleteById(userId);
    }
}