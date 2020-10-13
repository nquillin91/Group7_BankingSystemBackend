package com.group7.banking.repository.sql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.sql.SsnEntity;

@Repository
public interface SSNRepository extends CrudRepository<SsnEntity, Long> {
}