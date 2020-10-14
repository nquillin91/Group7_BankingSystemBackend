package com.group7.banking.controller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("MainController")
public class MainController {
    Logger logger = LoggerFactory.getLogger(MainController.class);
	
    @GetMapping("/")
    public String index() {
    	return "Welcome to the banking system !!!";
    }
}