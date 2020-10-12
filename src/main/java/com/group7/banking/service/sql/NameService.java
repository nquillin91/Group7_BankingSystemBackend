package com.group7.banking.service.sql;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.repository.sql.AccountRepository;

@Service
public class NameService {
	Logger logger = LoggerFactory.getLogger(NameService.class);
	
    @Autowired
    private AccountRepository accountRepository;
//    
//    public String clearAndInit() {
//    	logger.info("Clearing all data");
//    	accountRepository.deleteAll();
//    	
//    	logger.info("Initializing users");
//    	
//    	
//    	
//    	return "Cleared and re-initialized";
//    }
//    
   
    
    
    
//    If bal>send money: send money
     void sendMoney() {
    	
    }
//  To check is balance> send money
    public void validateTransaction() {
    	
    }
    
// TODO:  Get beneficiary
    public void getBeneficiary() {
    	
    }
    
    
    
    
    
    
}