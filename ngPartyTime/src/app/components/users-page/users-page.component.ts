import { Component, OnInit } from '@angular/core';
import { EventComponent } from '../event/event.component';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { UserService } from '../../services/user.service';
import { Event } from '../../models/event';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MapLinkPipe } from '../../pipes/map-link.pipe';
import { EventInvite } from '../../models/event-invite';
import { EventInviteService } from '../../services/event-invite.service';
import { User } from '../../models/user';

@Component({
  selector: 'app-users-page',
  standalone: true,
  imports: [EventComponent, CommonModule, FormsModule, MapLinkPipe],
  templateUrl: './users-page.component.html',
  styleUrl: './users-page.component.css',
})
export class UsersPageComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private userService: UserService,
    private eventService: EventService,
    private eventInviteService: EventInviteService
  ) {}

  users: User[] = [];

  ngOnInit(): void {
    // TODO: ENSURE logged in user is admin
    this.loadUsers();
  }

  loadUsers(): void {
    this.userService.loadUsers().subscribe({
      next: (users: User[]) => {
        this.users = users;
        console.log(this.users);
        return;
      },
      error: (err) => {
        console.log(err);
        return;
      },
    });
  }
}
