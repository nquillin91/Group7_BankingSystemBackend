package com.group7.banking;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.group7.banking.model.Account;
import com.group7.banking.repository.AccountRepository;

@Component
public class TestRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(TestRunner.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        logger.info("initializing users");

        Account u1 = new Account("Paul", "Smith");
        accountRepository.save(u1);

        Account u2 = new Account("Robert", "Black");
        accountRepository.save(u2);

        Account u3 = new Account("John", "Doe");
        accountRepository.save(u3);
    }
}