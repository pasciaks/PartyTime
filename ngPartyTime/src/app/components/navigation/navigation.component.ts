import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import {
  ActivatedRoute,
  Router,
  RouterLink,
  RouterLinkActive,
} from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { FormsModule } from '@angular/forms';
import { NgbdModalComponent } from '../modal/modal-component';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [
    RouterLink,
    CommonModule,
    RouterLinkActive,
    FormsModule,
    NgbdModalComponent,
  ],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.css',
})
export class NavigationComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private eventService: EventService
  ) {}
  ngOnInit(): void {
    let storedUser = this.authService.getLoggedInUserFromLocalStorage();
    console.log('Stored User: ', storedUser);
  }

  showUserCredentials(): string {
    let storedUser = this.authService.getLoggedInUserFromLocalStorage();
    if (storedUser) {
      return storedUser?.username + ' (' + storedUser?.role + ')';
    } else {
      return '(Not Logged In)';
    }
  }

  hasRole(role: string): boolean {
    let storedUser = this.authService.getLoggedInUserFromLocalStorage();
    if (storedUser) {
      return storedUser.role === role;
    } else {
      return false;
    }
  }

  isLoggedIn(): boolean {
    return this.authService.checkLogin();
  }

  logout(): void {
    console.log('Logged Out');

    this.authService.logout();
    this.router.navigateByUrl('home');
  }
}
