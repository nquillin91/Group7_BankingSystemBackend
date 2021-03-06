package com.group7.banking.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.dto.AccountDTO;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.service.sql.AccountService;

@RestController("AccountController")
public class AccountController {

	@Autowired
	private AccountService accountService;

	@GetMapping("/accounts/{id}")
	public AccountEntity get(@PathVariable long id) throws Exception {
		return accountService.findById(id);
	}

	@DeleteMapping("/accounts/{id}")
	public void delete(@PathVariable String id) {
		Long accountID = Long.parseLong(id);
		accountService.deleteById(accountID);
	}
}