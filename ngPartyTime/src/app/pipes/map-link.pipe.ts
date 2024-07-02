import { Pipe, PipeTransform } from '@angular/core';
import { Event } from '../models/event';

@Pipe({
  name: 'mapLink',
  standalone: true,
})
export class MapLinkPipe implements PipeTransform {
  transform(event: Event, zoom: string): string {
    let url: string = `https://www.google.com/maps/@${event.lat},${event.lng},${zoom}z?entry=ttu`;

    url = `http://maps.google.com/maps?q=${event.lat},${event.lng}`;

    // url = `https://www.google.com/maps/place/${event.lat},${event.lng}`;

    return url;
  }
}
