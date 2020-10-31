package com.group7.banking.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import com.group7.banking.dto.TransactionDTO;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.service.sql.AccountService;
import com.group7.banking.service.sql.TransactionService;
@RestController("TransactionController")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private AccountService accountService;
	@CrossOrigin(origins="http://localhost:4200")  
	@GetMapping(value = "/transaction", produces = MediaType.APPLICATION_JSON_VALUE)
	public String transaction(@RequestBody TransactionDTO transactionDTO) throws Exception {
		AccountEntity accountDetails = null;
		boolean isVerified;
		boolean checkBal = false;
		boolean transactionInitiated = false;


		/* Getting the account object */

		System.out.println("In Transaction");
		/*Getting the account object*/ 

		accountDetails = accountService.findById(transactionDTO.getTargetAccountDetails().getId());

		/* Verifying Details of both target account and origin account */
		isVerified = transactionService.verifyDetails(accountDetails, transactionDTO);

		/* Check minimum balance of verified origin account */
		if (isVerified)
			checkBal = transactionService.checkBalance(accountDetails, transactionDTO);
		else
			return "Unverified Account";

		/* Initiate and save the transaction */
		if (checkBal)
			transactionInitiated = transactionService.initiateTransaction(accountDetails, transactionDTO);
		else
			return "InsufficientBalance";

		/* Return transaction status */

		if (transactionInitiated)

			return "TransactionSuccessfull";

		else
			return "TransactionFailure";
		// TODO:exception handling
	}
}