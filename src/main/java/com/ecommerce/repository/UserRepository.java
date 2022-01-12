package com.ecommerce.repository;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
	public User findByEmail(String email);
	
}
