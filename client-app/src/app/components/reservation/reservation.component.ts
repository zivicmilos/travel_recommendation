import { Component, Input, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Destination } from 'src/app/model/destination-model';
import { Travel } from 'src/app/model/travel-model';
import { TravelDto } from 'src/app/dto/travel-dto';
import { DestinationService } from 'src/app/services/destination-service/destination.service';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.css']
})
export class ReservationComponent implements OnInit {
  destination: Destination = new Destination;
  transportationType: string = '';
  travelDate: Date = new Date();

  constructor(private destinationService: DestinationService, private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.destination = history.state.data;
  }

  reserve() {
    var travel: TravelDto = new TravelDto();
    travel.user = this.userService.getCurrentUser().username;
    travel.destination = this.destination.location.city;
    travel.travelDate = this.travelDate;
    travel.transportationType = this.transportationType;

    this.destinationService.reserve(travel).subscribe(
      () => {
        this.userService.getUserByUsername().subscribe(
          (data: any) => {
            this.userService.setCurrentUser(data);
            this.router.navigate(['/home']);
          }
        );
      }
    );
  }

}
