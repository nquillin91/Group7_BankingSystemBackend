package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.SsnEntity;

@Repository
public interface SSNRepository extends CrudRepository<SsnEntity, Long> {

  SsnEntity findById(long id);
}