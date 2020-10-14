package com.group7.banking.repository.nosql;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.group7.banking.model.nosql.EmailAddressEntity;


public interface EmailAddressRepository_nsql extends MongoRepository<EmailAddressEntity, String> {
	
	@Query("{emailaddress:'?0'}")
	public List<EmailAddressEntity> findConfirmationTokenByConfirmationToken(String token);
}