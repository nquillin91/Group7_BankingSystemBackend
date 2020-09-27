package com.group7.banking.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.model.Account;
import com.group7.banking.service.AccountService;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;
    
    @GetMapping("/")
    public String index() {
    	return "Welcome to the banking system!";
    }
    
    @GetMapping("/accounts")
    public List<Account> allUsers() {

        return accountService.findAll();
    }

    @GetMapping("/accounts/count")
    public Long count() {

        return accountService.count();
    }

    @DeleteMapping("/account/{id}")
    public void delete(@PathVariable String id) {

        Long accountID = Long.parseLong(id);
        accountService.deleteById(accountID);
    }
}