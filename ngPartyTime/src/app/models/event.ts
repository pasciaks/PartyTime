export class Event {
  id: number;
  lat?: number | null;
  lng?: number | null;
  title: string;
  userId: number;
  datetime?: Date | null;

  constructor(data: Partial<Event> = {}) {
    this.id = data.id ?? 0;
    this.lat = data.lat ?? null;
    this.lng = data.lng ?? null;
    this.title = data.title ?? 'Unknown Title Of Event';
    this.userId = data.userId ?? 0;
    this.datetime = data.datetime ? new Date(data.datetime) : null;
  }
}
