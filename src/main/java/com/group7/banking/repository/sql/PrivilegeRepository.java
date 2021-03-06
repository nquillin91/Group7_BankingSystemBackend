package com.group7.banking.repository.sql;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.sql.PrivilegeEntity;

@Repository
public interface PrivilegeRepository extends CrudRepository<PrivilegeEntity, Long> {
	public Optional<PrivilegeEntity> findByName(String name);
}