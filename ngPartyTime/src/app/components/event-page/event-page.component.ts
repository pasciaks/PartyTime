import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { EventComponent } from '../event/event.component';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';
import { EventService } from '../../services/event.service';
import { Event } from '../../models/event';
import { User } from '../../models/user';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms'; // Import this
import { EventInviteService } from '../../services/event-invite.service';
import { EventInvite } from '../../models/event-invite';
@Component({
  selector: 'app-event-page',
  standalone: true,
  imports: [CommonModule, FormsModule, EventComponent, ReactiveFormsModule],
  templateUrl: './event-page.component.html',
  styleUrl: './event-page.component.css',
})
export class EventPageComponent implements OnInit {
  eventId: Number = 0;
  wasFound: boolean = false;
  event: Event | null = null;

  loggedInUser: User | null = null;

  constructor(
    private activatedRoute: ActivatedRoute,
    private router: Router,
    private authService: AuthService,
    private eventService: EventService,
    private eventInviteService: EventInviteService,
    private fb: FormBuilder
  ) {
    console.log('Constructor');
    this.emailForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      invalid: [false],
    });
  }

  emailForm: FormGroup;

  eventInvite: EventInvite | null = null;

  onSubmit() {
    if (this.emailForm.valid) {
      console.log('Form Submitted!', this.emailForm.value);

      // post form to subscribed service to create event-invite

      this.eventInviteService
        .invite(this.emailForm.value.email, this.eventId)
        .subscribe({
          next: (eventInvite: EventInvite) => {
            console.log(eventInvite);
            this.eventInvite = eventInvite;
            alert(
              'User invited to event! \n\n' +
                JSON.stringify(eventInvite, null, 2)
            );
          },
          error: (err) => {
            console.error(err);
            console.error('Error inviting user to event.');
            alert(
              'Error inviting user to event. \n\n' +
                JSON.stringify(err, null, 2)
            );
          },
        });
    }
  }

  ngOnInit(): void {
    this.loggedInUser = null;
    console.log('.........getting user..........');
    this.authService.getLoggedInUser().subscribe({
      next: (user: User) => {
        console.log(user);
        this.loggedInUser = user;
      },
      error: (err) => {
        console.error(err);
        console.error('You are not logged in.');
        this.router.navigateByUrl('login');
      },
    });
    this.activatedRoute.paramMap.subscribe({
      next: (params) => {
        console.log(params);
        this.eventId = Number(params.get('eventId') || 0);
        this.getSingleEvent(this.eventId);
      },
    });
  }

  getSingleEvent(eventId: Number): void {
    this.eventService.show(eventId).subscribe({
      next: (event: Event) => {
        console.log(event);
        this.event = event;
        this.wasFound = true;
        localStorage.setItem('lat', this.event?.lat?.toString() || '0');
        localStorage.setItem('lng', this.event?.lng?.toString() || '0');
      },
      error: (err) => {
        this.event = null;
        console.error(err);
        this.wasFound = false;
        console.error(
          'You are not the event creator and are not invited to this event.'
        );
      },
    });
  }
}
