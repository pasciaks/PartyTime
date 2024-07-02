export class Event {
  id: number;
  lat?: number | null;
  lng?: number | null;
  title: string;
  userId: number;
  dateTime?: Date | null;

  constructor(
    id: number = 0,
    lat: number | null = 0,
    lng: number | null = 0,
    title: string = '',
    userId: number = 0,
    dateTime?: Date | null
  ) {
    this.id = id ?? 0;
    this.lat = lat ?? null;
    this.lng = lng ?? null;
    this.title = title ?? 'Unknown Title Of Event';
    this.userId = userId ?? 0;
    this.dateTime = dateTime ? new Date(dateTime) : null;
  }
}
