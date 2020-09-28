package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.EmailAddress;

@Repository
public interface EmailAddressRepository extends CrudRepository<EmailAddress, Long> {
	
  EmailAddress findById(long id);
}