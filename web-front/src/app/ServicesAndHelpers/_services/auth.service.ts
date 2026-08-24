import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {LoginResponse} from "../../Models/loginResponse";
import {formRegister} from "../../Models/formRegister";
import {formLogin} from "../../Models/formLogin";
import {environment} from "../../../environments/environment";
import {TwoFactorRequest} from "../../Models/twoFactorRequest";

const httpOptions = environment.httpOptions;
const AUTH_END = environment.apiEndPointAuth;

@Injectable({
  providedIn: 'root'
})

export class AuthService {

  constructor(private http:HttpClient) { }

  login(data:formLogin):Observable<LoginResponse|null>
  {
    return this.http.post<LoginResponse>(AUTH_END+'/login',data,httpOptions);
  }

  register(data:formRegister):Observable<any>
  {
    return this.http.post(AUTH_END+'/register',data,httpOptions);
  }

  verificateEmail(payload:TwoFactorRequest):Observable<any>
  {
    return this.http.post(AUTH_END+'/verify-email',payload,httpOptions);
  }

  resendCode(payload:string):Observable<any>{
    return this.http.post(AUTH_END+'/send-code',payload,httpOptions);
  }

}
