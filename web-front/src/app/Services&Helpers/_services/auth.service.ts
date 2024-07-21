import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';



const AUTH_END = 'http://localhost:8080/api/auth/';

const httpOptions ={
  headers: new HttpHeaders({'Content-Type':'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http:HttpClient) { }

  login(username:string,password:string):Observable<any>
  {
    return this.http.post(AUTH_END+'login',{username,password},httpOptions);
  }

  register(username:string,email:string,password:string):Observable<any>
  {
    return this.http.post(AUTH_END+'signup',{username,email,password},httpOptions);
  }

}
