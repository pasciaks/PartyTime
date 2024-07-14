import { Component, Input, OnInit } from '@angular/core';
import { User } from '../../models/user';
import { Event } from '../../models/event';
import { MapLinkPipe } from '../../pipes/map-link.pipe';
import { CommonModule } from '@angular/common';
import { EventInvite } from '../../models/event-invite';

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [MapLinkPipe, CommonModule],
  templateUrl: './event.component.html',
  styleUrl: './event.component.css',
})
export class EventComponent implements OnInit {
  @Input() id: string = '';
  @Input() title: string = '';
  @Input() lat: string = '0';
  @Input() lng: string = '0';
  @Input() dateTime: string = '';
  @Input() user: User | null = null;
  @Input() event: Event | null = null;

  constructor() {}

  evaluate(eventInvite: EventInvite): string {
    if (eventInvite.attending === 1) {
      return 'Attending';
    } else if (eventInvite.attending === 0) {
      return 'Not Attending';
    } else {
      return 'Unknown';
    }
  }

  ngOnInit() {
    // Initialize the properties with the input values
    // this.id = this.id || '';
    // this.title = this.title || '';
    // this.lat = this.lat || '0';
    // this.lng = this.lng || '0';
    // this.dateTime = this.dateTime || '';
    // this.user = this.user || null;
  }
}
