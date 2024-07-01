package com.pasciak.partytime.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pasciak.partytime.entities.Event;
import com.pasciak.partytime.entities.User;
import com.pasciak.partytime.services.AuthService;
import com.pasciak.partytime.services.EventService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
@RequestMapping("api")
public class EventController {

	private AuthService authService;
	private EventService eventService;

	public EventController(AuthService authService, EventService eventService) {
		super();
		this.authService = authService;
		this.eventService = eventService;
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

	@GetMapping("events/{id}")
	public Event show(@PathVariable("id") int id, Principal principal, HttpServletResponse response,
			HttpServletRequest request) {

		if (!isAuthorized(principal, response)) {
			return null;
		}

		Event event = null;

		try {
			event = eventService.show(id);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		if (event == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		User requestingUser = loggedInUser(principal, response);

		if (isAdmin(principal, response)) {
			response.setStatus(HttpServletResponse.SC_OK);
			return event;
		}

		if (requestingUser.getId() == event.getUser().getId()) {
			response.setStatus(HttpServletResponse.SC_OK);
			return event;
		}

		response.setStatus(HttpServletResponse.SC_FORBIDDEN);
		return null;

	}

	@PostMapping("events")
	public Event create(@RequestBody Event event, Principal principal, HttpServletResponse response,
			HttpServletRequest request) {

		if (!isAuthorized(principal, response)) {
			return null;
		}

		User requestingUser = loggedInUser(principal, response);

		Event createdEvent = null;

		event.setUser(requestingUser);

		try {
			createdEvent = eventService.create(event);
			response.setStatus(HttpServletResponse.SC_CREATED);
			StringBuffer url = request.getRequestURL();
			url.append("/").append(createdEvent.getId());
			response.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		if (createdEvent == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		return createdEvent;

	}

	@DeleteMapping("events/{eventId}")
	public void delete(@PathVariable("eventId") long eventId, Principal principal, HttpServletResponse response) {

		if (!isAuthorized(principal, response)) {
			return;
		}

		User requestingUser = loggedInUser(principal, response);

		Event foundEvent = eventService.show(eventId);

		if (foundEvent == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		if (!isAdmin(principal, response)) {
			if (!requestingUser.getId().equals(foundEvent.getUser().getId())) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			}
		}

		try {
			eventService.delete(eventId);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		return;

	}

	@PutMapping("events/{eventId}")
	public Event update(@PathVariable("eventId") int eventId, @RequestBody Event event, Principal principal,
			HttpServletResponse response) {

		Event updatedEvent = null;

		if (!isAuthorized(principal, response)) {
			return null;
		}

		Event requestedEvent = eventService.show(eventId);

		if (requestedEvent == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			return null;
		}

		User requestingUser = loggedInUser(principal, response);

		if (!isAdmin(principal, response)) {

			if (!requestingUser.getId().equals(requestedEvent.getUser().getId())) {
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return null;
			}
		}

		try {
			updatedEvent = eventService.update(eventId, event);
			response.setStatus(HttpServletResponse.SC_OK);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}

		return updatedEvent;

	}

	@GetMapping("events")
	public List<Event> events(Principal principal, HttpServletResponse response) {

		if (!isAuthorized(principal, response)) {
			return null;
		}

		if (isAdmin(principal, response)) {
			List<Event> events = eventService.index();
			response.setStatus(HttpServletResponse.SC_OK);
			return events;
		} else {
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			return null;
		}

	}

	@GetMapping("events/myevents")
	public List<Event> myEvents(Principal principal, HttpServletResponse response) {

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

		List<Event> events = eventService.findEventsByUserId(requestingUser.getId());

		return events;

	}
}
