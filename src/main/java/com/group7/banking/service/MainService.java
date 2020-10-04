package com.group7.banking.service;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.AccountEntity;
import com.group7.banking.model.BillingAddressEntity;
import com.group7.banking.model.EmailAddressEntity;
import com.group7.banking.model.NameEntity;
import com.group7.banking.model.PhoneNumberEntity;
import com.group7.banking.model.ProvidedIncomeEntity;
import com.group7.banking.model.SsnEntity;
import com.group7.banking.model.UserEntity;
import com.group7.banking.repository.AccountRepository;
import com.group7.banking.repository.BillingAddressRepository;
import com.group7.banking.repository.EmailAddressRepository;
import com.group7.banking.repository.NameRepository;
import com.group7.banking.repository.PhoneNumberRepository;
import com.group7.banking.repository.ProvidedIncomeRepository;
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
    private ProvidedIncomeRepository providedIncomeRepository;
    
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
    	UserEntity userA = new UserEntity("userA", "passA", LocalDate.now());
    	
    	NameEntity nameA = new NameEntity(userA, "John-a", "", "Doe-a");
    	userA.setName(nameA);
    	
    	ProvidedIncomeEntity providedIncomeA = new ProvidedIncomeEntity(userA, 70000);
    	userA.setProvidedIncome(providedIncomeA);
    	
    	BillingAddressEntity billingA = new BillingAddressEntity(userA, "123 Abc Avenue A", "Apt 1",
    			"Chicago", "IL", "60602");
    	userA.setBillingAddress(billingA);
    	
    	EmailAddressEntity emailA = new EmailAddressEntity(userA, "john.doeA@example.com");
    	userA.setEmailAddress(emailA);
    	
    	PhoneNumberEntity phoneNumA = new PhoneNumberEntity(userA, "012-345-6780");
    	userA.setPhoneNumber(phoneNumA);
    	
    	SsnEntity ssnA = new SsnEntity(userA, "123-a");
    	userA.setSsn(ssnA);
    	
    	AccountEntity accountA = new AccountEntity(userA, AccountEntity.Type.CHECKING, 100.0);
    	userA.addAccount(accountA);
    	
    	userRepository.save(userA);
    	nameRepository.save(nameA);
    	providedIncomeRepository.save(providedIncomeA);
    	billingAddressRepository.save(billingA);
    	emailAddressRepository.save(emailA);
    	phoneNumberRepository.save(phoneNumA);
    	ssnRepository.save(ssnA);
    	accountRepository.save(accountA);
    	
    	// -- Adding test user b --
    	UserEntity userB = new UserEntity("userB", "passB", LocalDate.now());
    	
    	NameEntity nameB = new NameEntity(userB, "John-b", "", "Doe-b");
    	userB.setName(nameB);
    	
    	ProvidedIncomeEntity providedIncomeB = new ProvidedIncomeEntity(userB, 145000);
    	userB.setProvidedIncome(providedIncomeB);
    	
    	BillingAddressEntity billingAddressB = new BillingAddressEntity(userB, "123 Abc Avenue B", "Apt 1",
    			"Chicago", "IL", "60602");
    	userB.setBillingAddress(billingAddressB);
    	
    	EmailAddressEntity emailB = new EmailAddressEntity(userB, "john.doeB@example.com");
    	userB.setEmailAddress(emailB);
    	
    	PhoneNumberEntity phoneNumB = new PhoneNumberEntity(userB, "012-345-6781");
    	userB.setPhoneNumber(phoneNumB);
    	
    	SsnEntity ssnB = new SsnEntity(userB, "123-a");
    	userB.setSsn(ssnB);
    	
    	AccountEntity accountBChecking = new AccountEntity(userB, AccountEntity.Type.CHECKING, 50.0);
    	userB.addAccount(accountBChecking);
    	AccountEntity accountBSavings = new AccountEntity(userB, AccountEntity.Type.SAVINGS, 1000.0);
    	userB.addAccount(accountBSavings);
    	
    	userRepository.save(userB);
    	nameRepository.save(nameB);
    	providedIncomeRepository.save(providedIncomeB);
    	billingAddressRepository.save(billingAddressB);
    	emailAddressRepository.save(emailB);
    	phoneNumberRepository.save(phoneNumB);
    	ssnRepository.save(ssnB);
    	accountRepository.save(accountBChecking);
    	accountRepository.save(accountBSavings);
    	
    	return "Cleared and re-initialized";
    }
}