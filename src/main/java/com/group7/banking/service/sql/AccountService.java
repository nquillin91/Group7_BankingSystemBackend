package com.group7.banking.service.sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.group7.banking.component.AccountEntityConverter;
import com.group7.banking.dto.AccountDTO;
import com.group7.banking.model.sql.AccountEntity;
import com.group7.banking.repository.sql.AccountRepository;

@Service
public class AccountService {
	Logger logger = LoggerFactory.getLogger(AccountService.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private AccountEntityConverter accountEntityConverter;

	public AccountEntity findById(long id) throws Exception {
		Optional<AccountEntity> optionalAccount = accountRepository.findById(id);

		if (!optionalAccount.isPresent()) {
			throw new Exception("No account Details for id:" + id);
		}

		return optionalAccount.get();
	}

	public List<AccountEntity> findByUserId(Long userId) throws Exception {
		List<AccountEntity> optionalAccount = accountRepository.findByUserId(userId);

		if (optionalAccount.isEmpty()) {
			throw new Exception("No account Details for id:" + userId);
		}
		return optionalAccount;

	}


	public Optional<AccountEntity> findById(Long accountId) throws Exception {
		Optional<AccountEntity> optionalAccount = accountRepository.findById(accountId);

		if (optionalAccount.isPresent()) {
			throw new Exception("No account Details for id:" + accountId);
		}
		return optionalAccount;

	}

	public List<AccountEntity> findAll() {

		Iterable<AccountEntity> it = accountRepository.findAll();

		ArrayList<AccountEntity> users = new ArrayList<AccountEntity>();
		it.forEach(e -> {
			users.add(e);
		});

		return users;
	}

	public Long count() {

		return accountRepository.count();
	}

	public void deleteById(Long userId) {

		accountRepository.deleteById(userId);
	}

	public List<AccountDTO> convertAccountDetails(List<AccountEntity> senderAccountEntity) {
		return accountEntityConverter.toResponse(senderAccountEntity);

	}

	public AccountDTO convertAccountDetails(AccountEntity receiverAccountEntity) {
		return accountEntityConverter.toResponse(receiverAccountEntity);

	}

}