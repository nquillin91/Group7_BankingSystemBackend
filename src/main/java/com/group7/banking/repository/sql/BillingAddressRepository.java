package com.group7.banking.repository.sql;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.sql.BillingAddressEntity;

@Repository
public interface BillingAddressRepository extends CrudRepository<BillingAddressEntity, Long> {
	public Optional<BillingAddressEntity> findByUserId(long userId);
}