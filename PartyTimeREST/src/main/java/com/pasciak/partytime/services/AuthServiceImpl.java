package com.pasciak.partytime.services;
 

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pasciak.partytime.entities.User;
import com.pasciak.partytime.repositories.UserRepository;


@Service
public class AuthServiceImpl implements AuthService {
	
	private PasswordEncoder encoder;
	private UserRepository userRepo;
	
	public AuthServiceImpl(PasswordEncoder encoder, UserRepository userRepo) {
		super();
		this.encoder = encoder;
		this.userRepo = userRepo;
	}

	@Override
	public User register(User user) {
		
		// encrypt and set password for user
		String encryptedPassword = encoder.encode(user.getPassword());
		
		user.setPassword(encryptedPassword);
		
		//other business logic related to registering a new account
		user.setEnabled(true);
		
		user.setRole("standard"); // standard // default // student // instructor // admin

		//persist new user to db
		
		try {
			user = userRepo.save(user);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			System.out.println("Error saving user to database: DataIntegrityViolationException");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("Error saving user to database: Exception");
			e.printStackTrace();
			return null;
		}
		
		// userRepo.saveAndFlush(user);
		
		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}
}
