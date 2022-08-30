import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user-model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  errorMessage: string = '';

  constructor(private userService: UserService, private router: Router, private authService: AuthService) { }

  ngOnInit(): void {
  }

  login() {
    /*this.userService.login(this.user).subscribe(
      (data: any) => {
        if (data.username) {
          this.userService.setCurrentUser(data);
          this.router.navigate(['/home']);
        }
        else {
          this.errorMessage = data;
        }
      });*/
      this.authService.login(this.user.username, this.user.password);
  }

  isLoginBlocked() {
    var date = new Date(this.userService.getCurrentUser().loginBlocked);
    date.setMinutes(date.getMinutes());
    var today = new Date();
    if (date > today)
      return false;
    else 
      return false;
  }

}
