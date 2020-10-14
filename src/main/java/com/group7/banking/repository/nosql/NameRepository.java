package com.group7.banking.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.nosql.NameEntity;

@Repository
public interface NameRepository extends MongoRepository<NameEntity, String> {
	
	@Query("{user_id: ?0}")
	public NameEntity findByUserId(Long userId);
}