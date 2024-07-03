package com.pasciak.partytime.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pasciak.partytime.entities.EventInvite;
import com.pasciak.partytime.entities.User;
import com.pasciak.partytime.services.AuthService;
import com.pasciak.partytime.services.EventInviteService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping("api")
public class EventInvitesController {

	private AuthService authService;
	private EventInviteService eventInviteService;

	public EventInvitesController(AuthService authService, EventInviteService eventInviteService) {
		super();
		this.authService = authService;
		this.eventInviteService = eventInviteService;
	}

	// TODO: Re-look to consider use of 200, 201, 204, 400, 401, 403, 404, 500

	private boolean isAuthorized(Principal principal, HttpServletResponse response) {
		if (principal == null) { // no Authorization header sent
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401 - SC_UNAUTHORIZED
			response.setHeader("WWW-Authenticate", "Basic");
			return false;
		}
		return true;
	}

	private boolean isAdmin(Principal principal, HttpServletResponse response) {
		User foundUser = authService.getUserByUsername(principal.getName());
		if (foundUser == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 - SC_FORBIDDEN
			return false;
		}
		if (!foundUser.getRole().equals("admin")) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 - SC_FORBIDDEN
			return false;
		}
		return true;
	}

	private User loggedInUser(Principal principal, HttpServletResponse response) {
		User foundUser = authService.getUserByUsername(principal.getName());
		if (foundUser == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 - SC_FORBIDDEN
			return null;
		}
		return foundUser;
	}

	@GetMapping("event-invites")
	public List<EventInvite> eventInvites(Principal principal, HttpServletResponse response) {

		if (!isAuthorized(principal, response)) {
			return null;
		}

		if (isAdmin(principal, response)) {
			List<EventInvite> eventInvites = eventInviteService.index();
			response.setStatus(HttpServletResponse.SC_OK);
			return eventInvites;
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}

	}

	@GetMapping("event-invites/myeventinvites")
	public List<EventInvite> myEventInvites(Principal principal, HttpServletResponse response) {

		if (!isAuthorized(principal, response)) {
			return null;
		}

		User requestingUser = loggedInUser(principal, response);

		if (requestingUser == null) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		} else {
			response.setStatus(HttpServletResponse.SC_OK);
		}

		List<EventInvite> events = eventInviteService.findEventInvitesByUserId(requestingUser.getId());

		return events;

	}
}
