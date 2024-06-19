package com.pasciak.partytime.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIncludeProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Event {
	
	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name="lat")
	private Double lat;
	
	@Column(name="lng")
	private Double lng;
	
	@Column(name="datetime")
	private LocalDateTime dateTime;

	@JoinColumn(name = "user_id")
	@ManyToOne
	private User user;
	
	public List<EventInvite> getEventInvites() {
		return eventInvites;
	}

	public void setEventInvites(List<EventInvite> eventInvites) {
		this.eventInvites = eventInvites;
	}

	@OneToMany(mappedBy="event")
	List<EventInvite> eventInvites;
	
	Event() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public LocalDateTime getDateTime() {
		return dateTime;
	}

	public void setDateTime(LocalDateTime datetime) {
		this.dateTime = datetime;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Event [id=" + id + ", lat=" + lat + ", lng=" + lng + ", dateTime=" + dateTime + "]";
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	
	
}
