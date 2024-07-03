package com.pasciak.partytime.services;

import java.util.List;

import com.pasciak.partytime.entities.User;

public interface AuthService {

	public User register(User user);

	public User getUserByUsername(String username);

	public List<User> index();

}
