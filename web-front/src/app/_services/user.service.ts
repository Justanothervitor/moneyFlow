import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const DATA_URL = 'http://localhost:8080/api/data/';

const httpOptions = {
  headers: new HttpHeaders({'Content-Type':'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class UserService {

  constructor(private http:HttpClient) {}

  getHomeAnnotations():Observable<any>{
    return this.http.get(DATA_URL+'home',{responseType: 'text'});
  }

  getAllAnnotations(): Observable<any>{
    return this.http.get(DATA_URL+'all',{responseType :'text'});
  }
  getOneAnnotatition(id:String): Observable<String>{
    return this.http.get(DATA_URL+id,{responseType :'text'});
  }
  addAnnotation(): Observable<>{
    return this.http.post(DATA_URL+'add',{responseType :'text'});
  }
}
