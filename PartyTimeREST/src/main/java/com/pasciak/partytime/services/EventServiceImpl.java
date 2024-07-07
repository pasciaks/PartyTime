package com.pasciak.partytime.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.pasciak.partytime.entities.Event;
import com.pasciak.partytime.repositories.EventRepository;

@Service
public class EventServiceImpl implements EventService {

	private EventRepository eventRepo;

	public EventServiceImpl(EventRepository eventRepo) {
		super();
		this.eventRepo = eventRepo;
	}

	@Override
	public Event show(long eventId) {

		Optional<Event> event = null;

		event = eventRepo.findById(eventId);

		if (event.isPresent()) {
			return event.get();
		} else {
			return null;
		}

	}

	@Override
	public Event create(Event event) {

		Event createdEvent = null;

		try {
			createdEvent = eventRepo.saveAndFlush(event);
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}

		return createdEvent;
	}

	@Override
	public Event update(long eventId, Event event) {

		Optional<Event> eventOpt = eventRepo.findById(eventId);

		Event managedEvent = null;

		if (eventOpt.isPresent()) {
			managedEvent = eventOpt.get();

			// NOTE: Update normal entity fields
			managedEvent.setLat(event.getLat());
			managedEvent.setLng(event.getLng());
			managedEvent.setDateTime(event.getDateTime());

			// NOTE: Update the user if one is provided
			if (event.getUser() != null) {
				managedEvent.setUser(event.getUser());
			}

			if (event.getEventInvites() != null) {
				event.getEventInvites().forEach(invite -> {
					// NOTE: Updates do not currently support adding new invites
					System.out.println("Invite: " + invite);
				});
			}

			eventRepo.saveAndFlush(managedEvent);
		}

		return managedEvent;

	}

	@Override
	public void delete(long eventId) {

		try {
			if (eventRepo.existsById(eventId)) {
				eventRepo.deleteById(eventId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Override
	public List<Event> index() {
		return eventRepo.findAll();
	}

	@Override
	public List<Event> findEventsByUserId(long userId) {
		List<Event> foundEvents = eventRepo.findEventsByUserIdOrderByIdDesc(userId);
		return foundEvents;
	}

}
