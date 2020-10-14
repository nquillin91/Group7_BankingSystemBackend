package com.group7.banking.service.sql;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.model.nosql.NameEntity;
import com.group7.banking.model.nosql.PhoneNumberEntity;
import com.group7.banking.model.nosql.ProvidedIncomeEntity;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.model.sql.BillingAddressEntity;
import com.group7.banking.model.nosql.EmailAddressEntity;
import com.group7.banking.model.sql.SsnEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.nosql.NameRepository;
import com.group7.banking.repository.nosql.PhoneNumberRepository;
import com.group7.banking.repository.nosql.ProvidedIncomeRepository;
import com.group7.banking.repository.sql.AccountRepository;
import com.group7.banking.repository.sql.BillingAddressRepository;
import com.group7.banking.repository.nosql.EmailAddressRepository;
import com.group7.banking.repository.sql.SSNRepository;
import com.group7.banking.repository.sql.UserRepository;

@Service
public class MainService {
	Logger logger = LoggerFactory.getLogger(MainService.class);
	
    @Autowired
    private UserRepository userRepository;
   
    @Autowired
    private ProvidedIncomeRepository providedIncomeRepository;
    
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
    	
    	//NameEntity nameA = new NameEntity("John-a", "", "Doe-a");
    	//userA.setName(nameA);
    	
    	ProvidedIncomeEntity providedIncomeA = new ProvidedIncomeEntity( 70000);
    //	userA.setProvidedIncome(providedIncomeA);
    	
//    	BillingAddressEntity billingA = new BillingAddressEntity("123 Abc Avenue A", "Apt 1",
//    			"Chicago", "IL", "60602");
//    	billingA.getUsers().add(userA);
//    	userA.setBillingAddress(billingA);
    	
    	//EmailAddressEntity emailA = new EmailAddressEntity(userA, "john.doeA@example.com");
    	//userA.setEmailAddress(emailA);
    	
    	//PhoneNumberEntity phoneNumA = new PhoneNumberEntity("012-345-6780");
    	//userA.setPhoneNumber(phoneNumA);
    	
    	SsnEntity ssnA = new SsnEntity(userA, "123-a");
    	userA.setSsn(ssnA);
    	
    	AccountEntity accountA = new AccountEntity(userA, AccountEntity.Type.CHECKING, 100.0);
    	userA.addAccount(accountA);
    	
    	userRepository.save(userA);
    	//nameRepository.save(nameA);
    	providedIncomeRepository.save(providedIncomeA);
    	//billingAddressRepository.save(billingA);
    	//emailAddressRepository.save(emailA);
    	//phoneNumberRepository.save(phoneNumA);
    	ssnRepository.save(ssnA);
    	accountRepository.save(accountA);
    	
    	// -- Adding test user b --
    	UserEntity userB = new UserEntity("userB", "passB", LocalDate.now());
    	
    	NameEntity nameB = new NameEntity( "John-b", "", "Doe-b");
    	//userB.setName(nameB);
    	
    	ProvidedIncomeEntity providedIncomeB = new ProvidedIncomeEntity(145000);
    	//userB.setProvidedIncome(providedIncomeB);
    	
    	//BillingAddressEntity billingAddressB = new BillingAddressEntity("123 Abc Avenue B", "Apt 1",
    	//		"Chicago", "IL", "60602");
    	//billingAddressB.getUsers().add(userB);
    	//userB.setBillingAddress(billingAddressB);
    	
    	//EmailAddressEntity emailB = new EmailAddressEntity(userB, "john.doeB@example.com");
    	//userB.setEmailAddress(emailB);
    	
    	//PhoneNumberEntity phoneNumB = new PhoneNumberEntity("012-345-6781");
    	//userB.setPhoneNumber(phoneNumB);
    	
    	SsnEntity ssnB = new SsnEntity(userB, "123-a");
    	userB.setSsn(ssnB);
    	
    	AccountEntity accountBChecking = new AccountEntity(userB, AccountEntity.Type.CHECKING, 50.0);
    	userB.addAccount(accountBChecking);
    	AccountEntity accountBSavings = new AccountEntity(userB, AccountEntity.Type.SAVINGS, 1000.0);
    	userB.addAccount(accountBSavings);
    	
    	userRepository.save(userB);
    	//nameRepository.save(nameB);
    	providedIncomeRepository.save(providedIncomeB);
    	//billingAddressRepository.save(billingAddressB);
    	//emailAddressRepository.save(emailB);
    	//phoneNumberRepository.save(phoneNumB);
    	ssnRepository.save(ssnB);
    	accountRepository.save(accountBChecking);
    	accountRepository.save(accountBSavings);
    	
    	return "Cleared and re-initialized";
    }
}