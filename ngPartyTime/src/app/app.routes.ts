import { Routes } from '@angular/router';
import { RootComponent } from './components/root/root.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { HomeComponent } from './components/home/home.component';
import { AboutComponent } from './components/about/about.component';
import { RegisterComponent } from './components/register/register.component';
import { LoginComponent } from './components/login/login.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { ProfileComponent } from './components/profile/profile.component';
import { UsersPageComponent } from './components/users-page/users-page.component';
import { EventsPageComponent } from './components/events-page/events-page.component';
import { GeoComponent } from './components/geo/geo.component';

export const routes: Routes = [
  { path: '', component: RootComponent },
  { path: 'welcome', component: WelcomeComponent },
  { path: 'home', component: HomeComponent },
  { path: 'about', component: AboutComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'login', component: LoginComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'users', component: UsersPageComponent },
  { path: 'events', component: EventsPageComponent },
  { path: 'geo/:lat/:lng', component: GeoComponent },
  { path: '**', component: NotFoundComponent },
];
