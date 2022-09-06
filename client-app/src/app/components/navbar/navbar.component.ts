import { Component, HostListener, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from 'src/app/model/user-model';
import { UserService } from 'src/app/services/user-service/user.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit {
  isAuthenticated: boolean = false;
  isAdmin: boolean = false;
  user: User = new User();

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
    this.isLoggedIn();

  }

  logOut(): void {
    document.cookie = "token= ; expires = Thu, 01 Jan 1970 00:00:00 GMT";
    this.userService.removeCurrentUser();
    localStorage.removeItem('jwt');
    localStorage.removeItem('transportationType');
    localStorage.removeItem('budget');
    localStorage.removeItem('destinationType');
    localStorage.removeItem('weather');
    localStorage.removeItem('continent');
    localStorage.removeItem('destination');
    setTimeout(() => {
      window.location.reload();
    }, 1000);
  }

  isLoggedIn() {
    if (this.userService.getCurrentUser() !== null) {
      this.isAuthenticated = true;
      this.checkIsAdmin();
      this.user = this.userService.getCurrentUser();
    }
    else {
      this.isAuthenticated = false;
      this.router.navigate(['/']);
    }
  }

  checkIsAdmin() {
    if (this.userService.getCurrentUser().roles[0].name === 'ROLE_ADMIN')
      this.isAdmin = true;
    else
      this.isAdmin = false;
  }

}
