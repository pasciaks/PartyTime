package com.pasciak.partytime.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.pasciak.partytime.entities.Event;
import com.pasciak.partytime.repositories.EventRepository;

@Service
public class EventServiceImpl implements EventService {
	
	private EventRepository eventRepo;
	
	public EventServiceImpl( EventRepository eventRepo) {
		super();
		this.eventRepo = eventRepo;
	}

	@Override
	public Event show(long eventId) {
		
		Optional<Event> event = null;

		event = eventRepo.findById(eventId);

		if (event.isPresent()) {
			return event.get();
		} else {
			return null;
		}
		
	}

	@Override
	public List<Event> index() {
		return eventRepo.findAll();
	}

	@Override
	public Event create(Event event) {
		
//		Film createdFilm = null;
//
//		if (film.getLanguage() == null) {
//			Language defaultLanguage = new Language();
//			defaultLanguage.setId(1);
//			film.setLanguage(defaultLanguage);
//		}
//
//		try {
//			System.out.println("Before saveAndFlush");
//			createdFilm = filmRepo.saveAndFlush(film);
//			System.out.println("After saveAndFlush");
//		} catch (Exception e) {
//			System.out.println("Exception: " + e);
//			e.printStackTrace();
//			Film filmToReturn = new Film();
//			filmToReturn.setErrorMessage(e.getMessage());
//			return filmToReturn;
//		}
//
//		return createdFilm;
		
		return null;
	}

	@Override
	public Event update(long eventId, Event event) {
		
//		Optional<Film> filmOpt = filmRepo.findById(filmId);

//		Film managedFilm = null;

//		if (filmOpt.isPresent()) {
//			managedFilm = filmOpt.get();
//			managedFilm.setTitle(film.getTitle());
//			managedFilm.setDescription(film.getDescription());
//			managedFilm.setReleaseYear(film.getReleaseYear());

			// managedFilm.setSpecialFeatures(film.getSpecialFeatures());

//			Language filmLanguage = film.getLanguage();

//			if (filmLanguage != null && filmLanguage.getId() > 0) {

				// if (languageRepo.existsById(film.getLanguage().getId())) {
				// __ managedFilm.setLanguage(film.getLanguage());
				// } else {
				// __ // optionally add new language to database
				// __ managedFilm.setLanguage(null);
				// }

//				managedFilm.setLanguage(film.getLanguage());
//			}

//			managedFilm.setRentalDuration(film.getRentalDuration());
//			managedFilm.setRentalRate(film.getRentalRate());
//			managedFilm.setLength(film.getLength());
//			managedFilm.setReplacementCost(film.getReplacementCost());

			// managedFilm.setRating(film.getRating());

//			filmRepo.saveAndFlush(managedFilm);

//		}

//		return managedFilm;
		
		return null;
	}

	@Override
	public void delete(long eventId) {
		

//		if (filmRepo.existsById(filmId)) {
//			filmRepo.deleteById(filmId);
//		}

		// Film film = this.show(filmId);
		//
		// if (film == null) {
		// return;
		// }
		//
		// try {
		// filmRepo.delete(film);
		// } catch (Exception e) {
		// e.printStackTrace();
		// }

	}

	@Override
	public List<Event> findEventsByUserId(long userId) {
		
		List<Event> foundEvents = eventRepo.findEventsByUserId(userId);
		return foundEvents;
	}

}
