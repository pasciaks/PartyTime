import { Component, OnInit } from '@angular/core';
import * as L from 'leaflet';

// Fix for leaflet's default icon issues
// import 'assets/images/marker-shadow.png';
// import 'assets/images/marker-icon-2x.png';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.css'],
})
export class MapComponent implements OnInit {
  constructor() {}

  defaultIcon = L.icon({
    iconUrl: 'assets/images/marker-icon.png',
    shadowUrl: 'assets/images/marker-shadow.png',
    iconSize: [25, 41],
    iconAnchor: [12, 41],
    popupAnchor: [1, -34],
    shadowSize: [41, 41],
  });

  map: any = null;

  // Method to handle map click events
  // onMapClick(e: L.LeafletMouseEvent) {
  //   console.log('Map clicked at: ', e.latlng);
  // }

  // Method to handle map click events
  onMapClick(e: L.LeafletMouseEvent) {
    console.log('Map clicked at: ', e.latlng);

    // Create a custom icon
    const customIcon = L.icon({
      iconUrl: 'assets/images/marker-icon.png',
      shadowUrl: 'assets/images/marker-shadow.png',
      iconSize: [25, 41], // Size of the icon
      iconAnchor: [12, 41], // Point of the icon which will correspond to marker's location
      popupAnchor: [1, -34], // Point from which the popup should open relative to the iconAnchor
      shadowSize: [41, 41], // Size of the shadow
    });

    // Add a marker with the custom icon at the clicked location
    L.marker(e.latlng, {
      icon: customIcon,
      draggable: false,
      riseOnHover: true,
      title: 'My Marker',
      alt: 'My Marker',
    })
      .addTo(this.map)
      .bindPopup(
        `<br><br><a href='/#/geo/${
          e.latlng.lat.toString() + '/' + e.latlng.lng.toString()
        }'>Use This Location</a>`
      )
      .openPopup();

    // .bindPopup(
    //   'You clicked the map at ' +
    //     e.latlng.toString() +
    //     `<br><br><a href='/#/geo/${
    //       e.latlng.lat.toString() + '/' + e.latlng.lng.toString()
    //     }'>Use This Location</a>`
    // )
  }

  ngOnInit(): void {
    const map = L.map('map').setView([51.505, -0.09], 13);

    this.map = map;

    L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
      attribution:
        '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
    }).addTo(map);

    // const defaultIcon = L.icon({
    //   iconUrl: 'assets/images/marker-icon.png',
    //   shadowUrl: 'assets/images/marker-shadow.png',
    //   iconSize: [25, 41],
    //   iconAnchor: [12, 41],
    //   popupAnchor: [1, -34],
    //   shadowSize: [41, 41],
    // });

    let lat = Number(localStorage.getItem('lat') || 0);
    let lng = Number(localStorage.getItem('lng') || 0);

    L.marker([lat, lng], { icon: this.defaultIcon })
      .addTo(map)
      .bindPopup('Currently Selected Location')
      .openPopup();

    // Add a click event listener to the map
    map.on('click', this.onMapClick.bind(this));

    map.flyTo([lat, lng], 13);
  }

  // ngOnInit(): void {
  //   const map = L.map('map').setView([51.505, -0.09], 13);

  //   L.tileLayer('https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png', {
  //     attribution:
  //       '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors',
  //   }).addTo(map);

  //   L.marker([51.5, -0.09])
  //     .addTo(map)
  //     .bindPopup('A pretty CSS3 popup.<br> Easily customizable.')
  //     .openPopup();
  // }
}
