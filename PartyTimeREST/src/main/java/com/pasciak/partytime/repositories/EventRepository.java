package com.pasciak.partytime.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasciak.partytime.entities.Event;

public interface EventRepository extends JpaRepository<Event, Long> {

	Optional<Event> findById(long id);

	List<Event> findAll();

	List<Event> findEventsByUserIdOrderByIdDesc(long userId);

}
