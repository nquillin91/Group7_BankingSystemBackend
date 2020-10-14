package com.group7.banking.repository.nosql;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.nosql.BillingAddressEntity;

@Repository
public interface BillingAddressRepository_nsql extends MongoRepository<BillingAddressEntity, String> {
	
	@Query("{userId: ?0}")
	public BillingAddressEntity findByUserId(Long userId);
}