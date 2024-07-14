import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { tap, catchError, throwError, Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { User } from '../models/user';
import { Buffer } from 'buffer';
import { Router } from '@angular/router';
import { RegistrationError } from '../models/registration-error';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  // Set port number to server's port
  // private baseUrl = 'http://localhost:8088/';
  private baseUrl = environment.baseUrl;
  private url = this.baseUrl;

  constructor(private http: HttpClient, private router: Router) {}

  getUrl(): string {
    return this.url;
  }

  geo(lat: string, lng: string): Observable<string> {
    // Create POST request to register a new account
    return this.http
      .get<string>(this.url + `api/geo/${lat}/${lng}`, this.getHttpOptions())
      .pipe(
        catchError((err: any) => {
          // console.log(err?.status);
          return throwError(
            () => new RegistrationError(err?.status || 'Unknown', err.status)
          );
        })
      );
  }

  register(user: User): Observable<User> {
    // Create POST request to register a new account
    return this.http.post<User>(this.url + 'register', user).pipe(
      catchError((err: any) => {
        console.log(err?.status);
        return throwError(
          () =>
            new RegistrationError(
              'AuthService.register(): error registering user.',
              err.status
            )
        );
      })
    );
  }

  login(username: string, password: string): Observable<User> {
    // Make credentials
    const credentials = this.generateBasicAuthCredentials(username, password);
    // Send credentials as Authorization header specifying Basic HTTP authentication
    const httpOptions = {
      headers: new HttpHeaders({
        Authorization: `Basic ${credentials}`,
        'X-Requested-With': 'XMLHttpRequest',
      }),
    };

    // Create GET request to authenticate credentials
    return this.http.get<User>(this.url + 'authenticate', httpOptions).pipe(
      tap((newUser) => {
        // While credentials are stored in browser localStorage, we consider
        // ourselves logged in.
        localStorage.setItem('credentials', credentials);
        localStorage.setItem('loggedInUser', JSON.stringify(newUser));
        console.log(newUser);
        return newUser;
      }),
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () => new Error('AuthService.login(): error logging in user.')
        );
      })
    );
  }

  upload(formData: FormData): Observable<string> {
    // Create POST request to register a new account
    return this.http
      .post<string>(
        this.url + 'api/files/uploadfiles',
        formData,
        this.getHttpOptions()
      )
      .pipe(
        catchError((err: any) => {
          console.log(err?.status);
          return throwError(
            () =>
              new RegistrationError(
                'AuthService.register(): error registering user.',
                err.status
              )
          );
        })
      );
  }

  logout(): void {
    localStorage.removeItem('credentials');
    localStorage.removeItem('loggedInUser');
    this.router.navigateByUrl('login');
  }

  getLoggedInUser(): Observable<User> {
    console.log('  AuthService.getLoggedInUser()');
    if (!this.checkLogin()) {
      return throwError(() => {
        new Error('Not logged in.');
      });
    }
    let httpOptions = {
      headers: {
        Authorization: 'Basic ' + this.getCredentials(),
        'X-Requested-with': 'XMLHttpRequest',
      },
    };
    return this.http.get<User>(this.url + 'authenticate', httpOptions).pipe(
      catchError((err: any) => {
        console.log(err);
        return throwError(
          () =>
            new Error(
              'AuthService.getUserById(): error retrieving user: ' + err
            )
        );
      })
    );
  }

  checkLogin(): boolean {
    if (localStorage.getItem('credentials')) {
      return true;
    }
    return false;
  }

  generateBasicAuthCredentials(username: string, password: string): string {
    return Buffer.from(`${username}:${password}`).toString('base64');
  }

  getCredentials(): string | null {
    return localStorage.getItem('credentials');
  }

  getLoggedInUserFromLocalStorage(): User | null {
    let loggedInUser = localStorage.getItem('loggedInUser');
    if (loggedInUser === null) {
      return null;
    }
    return JSON.parse(loggedInUser);
  }

  getHttpOptions() {
    let options = {
      headers: {
        Authorization: 'Basic ' + this.getCredentials(),
        'X-Requested-With': 'XMLHttpRequest',
      },
    };
    return options;
  }
}
