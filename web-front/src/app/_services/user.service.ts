import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const TEST_URL = 'http://localhost:8080/api/test/';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) {}

  getPublicContent():Observable<any>{
    return this.http.get(TEST_URL+'all',{responseType: 'text'});
  }

  getUserBoard(): Observable<any>{
    return this.http.get(TEST_URL+'user',{responseType :'text'});
  }
  getEnterpressBoard(): Observable<any>{
    return this.http.get(TEST_URL+'enterpress',{responseType :'text'});
  }
  getAdminBoard(): Observable<any>{
    return this.http.get(TEST_URL+'admin',{responseType :'text'});
  }
}
