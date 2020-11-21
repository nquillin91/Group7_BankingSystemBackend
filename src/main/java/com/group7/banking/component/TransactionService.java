package com.group7.banking.component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.dto.TransactionDTO;
import com.group7.banking.dto.UserProfileDTO;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.model.sql.TransactionEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.sql.AccountRepository;
import com.group7.banking.repository.sql.TransactionRepository;

@Service
public class TransactionService {
	Logger logger = LoggerFactory.getLogger(TransactionService.class);

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private UserProfileConverter userProfileConverter;

	public UserProfileDTO getUserDetails(Optional<UserEntity> userDetailsEntity) {
		UserProfileDTO userDetails = null;
		userDetails = userProfileConverter.toResponse(userDetailsEntity.get());
		return userDetails;

	}

	/* Check minimum balance of verified origin account */
	
	public boolean checkBalance(AccountEntity receiverAccountEntity, TransactionDTO transactionDTO) throws Exception {
		boolean checkbal = false;
		checkbal = transactionDTO.getAccountBalance() > transactionDTO.getTransferAmount();
		if (!(checkbal))
			throw new Exception("Insufficient balance");
		return checkbal;

	}

	/* Initiate and save the transaction */
	
	public boolean initiateTransaction(AccountEntity senderAccountEntity, AccountEntity receiverAccountEntity,
			TransactionDTO transactionDTO, UserProfileDTO userDetails) {
		double originAccBal;
		double amountReq;
		double updatedAccBalForSender;
		double receiverAccBalance;
		double updatedreceiverAccBal;

		//Deducting balance from original account and adding it to the target account	
		
		originAccBal = transactionDTO.getAccountBalance();
		receiverAccBalance = receiverAccountEntity.getBalance();
		amountReq = transactionDTO.getTransferAmount();
		
		//Saving the transaction
		
		TransactionEntity transactionEntity = new TransactionEntity(senderAccountEntity, receiverAccountEntity,"DEPOSIT", transactionDTO.getTransferAmount(), transactionDTO.getRemarks(), transactionDTO.getDate());
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String transactionDate = format.format(transactionDTO.getDate());

		Date date = new Date();
		String localDate = format.format(date);

		//Comparing today's date with the date of transaction specified by the user. 
		//If the specified date is future date then just save the transaction and the scheduler will run for future transactions
		//If today's date is specified by the user then the transaction will be done immediately
		
		if (transactionDate.compareTo(localDate) == 0) {
			updatedAccBalForSender = originAccBal - amountReq;
			updatedreceiverAccBal = receiverAccBalance + amountReq;
			senderAccountEntity.setBalance(updatedAccBalForSender);
			receiverAccountEntity.setBalance(updatedreceiverAccBal);

			transactionEntity.setAmount(transactionDTO.getTransferAmount());
			transactionEntity.setComments(transactionDTO.getRemarks());
			transactionEntity.setCreatedDate(transactionDTO.getDate());
			transactionEntity.setTargetAccount(receiverAccountEntity);
			transactionEntity.setOriginAccount(senderAccountEntity);
			transactionEntity.setStatus(1);
			accountRepository.save(senderAccountEntity);
			accountRepository.save(receiverAccountEntity);

			transactionRepository.save(transactionEntity);

		} else {
			transactionEntity.setStatus(0);
			transactionRepository.save(transactionEntity);
		}
		return true;
	}

	/*Scheduler to run for future dates*/
	
	//Depending on the status the future transactions are executed. If status is 0 that means the transaction has not been executed.
	//The scheduler picks the transaction based on the status. Then it compares if the sys date is equivalent to transaction date, it will get executed. 

	public void findByStatus() throws Exception {
		double originAccBal;
		double amountReq;
		double updatedAccBalForSender;
		double receiverAccBalance;
		double updatedreceiverAccBal;
		Optional<AccountEntity> senderAccountEntity;
		Optional<AccountEntity> receiverAccountEntity;
		long senderAccountId;
		long receiverAccountId;
		
		List<TransactionEntity> transactionList = transactionRepository.findByStatus(0);
		for (TransactionEntity transactionEntity : transactionList) {
			DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			String transactionDate = format.format(transactionEntity.getCreatedDate());
			Date date = new Date();
			String localDate = format.format(date);
			
			//Testing with default future date
			
			// String localDate="2020-11-24";
			if (transactionDate.compareTo(localDate) == 0) {

				senderAccountId = transactionEntity.getOriginAccount().getId();
				senderAccountEntity = accountRepository.findById(senderAccountId);
				receiverAccountId = transactionEntity.getTargetAccount().getId();
				receiverAccountEntity = accountRepository.findById(receiverAccountId);
				originAccBal = senderAccountEntity.get().getBalance();
				receiverAccBalance = receiverAccountEntity.get().getBalance();
				amountReq = transactionEntity.getAmount();
				updatedAccBalForSender = originAccBal - amountReq;
				updatedreceiverAccBal = receiverAccBalance + amountReq;
				senderAccountEntity.get().setBalance(updatedAccBalForSender);
				receiverAccountEntity.get().setBalance(updatedreceiverAccBal);

				transactionEntity.setLastUpdatedDate(LocalDateTime.now());
				transactionEntity.setStatus(1);
				
				
				accountRepository.save(senderAccountEntity.get());
				accountRepository.save(receiverAccountEntity.get());

				transactionRepository.save(transactionEntity);
			}
		}


	}

}