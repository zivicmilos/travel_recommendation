import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminComponent } from './components/admin/admin.component';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { PageNotFoundComponent } from './components/page-not-found/page-not-found.component';
import { ProfileComponent } from './components/profile/profile.component';
import { ReservationComponent } from './components/reservation/reservation.component';
import { SuspiciousEventsComponent } from './components/suspicious-events/suspicious-events.component';
import { TravelComponent } from './components/travel/travel.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'reservation', component: ReservationComponent },
  { path: 'travel', component: TravelComponent },
  { path: 'admin', component: AdminComponent },
  { path: 'suspicious_event', component: SuspiciousEventsComponent },
  { path: 'profile', component: ProfileComponent },
  { path: '', component: LoginComponent },
  { path: '**', component: PageNotFoundComponent },
  { path: 'navbar', component: NavbarComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
