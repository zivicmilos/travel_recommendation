import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { retry } from 'rxjs';
import { UserDto } from 'src/app/dto/user-dto';
import { environment } from 'src/environments/environment';
import { UserService } from '../user-service/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  baseUrl: String = environment.appUrl;

  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage['jwt']}`
  });

  constructor(private http: HttpClient, private router: Router, private userService: UserService) { }

  login(username: string, password: string) {
    return this.http.post<any>(this.baseUrl + "login", {
      username: username,
      password: password
    });
  }

  register(userDto: UserDto) {
    return this.http.post<any>(this.baseUrl + 'register', userDto);
  }
}
