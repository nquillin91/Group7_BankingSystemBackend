package com.group7.banking.service.sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.dto.TransactionDTO;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.model.sql.TransactionEntity;
import com.group7.banking.repository.sql.AccountRepository;
import com.group7.banking.repository.sql.TransactionRepository;

@Service
public class TransactionService {
	Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private AccountRepository accountRepository;
	private TransactionRepository transactionRepository;
	
	/*Verifying Details of both target account and origin account*/ 
	public boolean verifyDetails(AccountEntity accountDetails, TransactionDTO transactionDTO) {

		// TODO:verify origin account by session
		// TODO: beneficiary account details in account entity
		return true;
	}
	
	/*Check minimum balance of verified origin account*/
	public boolean checkBalance(AccountEntity accountDetails, TransactionDTO transactionDTO) {
		boolean checkbal = false;
		checkbal = accountDetails.getBalance() > transactionDTO.getAmount();
		return checkbal;
		
//TODO:Check default balance condition for saving account
	}
	
	/*Initiate and save the transaction*/ 
	public boolean initiateTransaction(AccountEntity accountDetails, TransactionDTO transactionDTO) {
		double originAccountBalance;
		double amountRequested;
		double newAccountBalance;

		originAccountBalance = accountDetails.getBalance();
		amountRequested = transactionDTO.getAmount();
		newAccountBalance = originAccountBalance - amountRequested;
		accountDetails.setBalance(newAccountBalance);
		
		// TODO: set target account balance through beneficiary
		// TODO:Exception Handling
		transactionRepository.save(accountDetails);
		return true;

	}


}