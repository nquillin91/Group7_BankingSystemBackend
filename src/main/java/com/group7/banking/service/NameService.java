package com.group7.banking.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.AccountEntity;
import com.group7.banking.repository.AccountRepository;

@Service
public class NameService {
	Logger logger = LoggerFactory.getLogger(NameService.class);
	
    @Autowired
    private AccountRepository accountRepository;
    
    public String clearAndInit() {
    	logger.info("Clearing all data");
    	accountRepository.deleteAll();
    	
    	logger.info("Initializing users");
    	
    	
    	
    	return "Cleared and re-initialized";
    }
    
    public List<AccountEntity> findAll() {

        Iterable<AccountEntity> it = accountRepository.findAll();

        ArrayList<AccountEntity> users = new ArrayList<AccountEntity>();
        it.forEach(e -> {
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