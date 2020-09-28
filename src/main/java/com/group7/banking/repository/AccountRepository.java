package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {
	
  Account findById(long id);
}