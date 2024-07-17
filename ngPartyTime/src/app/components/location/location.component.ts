import { Component, OnInit } from '@angular/core';
import { GeolocationService } from '../../services/geolocation.service';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-location',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './location.component.html',
  styleUrls: ['./location.component.css'],
})
export class LocationComponent implements OnInit {
  latitude: number | null = null;
  longitude: number | null = null;
  error: string | null = null;

  constructor(private geolocationService: GeolocationService) {}

  ngOnInit(): void {
    this.latitude = 0;
    this.longitude = 0;
    this.geolocationService
      .getCurrentPosition()
      .then((position) => {
        this.latitude = position.coords.latitude;
        this.longitude = position.coords.longitude;
      })
      .catch((err) => {
        this.error = err.message;
      });
  }
}
