import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';


const DATA_URL = 'http//localhost:8080/api/data/';

const httpOptions={
  headers: new HttpHeaders({'Content-Type':'application/json'})
};

@Injectable({
  providedIn: 'root'
})

export class AnnotationsService {

  constructor(private http:HttpClient) { }
  
  getAllAnotations():Observable<any>
  {
    return this.http.get("http://localhost:8080/api/data/recent",{responseType:'text'});
  }

  getAnnotationById(id:String):Observable<any>
  {
    return this.http.get("http://localhost:8080/api/data/"+id,{responseType:'json'});
  }

  createAnnotation(name:String,value:Number,dateInput:Date,description:String,auth:String):Observable<any>
  {
    return this.http.post("http://localhost:8080/api/data/add",{name,value,dateInput,description,auth},httpOptions);
  }

  updateAnnotation(id:String,name:String,value:Number,description:String,auth:String):Observable<any>
  {
    return this.http.put("http://localhost:8080/api/data/"+id,{name,value,description,auth},httpOptions);
  }

  deleteAnnotation(id:String):Observable<any>
  {
    return this.http.delete("http://localhost:8080/api/data"+id,httpOptions);
  }
}
