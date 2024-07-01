package com.pasciak.partytime.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pasciak.partytime.entities.Event;

@Service
public interface EventService {

	Event show(long eventId);

	Event create(Event event);

	Event update(long eventId, Event event);

	void delete(long eventId);

	List<Event> index();

	List<Event> findEventsByUserId(long userId);

}
