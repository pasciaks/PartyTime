package com.pasciak.partytime.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pasciak.partytime.entities.EventInvite;
import com.pasciak.partytime.repositories.EventInviteRepository;

@Service
public class EventInviteServiceImpl implements EventInviteService {

	private EventInviteRepository eventInviteRepo;

	public EventInviteServiceImpl(EventInviteRepository eventInviteRepo) {
		super();
		this.eventInviteRepo = eventInviteRepo;
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

}
