package com.group7.banking.component;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.group7.banking.dto.AccountDTO;
import com.group7.banking.dto.UserDTO;
import com.group7.banking.dto.UserProfileDTO;
import com.group7.banking.model.nosql.NameEntity;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.model.sql.UserEntity;
import com.group7.banking.repository.nosql.NameRepository;
import com.group7.banking.repository.sql.AccountRepository;

@Component
public class AccountEntityConverter {
	
	@Autowired
	private AccountRepository accountRepository;
	
	public AccountDTO toResponse(AccountEntity receiverAccountEntity) {


		
			AccountDTO accounts = new AccountDTO();
		//	accounts.setAccountType(receiverAccountEntity.getAccountType());;
			accounts.setBalance(receiverAccountEntity.getBalance());
			accounts.setId(receiverAccountEntity.getId());
			accounts.setCreatedDate(receiverAccountEntity.getCreatedDate());
			accounts.setLastUpdatedDate(receiverAccountEntity.getLastUpdatedDate());
	
		
		
		
		return accounts;
    
}
public List<AccountDTO> toResponse(List<AccountEntity> optionalAccount) {

	List <AccountDTO> accountDetails=new ArrayList<AccountDTO>();
	for(AccountEntity accountEntity:optionalAccount)
	{
		AccountDTO accounts = new AccountDTO();
		accounts.setAccountType(accountEntity.getAccountType());;
		accounts.setBalance(accountEntity.getBalance());
		accounts.setId(accountEntity.getId());
		accounts.setCreatedDate(accountEntity.getCreatedDate());
		accounts.setLastUpdatedDate(accountEntity.getLastUpdatedDate());

	
		accountDetails.add(accounts);
	}
	return accountDetails;
}
}