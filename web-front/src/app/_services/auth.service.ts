import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders } from '@angular/common/http'
import { Observable } from 'rxjs';
import { StorageService } from './storage.service';

const AUTH_API = 'http://localhost:8080/api/auth/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type':'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private http:HttpClient, private storageService:StorageService){}

  login(username:string,password:string):Observable<any>{
    return this.http.post(AUTH_API+'login',{username,password,},httpOptions);
  }

  register(username:string,email:string,password:string):Observable<any>{
    return this.http.post(AUTH_API+'signup',{username,email,password,},httpOptions);
  }

  logout():void{
    this.storageService.removeUser();
    window.location.reload;
  }
}
