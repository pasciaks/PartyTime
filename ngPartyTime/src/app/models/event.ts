import { EventInvite } from './event-invite';
import { User } from './user';

export class Event {
  id: number;
  lat?: number | null;
  lng?: number | null;
  title: string;
  userId: number;
  dateTime?: Date | null;
  user: User | null;
  eventInvites: EventInvite[] | null;

  constructor(
    id: number = 0,
    lat: number | null = 0,
    lng: number | null = 0,
    title: string = '',
    userId: number = 0,
    dateTime?: Date | null,
    user: User | null = null,
    eventInvites: EventInvite[] | null = null
  ) {
    this.id = id ?? 0;
    this.lat = lat ?? null;
    this.lng = lng ?? null;
    this.title = title ?? 'Unknown Title Of Event';
    this.userId = userId ?? 0;
    this.dateTime = dateTime ? new Date(dateTime) : null;
    this.user = user ?? null;
    this.eventInvites = eventInvites ?? null;
  }
}
