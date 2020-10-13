package com.group7.banking.service.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.repository.sql.AccountRepository;

@Service
public class AccountService {
	Logger logger = LoggerFactory.getLogger(AccountService.class);
	
    @Autowired
    private AccountRepository accountRepository;
    
    public AccountEntity findById(long id) {
    	Optional<AccountEntity> optionalAccount = accountRepository.findById(id);
    	
    	return optionalAccount.get();
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