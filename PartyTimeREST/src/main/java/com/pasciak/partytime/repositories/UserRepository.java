package com.pasciak.partytime.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasciak.partytime.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);
	
	User findById(long id);
	
	List<User> findAll();
	
}
