import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-navigation',
  standalone: true,
  imports: [RouterLink, CommonModule],
  templateUrl: './navigation.component.html',
  styleUrl: './navigation.component.css',
})
export class NavigationComponent {
  constructor(private authService: AuthService, private router: Router) {}

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
