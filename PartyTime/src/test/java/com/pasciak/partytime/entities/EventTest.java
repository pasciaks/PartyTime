package com.pasciak.partytime.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class EventTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private Event event = null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("PartyTime");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		event = em.find(Event.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		event = null;
	}
	
	@Test
	void test_entity_mapping() {
		assertNotNull(event);
		assertEquals(1, event.getId());
		System.out.println(event.getLat());
		System.out.println(event.getLng());
		System.out.println(event.getDateTime());
	}
	
	@Test
	void test_event_has_user_mapping() {
		assertNotNull(event);
		assertEquals(1, event.getId());
		assertNotNull(event.getUser());
		System.out.println(event.getUser().getId());
		System.out.println(event.getUser().getUsername());
	}
	
	@Test
	void test_event_has_event_invites_entity_mapping() {
		assertNotNull(event);
		assertEquals(1, event.getId());
		System.out.println("Event has event invites:");
		System.out.println(event.getEventInvites().size());
	}

}
