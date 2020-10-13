package com.group7.banking.controller;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.service.sql.MainService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController("MainController")
public class MainController {
    Logger logger = LoggerFactory.getLogger(MainService.class);
    
	@Autowired
	private MainService mainService;
	
    @GetMapping("/")
    public String index() {
        logger.info("Clearing all data");
    	return "Welcome to the banking system !!!";
    }
    
    @GetMapping("/init")
    public String init() {
        //System.out.print("test");
    	return mainService.clearAndInit();
    }
}