import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Like } from '../../model/like-model';
import { Travel } from '../../model/travel-model';
import {User} from '../../model/user-model';
import { TravelDto } from 'src/app/dto/travel-dto';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class DestinationService {
  baseUrl: String = environment.appUrl + 'destination';
  headers = new HttpHeaders({'Content-Type' : 'application/json',
                             'Authorization' : `Bearer ${localStorage['jwt']}`});

  constructor(private http: HttpClient) { }

  findDestinations(transportationType: String, budget: Number, destinationType: String,
    weather: String, continent: String) {
    return this.http.get(this.baseUrl + '?transportationType='+transportationType+
    '&budget='+budget+'&destinationType='+destinationType+'&weather='+weather+'&continent='+continent, { headers: this.headers });
  }

  like(like: Like) {
    return this.http.post(this.baseUrl + '/like', like, { headers: this.headers });
  }
  reserve(travel: TravelDto) {
    return this.http.post(this.baseUrl + '/reservation', travel, { headers: this.headers });
  }
}
