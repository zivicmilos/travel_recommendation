import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Destination } from 'src/app/model/destination-model';
import { Like } from 'src/app/model/like-model';
import { User } from 'src/app/model/user-model';
import { DestinationService } from 'src/app/services/destination-service/destination.service';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  currentUser: User = new User;
  transportationType: string = '';
  budget: number = NaN;
  destinationType: string = '';
  weather: string = '';
  continent: string = '';
  /*transportationType: String = 'PLANE';
  budget: Number = 400;
  destinationType: String = 'PARTY';
  weather: String = "WARM";
  continent: String = 'Europe';*/
  destinations: Destination[] = [];
  constructor(private destinationService: DestinationService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.currentUser = this.userService.getCurrentUser();
    this.transportationType = localStorage.getItem('transportationType') || '';
    this.destinationType = localStorage.getItem('destinationType') || '';
    this.weather = localStorage.getItem('weather') || '';
    this.continent = localStorage.getItem('continent') || '';
    this.budget = Number(localStorage.getItem('budget') || NaN);
    if (this.transportationType !== '')
      this.findDestinations();
  }

  findDestinations() {
    this.destinationService.findDestinations(this.transportationType, this.budget, this.destinationType,
      this.weather, this.continent).subscribe(
        (data: any) => {
          this.destinations = data;
          localStorage.setItem('transportationType', this.transportationType);
          localStorage.setItem('budget', this.budget.toString());
          localStorage.setItem('destinationType', this.destinationType);
          localStorage.setItem('weather', this.weather);
          localStorage.setItem('continent', this.continent);
        }
      );
  }

  like(destination: String) {
    let date: Date = new Date();
    date.setHours(date.getHours() + 2);
    let like: Like = new Like(this.currentUser.username, destination, date);
    this.destinationService.like(like).subscribe(
      (data: any) => {
        if (data === 'Ok') {
          this.destinations.find(d => d.location.city === destination)?.likes.push(like);
        }
      }
    );
  }

  reserve(destination: Destination) {
    this.router.navigate(['/reservation'], { state: { data: destination } });
    localStorage.setItem('destination', JSON.stringify(destination));
  }

  myTravels() {
    this.router.navigate(['/travel']);
  }

  capitalize(s: string) {
    s = s.toLowerCase();
    return s.charAt(0).toUpperCase() + s.slice(1);
  }
}
