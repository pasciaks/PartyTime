package com.pasciak.partytime.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class EventInviteTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private EventInvite eventInvite = null;

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
		eventInvite = em.find(EventInvite.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		eventInvite = null;
	}
	
	@Test
	void test_entity_mapping() {
		assertNotNull(eventInvite);
		assertEquals(1, eventInvite.getId());
		System.out.println("event invite id:" + eventInvite.getId());
		System.out.println("event invite comment:" + eventInvite.getComment());
		System.out.println("event invite attending:" + eventInvite.getAttending());
	}
	
	@Test
	void test_event_invite_has_user_entity_mapping() {
		assertNotNull(eventInvite);
		assertEquals(1, eventInvite.getUser().getId());
		System.out.println(eventInvite.getUser().toString());
	}
	
	@Test
	void test_event_invite_has_event_entity_mapping() {
		assertNotNull(eventInvite);
		assertEquals(1, eventInvite.getEvent().getId());
		System.out.println(eventInvite.getEvent().toString());
	}

}
