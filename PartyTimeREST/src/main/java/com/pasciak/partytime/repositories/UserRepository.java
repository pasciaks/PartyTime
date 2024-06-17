package com.pasciak.partytime.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasciak.partytime.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);
	
	User findById(int id);
	
	List<User> findAll();
	
}
