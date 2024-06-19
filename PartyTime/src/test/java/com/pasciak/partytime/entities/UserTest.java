package com.pasciak.partytime.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class UserTest {

	private static EntityManagerFactory emf;

	private EntityManager em;

	private User user = null;

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
		user = em.find(User.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
		user = null;
	}
	
	@Test
	void test_entity_mapping() {
		assertNotNull(user);
		assertEquals(1, user.getId());
		System.out.println(user.getUsername());
		System.out.println(user.getPassword());
		System.out.println(user.getEnabled());
		System.out.println(user.getCreatedAt());
		System.out.println(user.getRole());
		System.out.println(user.getFirstName());
		System.out.println(user.getLastName());
		System.out.println(user.getUpdatedAt());
	}
	
	@Test
	void test_user_events_entity_mapping() {
		assertNotNull(user);
		assertEquals(1, user.getId());
		System.out.println(user.getEvents().size());
		
		List<Event> events = user.getEvents();
		events.forEach(event -> {
			System.out.println(event.getLat());
			System.out.println(event.getLng());
			System.out.println(event.getDateTime());
		});
	}
	
	@Test
	void test_user_has_event_invites_entity_mapping() {
		assertNotNull(user);
		assertEquals(1, user.getId());
		System.out.println("User has event invites:");
		System.out.println(user.getEventInvites().size());
	}

}
