package com.group7.banking.repository.nosql;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.nosql.EmailAddressEntity;

@Repository
public interface EmailAddressRepository extends MongoRepository<EmailAddressEntity, String> {
	
	@Query("{user_id: ?0}")
	public List<EmailAddressEntity> findByUserId(Long userId);
}