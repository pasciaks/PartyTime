export class EventInvite {
  id: number;
  userId: number;
  eventId: number;
  comment?: string | null;
  attending?: number | null;

  constructor(data: Partial<EventInvite> = {}) {
    this.id = data.id ?? 0;
    this.userId = data.userId ?? 0;
    this.eventId = data.eventId ?? 0;
    this.comment = data.comment ?? null;
    this.attending = data.attending ?? null;
  }
}
