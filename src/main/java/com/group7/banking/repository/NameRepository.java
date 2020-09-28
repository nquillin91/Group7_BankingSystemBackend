package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.Name;

@Repository
public interface NameRepository extends CrudRepository<Name, Long> {

  Name findById(long id);
}