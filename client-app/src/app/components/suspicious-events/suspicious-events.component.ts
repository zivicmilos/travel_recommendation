import { Component, OnInit } from '@angular/core';
import { SuspiciousEvent } from 'src/app/model/suspicious-event-model';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-suspicious-events',
  templateUrl: './suspicious-events.component.html',
  styleUrls: ['./suspicious-events.component.css']
})
export class SuspiciousEventsComponent implements OnInit {
  suspiciousEvents: SuspiciousEvent[] = [];

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getSuspiciousEvents().subscribe(
      (data: any) => {
        this.suspiciousEvents = data;
      }
    );
  }

}
