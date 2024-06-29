package com.pasciak.partytime.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasciak.partytime.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	Event findById(int id);
	
	List<Event> findAll();
	
	List<Event> findEventsByUserId(long userId);
	
}
