package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.ProvidedIncomeEntity;

@Repository
public interface ProvidedIncomeRepository extends CrudRepository<ProvidedIncomeEntity, Long> {

  ProvidedIncomeEntity findById(long id);
}