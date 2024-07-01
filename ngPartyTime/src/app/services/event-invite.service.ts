import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { EventInvite } from '../models/event-invite';
import { Buffer } from 'buffer';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
@Injectable({
  providedIn: 'root',
})

// TODO: Implement EventInviteService
export class EventInviteService {
  // Set port number to server's port
  // private baseUrl = 'http://localhost:8088/';
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(private http: HttpClient, private router: Router) {}

  getEventInvites(): Observable<EventInvite[]> {
    return this.http
      .get<EventInvite[]>(this.url)
      .pipe(
        map((eventInvites) =>
          eventInvites.map((eventInvite) => new EventInvite(eventInvite))
        )
      );
  }

  getEventInviteById(id: number): Observable<EventInvite> {
    return this.http
      .get<EventInvite>(`${this.url}/${id}`)
      .pipe(map((eventInvite) => new EventInvite(eventInvite)));
  }

  createEventInvite(eventInvite: EventInvite): Observable<EventInvite> {
    return this.http
      .post<EventInvite>(this.url, eventInvite)
      .pipe(map((newEventInvite) => new EventInvite(newEventInvite)));
  }

  updateEventInvite(eventInvite: EventInvite): Observable<EventInvite> {
    return this.http
      .put<EventInvite>(`${this.url}/${eventInvite.id}`, eventInvite)
      .pipe(map((updatedEventInvite) => new EventInvite(updatedEventInvite)));
  }

  deleteEventInvite(id: number): Observable<void> {
    return this.http.delete<void>(`${this.url}/${id}`);
  }
}
