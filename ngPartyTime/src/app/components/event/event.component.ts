import { Component, Input, OnInit } from '@angular/core';

@Component({
  selector: 'app-event',
  standalone: true,
  imports: [],
  templateUrl: './event.component.html',
  styleUrl: './event.component.css',
})
export class EventComponent implements OnInit {
  @Input() id: string = ''; // Add an initializer for the 'id' property
  @Input() title: string = '';
  @Input() lat: number = 0;
  @Input() lng: number = 0;
  @Input() datetime: string = '';
  @Input() username: string = '';

  constructor() {}

  ngOnInit() {
    // Initialize the properties with the input values
    this.id = this.id || '';
    this.title = this.title || '';
    this.lat = this.lat || 0;
    this.lng = this.lng || 0;
    this.datetime = this.datetime || '';
    this.username = this.username || '';
  }
}
