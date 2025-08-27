import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {LoginResponse} from "../../Models/LoginResponse";
import {formRegister} from "../../Models/formRegister";
import {formLogin} from "../../Models/formLogin";
import {environment} from "../../../environments/environment";

const httpOptions = environment.httpOptions;
const AUTH_END = environment.apiUrl+"auth/";

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http:HttpClient) { }



  login(data:formLogin):Observable<LoginResponse|null>
  {
    return this.http.post<LoginResponse>(AUTH_END+'login',data,httpOptions);
  }

  register(data:formRegister):Observable<any>
  {
    return this.http.post(AUTH_END+'signup',data,httpOptions);
  }

}
