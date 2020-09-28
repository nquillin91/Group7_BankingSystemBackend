package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.Account;
import com.group7.banking.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Account, Long> {

  Transaction findById(long id);
}