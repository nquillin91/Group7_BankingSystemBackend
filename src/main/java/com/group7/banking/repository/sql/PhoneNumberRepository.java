package com.group7.banking.repository.sql;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.sql.PhoneNumberEntity;

@Repository
public interface PhoneNumberRepository extends CrudRepository<PhoneNumberEntity, Long> {
}