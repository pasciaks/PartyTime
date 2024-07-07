package com.pasciak.partytime.controllers;

import java.security.Principal;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pasciak.partytime.services.AuthService;
import com.pasciak.partytime.services.EmailService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping("api")
public class SiteController {

	private AuthService authService;
	private EmailService emailService;

	public SiteController(AuthService authService, EmailService emailService) {
		super();
		this.authService = authService;
		this.emailService = emailService;
	}

	@GetMapping("geo/{lat}/{lng}") // api/geo/lat/lng
	public String authenticate(@PathVariable("lat") String lat, @PathVariable("lng") String lng, Principal principal,
			HttpServletResponse res) {
		if (principal == null) { // no Authorization header sent
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 - SC_UNAUTHORIZED
			res.setHeader("WWW-Authenticate", "Basic");
			return "Unauthorized";
		}
		System.out.println(principal.getName());
		res.setStatus(HttpServletResponse.SC_OK);
		System.out.println("lat: " + lat + " lng: " + lng);
		return "lat: " + lat + " lng: " + lng;
	}
}
