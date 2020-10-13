package com.group7.banking.repository.nosql;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.group7.banking.model.nosql.ConfirmationTokenEntity;

public interface ConfirmationTokenRepository extends MongoRepository<ConfirmationTokenEntity, String> {
	
	@Query("{confirmationToken:'?0'}")
	public List<ConfirmationTokenEntity> findConfirmationTokenByConfirmationToken(String token);
}