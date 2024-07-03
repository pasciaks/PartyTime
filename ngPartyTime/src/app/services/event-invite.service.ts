import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { EventInvite } from '../models/event-invite';
import { Buffer } from 'buffer';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';
@Injectable({
  providedIn: 'root',
})

// TODO: Implement EventInviteService
export class EventInviteService {
  // Set port number to server's port
  // private baseUrl = 'http://localhost:8088/';
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  loadEventInvites(): Observable<EventInvite[]> {
    return this.http
      .get<EventInvite[]>(
        this.url + 'api/event-invites/myeventinvites',
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving :', err);
          return throwError(
            () =>
              new Error('EventInviteService.index(): error retrieving: ' + err)
          );
        })
      );
  }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.authService.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }

  // getEventInvites(): Observable<EventInvite[]> {
  //   return this.http
  //     .get<EventInvite[]>(this.url)
  //     .pipe(
  //       map((eventInvites) =>
  //         eventInvites.map((eventInvite) => new EventInvite(eventInvite))
  //       )
  //     );
  // }

  // getEventInviteById(id: number): Observable<EventInvite> {
  //   return this.http
  //     .get<EventInvite>(`${this.url}/${id}`)
  //     .pipe(map((eventInvite) => new EventInvite(eventInvite)));
  // }

  // createEventInvite(eventInvite: EventInvite): Observable<EventInvite> {
  //   return this.http
  //     .post<EventInvite>(this.url, eventInvite)
  //     .pipe(map((newEventInvite) => new EventInvite(newEventInvite)));
  // }

  // updateEventInvite(eventInvite: EventInvite): Observable<EventInvite> {
  //   return this.http
  //     .put<EventInvite>(`${this.url}/${eventInvite.id}`, eventInvite)
  //     .pipe(map((updatedEventInvite) => new EventInvite(updatedEventInvite)));
  // }

  // deleteEventInvite(id: number): Observable<void> {
  //   return this.http.delete<void>(`${this.url}/${id}`);
  // }
}
