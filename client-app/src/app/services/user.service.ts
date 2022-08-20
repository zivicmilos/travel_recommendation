import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Travel } from '../model/travel-model';
import { User } from '../model/user-model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl: String = 'http://localhost:8080/';
  constructor(private http: HttpClient) { }

  login(user: User) {
    return this.http.post(this.baseUrl + 'user/login', user);
  }

  getTravelsByUsername() {
    return this.http.get(this.baseUrl + 'user/travel?username='+this.getCurrentUser().username);
  }

  cancelTravel(travel: Travel) {
    return this.http.post(this.baseUrl + 'user/travel/cancel', travel);
  }

  setCurrentUser(user: User) {
    sessionStorage.setItem('user', JSON.stringify(user));
  }

  getCurrentUser() {
    let s = sessionStorage.getItem('user');
    let ss: string = '';
    if (s !== null) {
      ss = s;
    }
    return JSON.parse(ss);
  }
}
