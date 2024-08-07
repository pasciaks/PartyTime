import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { Event } from '../../models/event';
import { FormsModule } from '@angular/forms';
import { EventService } from '../../services/event.service';
import { MapModule } from '../../map.module';
import { LocationComponent } from '../../components/location/location.component'; //'../.././location/location.component';
import { GeolocationService } from '../../services/geolocation.service';
import { NgbdModalComponent } from '../modal/modal-component';
@Component({
  selector: 'app-geo',
  standalone: true,
  imports: [
    CommonModule,
    FormsModule,
    MapModule,
    LocationComponent,
    NgbdModalComponent,
  ],
  templateUrl: './geo.component.html',
  styleUrl: './geo.component.css',
})
export class GeoComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private eventService: EventService,
    private geolocationService: GeolocationService
  ) {
    console.log('Constructor');
  }

  onMapClick: (event: any) => void = (event) => {
    console.log(event);
    this.event.lat = event.lat;
    this.event.lng = event.lng;
    this.sendLatLngToBackend();
  };

  event: Event = new Event(0, 0, 0, '', 0, null, null);

  error: string = '';

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

  navToCurrentLocation: () => void = () => {
    this.geolocationService
      .getCurrentPosition()
      .then((position) => {
        this.lat = position.coords.latitude.toString();
        this.lng = position.coords.longitude.toString();
        localStorage.setItem('lat', this.lat);
        localStorage.setItem('lng', this.lng);
        this.sendLatLngToBackend();
        window.location.reload();
      })
      .catch((err) => {
        this.error = err.message;
      });
  };

  ngOnInit(): void {
    this.activatedRoute.paramMap.subscribe({
      next: (params) => {
        console.log(params);
        this.lat = params.get('lat') || localStorage.getItem('lat') || '';
        this.lng = params.get('lng') || localStorage.getItem('lng') || '';
        localStorage.setItem('lat', this.lat);
        localStorage.setItem('lng', this.lng);
        this.event.lat = Number(this.lat);
        this.event.lng = Number(this.lng);
        this.sendLatLngToBackend();
      },
    });
  }
}
