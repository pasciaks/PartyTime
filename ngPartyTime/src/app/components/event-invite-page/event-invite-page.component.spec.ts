import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EventInvitePageComponent } from './event-invite-page.component';

describe('EventInvitePageComponent', () => {
  let component: EventInvitePageComponent;
  let fixture: ComponentFixture<EventInvitePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [EventInvitePageComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(EventInvitePageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
