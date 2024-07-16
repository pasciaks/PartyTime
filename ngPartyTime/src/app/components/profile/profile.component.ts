import { Component, OnInit } from '@angular/core';
import { EventComponent } from '../event/event.component';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { Event } from '../../models/event';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { MapLinkPipe } from '../../pipes/map-link.pipe';
import { EventInvite } from '../../models/event-invite';
import { EventInviteService } from '../../services/event-invite.service';
import { NgbdModalComponent } from '../modal/modal-component';
import { MapModule } from '../../map.module';

@Component({
  selector: 'app-profile',
  standalone: true,
  imports: [
    EventComponent,
    CommonModule,
    FormsModule,
    MapLinkPipe,
    NgbdModalComponent,
    MapModule,
  ],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css',
})
export class ProfileComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private authService: AuthService,
    private router: Router,
    private eventService: EventService,
    private eventInviteService: EventInviteService
  ) {}

  handleAction(data: any) {
    console.log('Action called with data:', data);
  }

  handleEvent(event: any) {
    console.log('Event emitted with data:', event);
  }

  events: Event[] = [];

  eventInvites: EventInvite[] = [];

  ngOnInit(): void {
    this.loadEvents();
    this.loadEventInvites();
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

  loadEventInvites(): void {
    this.eventInviteService.loadEventInvites().subscribe({
      next: (eventInvites: EventInvite[]) => {
        this.eventInvites = eventInvites;
        console.log(this.eventInvites);
        return;
      },
      error: (err) => {
        console.log(err);
        return;
      },
    });
  }
}
