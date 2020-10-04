package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.AccountEntity;
import com.group7.banking.model.TransactionEntity;

@Repository
public interface TransactionRepository extends CrudRepository<AccountEntity, Long> {

  TransactionEntity findById(long id);
}