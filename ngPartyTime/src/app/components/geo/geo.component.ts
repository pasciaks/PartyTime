import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Event } from '../../models/event';
import { FormsModule } from '@angular/forms';
import { EventService } from '../../services/event.service';

@Component({
  selector: 'app-geo',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './geo.component.html',
  styleUrl: './geo.component.css',
})
export class GeoComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private eventService: EventService
  ) {
    console.log('Constructor');
  }

  event: Event = new Event(0, 0, 0, '', 0, null, null);

  lat: string = '';

  lng: string = '';

  errors: string[] = [];

  createEvent(): void {
    this.errors = [];
    console.log(this.event);
    if (this.event.lat === 0 || this.event.lng === 0) {
      console.error('Error: lat and lng must be set');
      this.errors.push('Error: lat and lng must be set');
      return;
    }
    if (this.event.title === '') {
      console.error('Error: title must be set');
      this.errors.push('Error: title must be set');
      return;
    }
    if (this.event.dateTime === null) {
      console.error('Error: dateTime must be set');
      this.errors.push('Error: dateTime must be set');
      return;
    }
    this.eventService.createEvent(this.event).subscribe({
      next: (event: Event) => {
        console.log(event);
        this.router.navigateByUrl('/profile');
      },
      error: (err) => {
        console.error(err);
        this.errors = err.error;
      },
    });
  }

  sendLatLngToBackend: () => void = () => {
    this.authService.geo(this.lat, this.lng).subscribe({
      next: (response: string) => {
        console.log(response);
      },
      error: (err) => {
        // console.error(err);
      },
    });
  };

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: (params) => {
        console.log(params);
        this.lat = params.get('lat') || '';
        this.lng = params.get('lng') || '';
        localStorage.setItem('lat', this.lat);
        localStorage.setItem('lng', this.lng);
        this.event.lat = Number(this.lat);
        this.event.lng = Number(this.lng);
        this.sendLatLngToBackend();
      },
    });
  }
}
