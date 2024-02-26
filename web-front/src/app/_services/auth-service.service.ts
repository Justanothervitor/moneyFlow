import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';


const AUTH_END = 'http://localhost:8080/api/auth/';

const httpOptions ={
  headers: new HttpHeaders({'Content-Type':'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class AuthServiceService {

  constructor(private http:HttpClient,private storage:StorageService) { }

  login(username:String,password:String):Observable<any>
  {
    return this.http.post(AUTH_END+'login',{username,password},httpOptions);
  }

  register(username:String,email:String,password:String):Observable<any>
  {
    return this.http.post(AUTH_END+'signup',{username,email,password},httpOptions);
  }

}
