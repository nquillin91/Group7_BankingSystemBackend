package com.group7.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.service.MainService;

@RestController("MainController")
public class MainController {
    
	@Autowired
	private MainService mainService;
	
    @GetMapping("/")
    public String index() {
    	return "Welcome to the banking system!";
    }
    
    @GetMapping("/init")
    public String init() {
    	return mainService.clearAndInit();
    }
}