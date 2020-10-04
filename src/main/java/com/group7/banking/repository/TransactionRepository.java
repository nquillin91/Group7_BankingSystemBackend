package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.AccountEntity;

@Repository
public interface TransactionRepository extends CrudRepository<AccountEntity, Long> {
}