package com.group7.banking.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.Account;
import com.group7.banking.model.BillingAddress;
import com.group7.banking.model.EmailAddress;
import com.group7.banking.model.Name;
import com.group7.banking.model.PhoneNumber;
import com.group7.banking.model.SSN;
import com.group7.banking.model.User;
import com.group7.banking.repository.AccountRepository;
import com.group7.banking.repository.BillingAddressRepository;
import com.group7.banking.repository.EmailAddressRepository;
import com.group7.banking.repository.NameRepository;
import com.group7.banking.repository.PhoneNumberRepository;
import com.group7.banking.repository.SSNRepository;
import com.group7.banking.repository.UserRepository;

@Service
public class MainService {
	Logger logger = LoggerFactory.getLogger(MainService.class);
	
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BillingAddressRepository billingAddressRepository;
    
    @Autowired
    private EmailAddressRepository emailAddressRepository;
    
    @Autowired
    private NameRepository nameRepository;
    
    @Autowired
    private PhoneNumberRepository phoneNumberRepository;
    
    @Autowired
    private SSNRepository ssnRepository;
    
    @Autowired
    private AccountRepository accountRepository;
    
    public String clearAndInit() {
    	logger.info("Clearing all data");
    	//userRepository.deleteAll();
    	
    	logger.info("Initializing users");
    	
    	// -- Adding test user a --
    	Name nameA = new Name("John-a", "Doe-a");
    	nameRepository.save(nameA);
    	
    	BillingAddress billingA = new BillingAddress("123 Abc Avenue A", "Apt 1",
    			"Chicago", "IL", "60602");
    	billingAddressRepository.save(billingA);
    	
    	EmailAddress emailA = new EmailAddress("john.doeA@example.com");
    	emailAddressRepository.save(emailA);
    	
    	PhoneNumber phoneNumA = new PhoneNumber("012-345-6780");
    	phoneNumberRepository.save(phoneNumA);
    	
    	SSN ssnA = new SSN("123-a");
    	ssnRepository.save(ssnA);
    	
    	User userA = new User("userA", "passA", nameA, billingA, emailA, phoneNumA, ssnA);
    	userRepository.save(userA);
    	Account accountA = new Account(userA, Account.Type.CHECKING, 100.0);
    	userA.addAccount(accountA);
    	userRepository.save(userA);
    	accountRepository.save(accountA);
    	
    	// -- Adding test user b --
    	Name nameB = new Name("John-b", "Doe-b");
    	nameRepository.save(nameB);
    	
    	BillingAddress billingB = new BillingAddress("123 Abc Avenue B", "",
    			"Chicago", "IL", "60602");
    	billingAddressRepository.save(billingB);
    	
    	EmailAddress emailB = new EmailAddress("john.doeB@example.com");
    	emailAddressRepository.save(emailB);
    	
    	PhoneNumber phoneNumB = new PhoneNumber("012-345-6789");
    	phoneNumberRepository.save(phoneNumB);
    	
    	SSN ssnB = new SSN("123-b");
    	ssnRepository.save(ssnB);
    	
    	User userB = new User("userB", "passB", nameB, billingB, emailB, phoneNumB, ssnB);
    	userRepository.save(userB);
    	Account accountBChecking = new Account(userB, Account.Type.CHECKING, 50.0);
    	userB.addAccount(accountBChecking);
    	Account accountBSavings = new Account(userB, Account.Type.SAVINGS, 1000.0);
    	userB.addAccount(accountBSavings);
    	
    	userRepository.save(userB);
    	accountRepository.save(accountBChecking);
    	accountRepository.save(accountBSavings);
    	
    	return "Cleared and re-initialized";
    }
}