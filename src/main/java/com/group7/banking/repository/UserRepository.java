package com.group7.banking.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.group7.banking.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
	
  User findById(long id);
}