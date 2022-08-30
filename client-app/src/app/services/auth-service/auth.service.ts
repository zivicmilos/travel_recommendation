import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user-service/user.service';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  baseUrl: String = 'http://localhost:8080/';

  headers = new HttpHeaders({'Content-Type' : 'application/json', 
                             'Authorization' : `Bearer ${localStorage['jwt']}`});

  constructor(private http: HttpClient, private router: Router, private userService: UserService) { }

  login(username: string, password: string){
    this.http.post<any>(this.baseUrl + "login", {
      username: username,
      password: password
    }).subscribe(
      {
        next: (response) => {
          localStorage.setItem('jwt', response.token);
          console.log(response.token)
          this.router.navigate(['/home']).then(()=>{
            localStorage.setItem('username', username);
            location.reload()});
        },
        error: (error: HttpErrorResponse) => {
        }
      })
  }
}
