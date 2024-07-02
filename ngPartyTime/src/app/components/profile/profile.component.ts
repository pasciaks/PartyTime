import { Component, OnInit } from '@angular/core';
import { EventComponent } from '../event/event.component';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { Event } from '../../models/event';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MapLinkPipe } from '../../pipes/map-link.pipe';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [EventComponent, CommonModule, FormsModule, MapLinkPipe],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private eventService: EventService
  ) {}

  events: Event[] = [];

  ngOnInit(): void {
    this.loadEvents();
  }

  loadEvents(): void {
    this.eventService.loadEvents().subscribe({
      next: (events: Event[]) => {
        this.events = events;
        console.log(this.events);
        return;
      },
      error: (err) => {
        console.log(err);
        return;
      },
    });
  }
}
