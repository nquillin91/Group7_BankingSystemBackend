package com.group7.banking.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.ConfirmationTokenEntity;

@Repository
public interface ConfirmationTokenRepository extends CrudRepository<ConfirmationTokenEntity, Long> {
	public Optional<ConfirmationTokenEntity> findConfirmationTokenByConfirmationToken(String token);
}