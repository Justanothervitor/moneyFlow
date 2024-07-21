import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

const DATA_END = 'http://localhost:8080/api/data/';

const httpOptions ={
  headers: new HttpHeaders({'Content-Type':'application/json'})
};

@Injectable({
  providedIn: 'root'
})
export class AnnotationsService {

  constructor(protected http:HttpClient) { }

  createAnnotation(name:String,value:Number,userInputDate:String,description:String):Observable<any>
  {
    return this.http.post(DATA_END+'add',{name,value,userInputDate,description},httpOptions);
  }

  getAllAnotations():Observable<any>
  {
    return this.http.get(DATA_END+'recent',httpOptions);
  }
}
