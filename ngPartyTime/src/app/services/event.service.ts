import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Event } from '../models/event';
import { Buffer } from 'buffer';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root',
})

// TODO: Implement EventService
export class EventService {
  // Set port number to server's port
  // private baseUrl = 'http://localhost:8088/';
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(private http: HttpClient, private router: Router) {}

  getEvents(): Observable<Event[]> {
    return this.http
      .get<Event[]>(this.url)
      .pipe(map((events) => events.map((event) => new Event(event))));
  }

  getEventById(id: number): Observable<Event> {
    return this.http
      .get<Event>(`${this.url}/${id}`)
      .pipe(map((event) => new Event(event)));
  }

  createEvent(event: Event): Observable<Event> {
    return this.http
      .post<Event>(this.url, event)
      .pipe(map((newEvent) => new Event(newEvent)));
  }

  updateEvent(event: Event): Observable<Event> {
    return this.http
      .put<Event>(`${this.url}/${event.id}`, event)
      .pipe(map((updatedEvent) => new Event(updatedEvent)));
  }

  deleteEvent(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
