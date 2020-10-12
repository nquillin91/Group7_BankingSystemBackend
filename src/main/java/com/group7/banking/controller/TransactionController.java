package com.group7.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.model.AccountEntity;
import com.group7.banking.service.AccountService;
import com.group7.banking.service.TransactionService;

@RestController("TransactionController")
public class TransactionController {
	
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/trasaction")
public String welcome()
{
		return "Welcome to the banking system!";
    	
}
    
 
}