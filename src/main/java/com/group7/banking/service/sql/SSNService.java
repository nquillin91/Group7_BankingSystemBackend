package com.group7.banking.service.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.repository.sql.SSNRepository;

@Service
public class SSNService {
	Logger logger = LoggerFactory.getLogger(SSNService.class);
	
    @Autowired
    private SSNRepository ssnRepository;

    public void deleteById(Long userId) {
    	ssnRepository.deleteById(userId);
    }
}