import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../../environments/environment";
import {CreateAnnotation} from "../../Models/formCreateAnnotation";
import {Annotation} from "../../Models/Annotation";
import {UpdateAnnotation} from "../../Models/formUpdateAnnotation";

const DATA_END = environment.apiUrl+"data/";

const httpOptions = environment.httpOptions;
@Injectable({
  providedIn: 'root'
})
export class AnnotationsService {

  constructor(protected http:HttpClient) { }

  createAnnotation(data:CreateAnnotation):Observable<any>
  {
    return this.http.post(DATA_END+'add',data,httpOptions);
  }

  getAllAnotations():Observable<Array<Annotation>>
  {
    return this.http.get<Array<Annotation>>(DATA_END+'recent',httpOptions);
  }

  getOnlyOneAnotation(id:String):Observable<Annotation>
  {
    return this.http.get<Annotation>(DATA_END+id,httpOptions);
  }

  updateAnotation(id:String,data:UpdateAnnotation):Observable<any>
  {
    return this.http.put(DATA_END+"update/"+id,data,httpOptions);
  }

  deleteAnotation(id:String):Observable<any>
  {
    return this.http.delete(DATA_END+"delete/"+id,httpOptions);
  }
}
