import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TravelDto } from 'src/app/dto/travel-dto';
import { Travel } from '../../model/travel-model';
import { User } from '../../model/user-model';

@Injectable({
  providedIn: 'root'
})
export class UserService {
  baseUrl: string = 'http://localhost:8080/user';
  headers = new HttpHeaders({
    'Content-Type': 'application/json',
    'Authorization': `Bearer ${localStorage['jwt']}`
  });

  constructor(private http: HttpClient) { }

  getUserByUsername(): Observable<Object>;

  getUserByUsername(headers: HttpHeaders): Observable<Object>;

  getUserByUsername(headers?: HttpHeaders) {
    if (headers != null)
      return this.http.get(this.baseUrl, { headers: headers });
    else
      return this.http.get(this.baseUrl, { headers: this.headers });
  }

  getTravelsByUsername() {
    return this.http.get(this.baseUrl + '/travel', { headers: this.headers });
  }

  cancelTravel(travel: TravelDto) {
    return this.http.post(this.baseUrl + '/travel/cancel', travel, { headers: this.headers });
  }

  setCurrentUser(user: User) {
    localStorage.setItem('user', JSON.stringify(user));
  }

  getCurrentUser() {
    let s = localStorage.getItem('user');
    let ss: string = '';
    if (s !== null) {
      ss = s;
      return JSON.parse(ss);
    }
    return null;
  }

  removeCurrentUser() {
    localStorage.removeItem('user');
  }
}
