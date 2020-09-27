package com.group7.banking.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.Account;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

  List<Account> findByLastName(String lastName);

  Account findById(long id);
}