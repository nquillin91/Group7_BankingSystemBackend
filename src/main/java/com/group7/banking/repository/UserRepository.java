package com.group7.banking.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	Optional<UserEntity> findByEmailAddress(String email);
	Optional<UserEntity> findByUsername(String username);
}