import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventInvitesPageComponent } from './event-invites-page.component';

describe('EventInvitesPageComponent', () => {
  let component: EventInvitesPageComponent;
  let fixture: ComponentFixture<EventInvitesPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventInvitesPageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EventInvitesPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
