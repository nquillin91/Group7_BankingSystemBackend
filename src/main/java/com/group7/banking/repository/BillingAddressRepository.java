package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.BillingAddressEntity;

@Repository
public interface BillingAddressRepository extends CrudRepository<BillingAddressEntity, Long> {

  BillingAddressEntity findById(long id);
}