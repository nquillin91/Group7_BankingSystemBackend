package com.group7.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.service.AccountService;

@RestController("AccountController")
public class AccountController {
	
    @Autowired
    private AccountService accountService;

    @DeleteMapping("/accounts/{id}")
    public void delete(@PathVariable String id) {

        Long accountID = Long.parseLong(id);
        accountService.deleteById(accountID);
    }
}