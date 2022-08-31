import { HttpHeaders } from '@angular/common/http';
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
    this.authService.login(this.user.username, this.user.password).subscribe(
      {
        next: (response) => {
          localStorage.setItem('jwt', response.token);
          console.log(response.token)
          //setTimeout(() => {
          this.userService.getUserByUsername(new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${response.token}`
          })).subscribe(
            (data: any) => {
              if (data.roles[0].name === 'ROLE_CLIENT') {
                this.router.navigate(['/home']).then(() => {
                  //localStorage.setItem('username', this.user.username);
                  this.userService.setCurrentUser(data);
                  location.reload()
                });
              } else if (data.roles[0].name === 'ROLE_ADMIN') {
                this.router.navigate(['/admin']).then(() => {
                  //localStorage.setItem('username', this.user.username);
                  this.userService.setCurrentUser(data);
                  location.reload()
                });
              }
            }
          )
        }/*,2000)}/*,
          error: (error: HttpErrorResponse) => {
          }*/
      });
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
