import { Component, OnInit } from '@angular/core';
import { Travel } from 'src/app/model/travel-model';
import { TravelDto } from 'src/app/dto/travel-dto';
import { UserService } from 'src/app/services/user-service/user.service';

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
    let travelDto: TravelDto = new TravelDto();
    travelDto.user = travel.user.username;
    travelDto.destination = travel.destination.location.city;
    travelDto.grade = travel.grade;
    travelDto.cost = travel.cost;
    travelDto.travelDate = travel.travelDate;
    travelDto.transportationType = travel.transportationType;
    this.userService.cancelTravel(travelDto).subscribe(
      () => {
        this.userService.getUserByUsername().subscribe(
          (data: any) => {
            this.userService.setCurrentUser(data);
          }
        );
        this.getTravels();
      }
    );

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
