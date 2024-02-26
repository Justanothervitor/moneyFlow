import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

const HOME_END = 'http://localhost:8080/api/home/';

@Injectable({
  providedIn: 'root'
})

export class HomeservicesService {

  constructor(private http:HttpClient) { }

  getWelcome():Observable<any>
  {
    return this.http.get(HOME_END+'welcome',{responseType:'text'});
  }

  getWelcomeLogged():Observable<any>
  {
    return this.http.get(HOME_END+'logged',{responseType:'text'});
  }
}
