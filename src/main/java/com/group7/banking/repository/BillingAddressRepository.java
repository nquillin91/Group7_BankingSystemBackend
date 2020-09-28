package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.BillingAddress;

@Repository
public interface BillingAddressRepository extends CrudRepository<BillingAddress, Long> {

  BillingAddress findById(long id);
}