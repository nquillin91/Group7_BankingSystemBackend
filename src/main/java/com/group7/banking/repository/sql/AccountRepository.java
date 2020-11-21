package com.group7.banking.repository.sql;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.dto.AccountDTO;
import com.group7.banking.model.sql.AccountEntity;

@Repository
public interface AccountRepository extends CrudRepository<AccountEntity, Long> {

	List<AccountEntity> findByUserId(Long userId);



}