package com.group7.banking.repository.sql;

import java.util.List;
import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.sql.TransactionEntity.Status;
import com.group7.banking.model.sql.TransactionEntity;

@Repository



public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

	List<TransactionEntity> findByStatus(int i);



}