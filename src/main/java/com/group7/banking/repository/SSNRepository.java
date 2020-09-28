package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.SSN;

@Repository
public interface SSNRepository extends CrudRepository<SSN, Long> {

  SSN findById(long id);
}