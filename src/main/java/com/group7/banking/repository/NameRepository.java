package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.NameEntity;

@Repository
public interface NameRepository extends CrudRepository<NameEntity, Long> {

  NameEntity findById(long id);
}