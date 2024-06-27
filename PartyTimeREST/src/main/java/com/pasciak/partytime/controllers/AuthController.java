package com.pasciak.partytime.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.pasciak.partytime.entities.User;
import com.pasciak.partytime.services.AuthService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
public class AuthController {

	private AuthService authService;

	public AuthController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("register")
	public User register(@RequestBody User user, HttpServletResponse res) {
	  if (user == null || user.getUsername() == null || user.getPassword() == null || user.getUsername().equals("") || user.getPassword().equals("")) {
	     res.setStatus(400);
	     return null;
	  }
	  
	  User alreadyExists = authService.getUserByUsername(user.getUsername());
		if (alreadyExists != null) {
			res.setStatus(HttpServletResponse.SC_CONFLICT); // 409 - SC_CONFLICT
			return null;
		}
		
	  user = authService.register(user);
	  
	  return user;
	}
	 
	@GetMapping("authenticate")
	public User authenticate(Principal principal, HttpServletResponse res) {
	  if (principal == null) { // no Authorization header sent
	     res.setStatus(401);
	     res.setHeader("WWW-Authenticate", "Basic");
	     return null;
	  }
	  return authService.getUserByUsername(principal.getName());
	}
}
