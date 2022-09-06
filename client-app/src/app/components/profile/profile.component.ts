import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/model/user-model';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
  user: User = new User();

  constructor(private userService: UserService) { }

  ngOnInit(): void {
    this.userService.getUserByUsername().subscribe(
      (data: any) => {
        this.user = data;
      }
    );
  }

}
