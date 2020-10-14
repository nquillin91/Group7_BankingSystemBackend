package com.group7.banking.repository.nosql;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.group7.banking.model.nosql.NameEntity;


public interface NameRepository_nsql extends MongoRepository<NameEntity, String> {
	
	@Query("{name:'?0'}")
	public List<NameEntity> findConfirmationTokenByConfirmationToken(String token);
}