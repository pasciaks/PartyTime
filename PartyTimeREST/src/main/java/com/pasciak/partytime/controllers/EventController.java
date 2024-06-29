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
	
	@GetMapping("events/{id}")
	public Event show(@PathVariable("id") int id, HttpServletResponse response, HttpServletRequest request) {

		Event event = null;

		try {
			event = eventService.show(id);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}

		if (event == null) {
			response.setStatus(404);
		}

		return event;

	}
	
	@PostMapping("events")
	public Event create(@RequestBody Event event, HttpServletResponse response, HttpServletRequest request) {

		Event createdEvent = null;

		try {
			createdEvent = eventService.create(event);
			response.setStatus(HttpServletResponse.SC_CREATED); // 201 - SC_CREATED
			StringBuffer url = request.getRequestURL();
			url.append("/").append(createdEvent.getId());
			response.setHeader("Location", url.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST); // 400 - SC_BAD_REQUEST	
		}

		if (createdEvent == null) {
			response.setStatus(400);
		}

		return createdEvent;

	}

	@DeleteMapping("events/{eventId}")
	public void delete(@PathVariable("eventId") long eventId, HttpServletResponse response) {

		try {
			eventService.delete(eventId);
			response.setStatus(HttpServletResponse.SC_NO_CONTENT); // 204 - SC_NO_CONTENT
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(404);
		}

	}

	@PutMapping("events/{eventId}")
	public Event update(@PathVariable("eventId") int eventId, @RequestBody Event event, HttpServletResponse response) {

		Event updatedEvent = null;

		try {
			updatedEvent = eventService.update(eventId, event);
			response.setStatus(200);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(400);
		}

		return updatedEvent;

	}
	
	@GetMapping("events")
	public List<Event> events(Principal principal, HttpServletResponse res) {

		if (principal == null) { // no Authorization header sent
			res.setStatus(401);
			res.setHeader("WWW-Authenticate", "Basic");
			return null;
		}

		User foundUser = authService.getUserByUsername(principal.getName());

		if (foundUser == null) {
			res.setStatus(HttpServletResponse.SC_NOT_FOUND); // 404 - SC_NOT_FOUND
			return null;
		} else {
			res.setStatus(HttpServletResponse.SC_OK); // 200 - SC_OK
		}
		
		List<Event> events = eventService.findEventsByUserId(foundUser.getId());

		return events;

	}
}
