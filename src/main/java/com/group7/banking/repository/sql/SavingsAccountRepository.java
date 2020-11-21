package com.group7.banking.repository.sql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.sql.SavingsAccountEntity;

@Repository
public class SavingsAccountRepository extends CrudRepository<SavingsAccountEntity, Long> {

}
