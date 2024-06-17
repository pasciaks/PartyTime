package com.pasciak.partytime.services;

import com.pasciak.partytime.entities.User;

public interface AuthService {
	
	public User register(User user);
	
	public User getUserByUsername(String username);

}
