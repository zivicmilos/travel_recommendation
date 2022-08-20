import { Component, OnInit } from '@angular/core';
import { Travel } from 'src/app/model/travel-model';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-travel',
  templateUrl: './travel.component.html',
  styleUrls: ['./travel.component.css']
})
export class TravelComponent implements OnInit {
  travels: Travel[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.getTravels();
  }

  getTravels() {
    this.userService.getTravelsByUsername().subscribe(
      (data: any) => {
        this.travels = data;
      }
    );
  }
  
  cancel(travel: Travel) {
    this.userService.cancelTravel(travel).subscribe();
    this.getTravels();
  }

  isGreatherThanToday(travelDate: Date) {
    var date = new Date(travelDate)
    var today = new Date();
    today.setHours(0,0,0,0);
    if (date > today)
      return true;
    else 
      return false;
  }
}
