import { User } from './user';
import { Event } from './event';

export class EventInvite {
  id: number;
  userId: number;
  eventId: number;
  comment?: string | null;
  attending?: number | null;
  user?: User | null;
  event?: Event | null;

  constructor(
    id: number = 0,
    userId: number = 0,
    eventId: number = 0,
    comment?: string | null,
    attending?: number | null,
    user: User | null = null,
    event: Event | null = null
  ) {
    this.id = id ?? 0;
    this.userId = userId ?? 0;
    this.eventId = eventId ?? 0;
    this.comment = comment ?? null;
    this.attending = attending ?? null;
    this.user = user ?? null;
    this.event = event ?? null;
  }
}
