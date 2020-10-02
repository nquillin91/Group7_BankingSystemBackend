package com.group7.banking.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	Optional<User> findById(long id);
	Optional<User> findByEmailAddress(String email);
	Optional<User> findByUsername(String username);
}