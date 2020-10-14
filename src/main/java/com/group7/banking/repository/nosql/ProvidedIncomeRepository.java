package com.group7.banking.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.nosql.ProvidedIncomeEntity;

@Repository
public interface ProvidedIncomeRepository extends MongoRepository<ProvidedIncomeEntity, Long> {

	@Query("{user_id: ?0}")
	public ProvidedIncomeEntity findByUserId(Long userId);
}