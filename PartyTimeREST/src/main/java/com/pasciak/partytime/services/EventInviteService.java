package com.pasciak.partytime.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pasciak.partytime.entities.EventInvite;

@Service
public interface EventInviteService {

	List<EventInvite> index();

	List<EventInvite> findEventInvitesByUserId(long userId);

}
