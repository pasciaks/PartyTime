package com.pasciak.partytime.services;

import java.util.List;

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

		String encryptedPassword = encoder.encode(user.getPassword());

		user.setPassword(encryptedPassword);

		user.setEnabled(true);

		user.setRole("standard"); // standard // default // student // instructor // admin

		try {
			user = userRepo.save(user); // userRepo.saveAndFlush(user);
		} catch (org.springframework.dao.DataIntegrityViolationException e) {
			System.out.println("Error saving user to database: DataIntegrityViolationException");
			e.printStackTrace();
			return null;
		} catch (Exception e) {
			System.out.println("Error saving user to database: Exception");
			e.printStackTrace();
			return null;
		}

		return user;
	}

	@Override
	public User getUserByUsername(String username) {
		return userRepo.findByUsername(username);
	}

	@Override
	public List<User> index() {
		List<User> users = userRepo.findAll();
		return users;
	}
}
