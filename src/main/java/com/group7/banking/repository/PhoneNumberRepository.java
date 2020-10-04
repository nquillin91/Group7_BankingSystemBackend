package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.PhoneNumberEntity;

@Repository
public interface PhoneNumberRepository extends CrudRepository<PhoneNumberEntity, Long> {
	
  PhoneNumberEntity findById(long id);
}