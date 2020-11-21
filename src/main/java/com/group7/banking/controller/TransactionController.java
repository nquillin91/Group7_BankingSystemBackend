package com.group7.banking.controller;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.group7.banking.component.TransactionService;
import com.group7.banking.dto.AccountDTO;
import com.group7.banking.dto.TransactionDTO;
import com.group7.banking.dto.UserProfileDTO;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.model.sql.TransactionEntity;
import com.group7.banking.service.sql.AccountService;
import com.group7.banking.service.sql.UserService;

@RestController("TransactionController")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	@Autowired
	private UserService userService;
	@Autowired
	private AccountService accountService;

	/* Getting all accounts of a particular of the logged in user */

	@GetMapping("/accDetails")
	public List<AccountDTO> getDetailsOfAccount(HttpServletRequest request) throws Exception {
		String principalUser = request.getUserPrincipal().getName();
		UserProfileDTO userDetailsList = null;
		List<AccountEntity> senderAccountEntity = null;
		List<AccountDTO> senderAccountDetailsList = null;

		// Getting user details based on user-name provided in session 
		userDetailsList = userService.getUserProfile(principalUser);
		
		// Getting all the savings and checking account of the logged in user 
		senderAccountEntity = accountService.findByUserId(userDetailsList.getId());
		senderAccountDetailsList = accountService.convertAccountDetails(senderAccountEntity);
		senderAccountDetailsList.get(0).setUser(userDetailsList);
		return senderAccountDetailsList;
	}

	/*Validating the initiating the transaction */
	
	@PostMapping(value = "/transaction", consumes = MediaType.APPLICATION_JSON_VALUE)
	public String transaction(@RequestBody TransactionDTO transactionDTO, HttpServletRequest request) throws Exception {

		boolean checkBal = false;
		boolean transactionInitiated = false;
		UserProfileDTO userDetails = null;
		AccountDTO receiverAccountDetails = null;
		AccountEntity receiverAccountEntity = null;
		List<AccountEntity> senderAccountEntityList = null;
		AccountEntity senderAccountEntity = null;
		AccountDTO senderAccountDetails = null;
		List<Optional<TransactionEntity>> transactionList = null;

		// Getting the sender and receiver account object 
		String principalUser = request.getUserPrincipal().getName();
		userDetails = userService.getUserProfile(principalUser);

		senderAccountEntityList = accountService.findByUserId(userDetails.getId());
		for (AccountEntity senderAccountEntities : senderAccountEntityList) {
			if (senderAccountEntities.getAccountType() == transactionDTO.getAccountType())
				senderAccountEntity = senderAccountEntities;
		}

		senderAccountDetails = accountService.convertAccountDetails(senderAccountEntity);
		senderAccountDetails.setUser(userDetails);
		System.out.println("In Transaction");

		// Verifying Details of  target account 
		
		receiverAccountEntity = accountService.findById(transactionDTO.getReceiverAccountNumber());

		// Check minimum balance of verified origin account 
		checkBal = transactionService.checkBalance(receiverAccountEntity, transactionDTO);

		
		 // Initiate and save the transaction if balance is sufficient
		
		if (checkBal) {
			receiverAccountDetails = accountService.convertAccountDetails(receiverAccountEntity);

			transactionInitiated = transactionService.initiateTransaction(senderAccountEntity, receiverAccountEntity,
					transactionDTO, userDetails);

			transactionService.findByStatus();

		}

		//Return transaction status 

		if (transactionInitiated)

			return "TransactionSuccessfull";
		else
			throw new Exception("Transaction Failure");

	}

	/*Scheduler to process transactions of future date*/
	//Scheduled for every hour
	@Scheduled(cron ="0 0 * ? * *")
	public void findByStatus() throws Exception {

		transactionService.findByStatus();
	}
}