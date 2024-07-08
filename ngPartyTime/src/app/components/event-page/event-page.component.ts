import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EventComponent } from '../event/event.component';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { Event } from '../../models/event';

@Component({
  selector: 'app-event-page',
  standalone: true,
  imports: [CommonModule, FormsModule, EventComponent],
  templateUrl: './event-page.component.html',
  styleUrl: './event-page.component.css',
})
export class EventPageComponent implements OnInit {
  eventId: Number = 0;
  wasFound: boolean = false;
  event: Event | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private eventService: EventService
  ) {
    console.log('Constructor');
  }
  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: (params) => {
        console.log(params);
        this.eventId = Number(params.get('eventId') || 0);
        this.getSingleEvent(this.eventId);
      },
    });
  }

  getSingleEvent(eventId: Number): void {
    this.eventService.show(eventId).subscribe({
      next: (event: Event) => {
        console.log(event);
        this.event = event;
        this.wasFound = true;
      },
      error: (err) => {
        console.error(err);
        this.wasFound = false;
        console.error(
          'You are not the event creator and are not invited to this event.'
        );
      },
    });
  }
}
