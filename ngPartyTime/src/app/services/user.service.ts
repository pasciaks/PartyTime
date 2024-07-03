import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
import { User } from '../models/user';
import { Buffer } from 'buffer';
import { Router } from '@angular/router';
import { environment } from '../../environments/environment';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root',
})

// TODO: Implement UserService
export class UserService {
  // Set port number to server's port
  // private baseUrl = 'http://localhost:8088/';
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(
    private http: HttpClient,
    private router: Router,
    private authService: AuthService
  ) {}

  loadUsers(): Observable<User[]> {
    return this.http
      .get<User[]>(this.url + 'api/users', this.getHttpOptions())
      .pipe(
        catchError((err: any) => {
          console.error('Error retrieving :', err);
          return throwError(
            () => new Error('loadUsers(): error retrieving: ' + err)
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

  // getUsers(): Observable<User[]> {
  //   return this.http
  //     .get<User[]>(this.url)
  //     .pipe(
  //       map((users) =>
  //         users.map(
  //           (user) =>
  //             new User(
  //               user.id,
  //               user.username,
  //               user.password,
  //               user.enabled,
  //               user.createdAt ? new Date(user.createdAt) : null,
  //               user.role,
  //               user.firstName,
  //               user.lastName,
  //               user.updatedAt ? new Date(user.updatedAt) : null
  //             )
  //         )
  //       )
  //     );
  // }

  // getUserById(id: number): Observable<User> {
  //   return this.http
  //     .get<User>(`${this.url}/${id}`)
  //     .pipe(
  //       map(
  //         (user) =>
  //           new User(
  //             user.id,
  //             user.username,
  //             user.password,
  //             user.enabled,
  //             user.createdAt ? new Date(user.createdAt) : null,
  //             user.role,
  //             user.firstName,
  //             user.lastName,
  //             user.updatedAt ? new Date(user.updatedAt) : null
  //           )
  //       )
  //     );
  // }

  // createUser(user: User): Observable<User> {
  //   return this.http
  //     .post<User>(this.url, user)
  //     .pipe(
  //       map(
  //         (newUser) =>
  //           new User(
  //             newUser.id,
  //             newUser.username,
  //             newUser.password,
  //             newUser.enabled,
  //             newUser.createdAt ? new Date(newUser.createdAt) : null,
  //             newUser.role,
  //             newUser.firstName,
  //             newUser.lastName,
  //             newUser.updatedAt ? new Date(newUser.updatedAt) : null
  //           )
  //       )
  //     );
  // }

  // updateUser(user: User): Observable<User> {
  //   return this.http
  //     .put<User>(`${this.url}/${user.id}`, user)
  //     .pipe(
  //       map(
  //         (updatedUser) =>
  //           new User(
  //             updatedUser.id,
  //             updatedUser.username,
  //             updatedUser.password,
  //             updatedUser.enabled,
  //             updatedUser.createdAt ? new Date(updatedUser.createdAt) : null,
  //             updatedUser.role,
  //             updatedUser.firstName,
  //             updatedUser.lastName,
  //             updatedUser.updatedAt ? new Date(updatedUser.updatedAt) : null
  //           )
  //       )
  //     );
  // }

  // deleteUser(id: number): Observable<void> {
  //   return this.http.delete<void>(`${this.url}/${id}`);
  // }
}
