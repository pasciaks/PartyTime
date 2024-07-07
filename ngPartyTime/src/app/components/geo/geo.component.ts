import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-geo',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './geo.component.html',
  styleUrl: './geo.component.css',
})
export class GeoComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthService
  ) {
    console.log('Constructor');
  }

  lat: string = '';

  lng: string = '';

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
        this.sendLatLngToBackend();
      },
    });
  }
}
