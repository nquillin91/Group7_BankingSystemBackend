package com.group7.banking.repository.nosql;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.nosql.ProvidedIncomeEntity;



@Repository
public interface ProvidedIncomeRepository extends MongoRepository<ProvidedIncomeEntity, Long> {
}