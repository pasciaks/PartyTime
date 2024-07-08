package com.pasciak.partytime.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pasciak.partytime.entities.Event;
import com.pasciak.partytime.entities.EventInvite;
import com.pasciak.partytime.entities.User;
import com.pasciak.partytime.repositories.EventInviteRepository;
import com.pasciak.partytime.repositories.EventRepository;
import com.pasciak.partytime.repositories.UserRepository;

@Service
public class EventInviteServiceImpl implements EventInviteService {

	private EventInviteRepository eventInviteRepo;
	private EventRepository eventRepo;
	private UserRepository userRepo;

	public EventInviteServiceImpl(EventInviteRepository eventInviteRepo, EventRepository eventRepo,
			UserRepository userRepo) {
		super();
		this.eventInviteRepo = eventInviteRepo;
		this.eventRepo = eventRepo;
		this.userRepo = userRepo;
	}

	@Override
	public List<EventInvite> index() {
		return eventInviteRepo.findAll();
	}

	@Override
	public List<EventInvite> findEventInvitesByUserId(long userId) {
		List<EventInvite> foundEventInvites = eventInviteRepo.findEventInvitesByUserId(userId);
		return foundEventInvites;
	}

	@Override
	public List<EventInvite> findEventInvitesByEventId(long eventId) {
		List<EventInvite> foundEventInvites = eventInviteRepo.findEventInvitesByEventId(eventId);
		return foundEventInvites;
	}

	@Override
	public EventInvite create(long userId, long eventId) {
		EventInvite createdEventInvite = new EventInvite();

		Event foundEvent = eventRepo.findById(eventId).get();

		createdEventInvite.setEvent(foundEvent);

		User foundUser = userRepo.findById(userId);

		createdEventInvite.setUser(foundUser);

		createdEventInvite = eventInviteRepo.saveAndFlush(createdEventInvite);

		return createdEventInvite;
	}

}
