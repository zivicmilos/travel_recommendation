import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { GoogleMap } from '@angular/google-maps';
import { Router } from '@angular/router';
import { UserDto } from 'src/app/dto/user-dto';
import { Coordinates } from 'src/app/model/coordinates-model';
import { Location } from 'src/app/model/location-model';
import { User } from 'src/app/model/user-model';
import { AuthService } from 'src/app/services/auth-service/auth.service';
import { UserService } from 'src/app/services/user-service/user.service';
import continents from '../../../assets/data/continents.json';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  user: User = new User();
  regUser: UserDto = new UserDto();

  @ViewChild('myGoogleMap', { static: false })
  map!: GoogleMap;

  center!: google.maps.LatLngLiteral;
  marker: any;

  constructor(private userService: UserService, private router: Router, private authService: AuthService, private http: HttpClient) { }

  ngOnInit(): void {
    navigator.geolocation.getCurrentPosition((position) => {
      this.center = {
        lat: position.coords.latitude,
        lng: position.coords.longitude,
      }
    });
  }

  login() {
    this.authService.login(this.user.username, this.user.password).subscribe(
      {
        next: (response) => {
          localStorage.setItem('jwt', response.token);
          console.log(response.token)
          this.userService.getUserByUsername(new HttpHeaders({
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${response.token}`
          })).subscribe(
            (data: any) => {
              if (data.roles[0].name === 'ROLE_CLIENT') {
                this.router.navigate(['/home']).then(() => {
                  this.userService.setCurrentUser(data);
                  location.reload()
                });
              } else if (data.roles[0].name === 'ROLE_ADMIN') {
                this.router.navigate(['/admin']).then(() => {
                  this.userService.setCurrentUser(data);
                  location.reload()
                });
              }
            }
          )
        }
      });
  }

  register() {
    this.authService.register(this.regUser).subscribe(
      () => {
        this.authService.login(this.regUser.username, this.regUser.password).subscribe(
          {
            next: (response) => {
              localStorage.setItem('jwt', response.token);
              console.log(response.token)
              this.userService.getUserByUsername(new HttpHeaders({
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${response.token}`
              })).subscribe(
                (data: any) => {
                  if (data.roles[0].name === 'ROLE_CLIENT') {
                    this.router.navigate(['/home']).then(() => {
                      this.userService.setCurrentUser(data);
                      location.reload()
                    });
                  }
                }
              )
            }
          });
      }
    );
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


  eventHandler(event: any, name: string) {
    console.log(event, name);

    if (name === 'mapClick') {
      this.dropMarker(event)
    }
  }

  dropMarker(event: any) {
    this.marker = {
      position: {
        lat: event.latLng.lat(),
        lng: event.latLng.lng(),
      },
      options: {
        animation: google.maps.Animation.DROP,
      },
    }
    this.regUser.location.coordinates.latitude = event.latLng.lat();
    this.regUser.location.coordinates.longitude = event.latLng.lng();

    this.http.get<any>('https://maps.googleapis.com/maps/api/geocode/json?latlng=' + event.latLng.lat() + ',' + event.latLng.lng() + '&key=AIzaSyAfeqYRd42QxNimU5mYU8ixQHToZNOoL5s&language=en').subscribe(
      (response) => {
        this.regUser.location.city = response.results[0].address_components[2].long_name;
        this.regUser.location.country = response.results[0].address_components[5].long_name;
        let s: string = response.results[0].address_components[5].short_name;
        this.regUser.location.continent = continents[s as keyof typeof continents];
      }
    )
  }

}
