import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import {environment} from "../../../environments/environment";
import {CreateAnnotation} from "../../Models/formCreateAnnotation";
import {Annotation} from "../../Models/annotation";
import {UpdateAnnotation} from "../../Models/formUpdateAnnotation";
import {NotesFilterRequest} from "../../Models/notesFilterRequest";
import {NotePagesResponse} from "../../Models/notePagesResponse";

const DATA_END = environment.apiEndPointDataNotes + "/";
const NOTES_API = '/api/v1/data/notes';

const httpOptions = environment.httpOptions;
@Injectable({
  providedIn: 'root'
})
export class AnnotationsService {

  constructor(protected http:HttpClient) { }

  /**
   * Pesquisa notas com filtro, paginação e ordenação.
   * Backend: POST /api/v1/data/notes/search
   */
  searchAnnotations(filter: NotesFilterRequest): Observable<NotePagesResponse<Annotation>> {
    return this.http.post<NotePagesResponse<Annotation>>(NOTES_API + '/search', filter);
  }

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

