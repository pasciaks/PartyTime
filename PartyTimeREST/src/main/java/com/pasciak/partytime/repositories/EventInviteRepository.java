package com.pasciak.partytime.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pasciak.partytime.entities.EventInvite;

public interface EventInviteRepository extends JpaRepository<EventInvite, Long> {

	Optional<EventInvite> findById(long id);

	List<EventInvite> findAll();

	List<EventInvite> findEventInvitesByUserId(long userId);

}
