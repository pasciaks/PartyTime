package com.pasciak.partytime.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.pasciak.partytime.entities.Event;
import com.pasciak.partytime.entities.EventInvite;
import com.pasciak.partytime.entities.User;
import com.pasciak.partytime.services.AuthService;
import com.pasciak.partytime.services.EmailService;
import com.pasciak.partytime.services.EventInviteService;
import com.pasciak.partytime.services.EventService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping("api")
public class EventInvitesController {

	private AuthService authService;
	private EventInviteService eventInviteService;
	private EventService eventService;
	private EmailService emailService;

	public EventInvitesController(AuthService authService, EventInviteService eventInviteService,
			EventService eventService, EmailService emailService) {
		super();
		this.authService = authService;
		this.eventInviteService = eventInviteService;
		this.eventService = eventService;
		this.emailService = emailService;
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

		List<EventInvite> eventInvites = eventInviteService.findEventInvitesByUserId(requestingUser.getId());

		return eventInvites;

	}

	@PostMapping("event-invites")
	public EventInvite createEventInvite(@RequestBody JsonNode requestBody, Principal principal,
			HttpServletResponse response) {

		long userId = 0;
		long eventId = 0;

		String email = "";

		// Extract key-value pairs from the request body

		try {
			eventId = requestBody.get("eventId").asLong();
		} catch (Exception e) {
			e.printStackTrace();
			// must have a valid eventId
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		try {
			userId = requestBody.get("userId").asLong();
			// attempt to get invited userId from request body
		} catch (Exception e) {
			//
		}

		try {

			email = requestBody.get("email").asText();

			// if an email is provided, get the userId from the email in user table

			User invitedUser = authService.getUserByUsername(email);
			if (invitedUser == null) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403 - SC_FORBIDDEN
				return null;
			}

			userId = invitedUser.getId();

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			return null;
		}

		System.out.println(eventId);
		System.out.println(email);
		System.out.println(userId);

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

		Event foundEvent = eventService.show(eventId);

		if (foundEvent == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		if (foundEvent.getUser().getId() != requestingUser.getId()) {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}

		EventInvite createdEventInvite = eventInviteService.create(userId, eventId);

		String to = email;
		String subject = "You've been invited to an event!";
		String theBody = "You've been invited to an event. Check it out at: http://localhost:4200/#/events/"
				+ foundEvent.getId();

		emailService.sendEmail(to, subject, theBody);

		return createdEventInvite;

	}

}
