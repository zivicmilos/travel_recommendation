import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { TravelDto } from 'src/app/model/travelDto-model';
import { Travel } from '../../model/travel-model';
import { User } from '../../model/user-model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl: String = 'http://localhost:8080/user';
  headers = new HttpHeaders({'Content-Type' : 'application/json', 
                             'Authorization' : `Bearer ${localStorage['jwt']}`});

  constructor(private http: HttpClient) { }

  getTravelsByUsername() {
    return this.http.get(this.baseUrl + '/travel?username='+this.getCurrentUser().username, { headers: this.headers });
  }

  getUserByUsername(username: string) {
    return this.http.get(this.baseUrl + '?username='+username, { headers: this.headers });
  }

  cancelTravel(travel: TravelDto) {
    return this.http.post(this.baseUrl + '/travel/cancel', travel, { headers: this.headers });
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
