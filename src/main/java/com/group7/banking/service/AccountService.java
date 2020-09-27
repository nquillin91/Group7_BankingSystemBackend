package com.group7.banking.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.Account;
import com.group7.banking.repository.AccountRepository;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> findAll() {

        Iterable<Account> it = accountRepository.findAll();

        ArrayList<Account> users = new ArrayList<Account>();
        it.forEach(e -> users.add(e));

        return users;
    }

    public Long count() {

        return accountRepository.count();
    }

    public void deleteById(Long userId) {

    	accountRepository.deleteById(userId);
    }
}