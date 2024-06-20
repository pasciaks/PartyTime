import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { AuthService } from './services/auth.service';
import { HeaderComponent } from './components/header/header.component';
import { RootComponent } from './components/root/root.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, HeaderComponent, RootComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css',
})
export class AppComponent {
  title = 'ngPartyTime';

  constructor(private auth: AuthService) {}

  ngOnInit() {
    // this.tempTestDeleteMeLater(); // DELETE LATER!!!
  }

  // tempTestDeleteMeLater() {
  //   this.auth.login('planner@pasciak.com', 'planner').subscribe({
  //     // change username to match DB
  //     next: (data) => {
  //       console.log('Logged in:');
  //       console.log(data);
  //     },
  //     error: (fail) => {
  //       console.error('Error authenticating:');
  //       console.error(fail);
  //     },
  //   });
  // }
}
