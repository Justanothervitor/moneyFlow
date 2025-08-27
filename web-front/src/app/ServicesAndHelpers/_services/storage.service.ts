import { Injectable } from '@angular/core';
import {User} from "../../Models/user";

const LOCAL_KEY='auth_user';
const AUTH_KEY = 'Authorization'
//const ANNOTATION_CACHE = 'AnnotationCache'

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  clean():void{
    window.localStorage.clear();
  }

  public saveUser(user:User,auth:any):void{
    window.localStorage.removeItem(LOCAL_KEY);
    window.localStorage.removeItem(AUTH_KEY);
    window.localStorage.setItem(LOCAL_KEY,JSON.stringify(user));
    window.localStorage.setItem(AUTH_KEY,JSON.stringify(auth))
  }

  /*public saveAnnotationToBeAltered(annotation:any):void{
    window.localStorage.setItem(ANNOTATION_CACHE,annotation);
  }

  public clearAnnotationFromLocalStorage():void{
    window.localStorage.removeItem(ANNOTATION_CACHE);
  }

  public getAnnotationFromLocalStorage():any{
    window.localStorage.getItem(ANNOTATION_CACHE);
  }
*/
  public getUser():any{
    const user = window.localStorage.getItem(LOCAL_KEY);
    if(user)
    {
      return JSON.parse(user);
    }
    return{};
  }

  public getAuthorization():any{
    const auth = window.localStorage.getItem(AUTH_KEY);
    if(auth)
    {
      return JSON.parse(auth);
    }
    return{};
  }

  public isLoggedIn(): boolean{
    const user = window.localStorage.getItem(LOCAL_KEY);
    return !!user;

  }

  public logoff():void{
    window.localStorage.removeItem(LOCAL_KEY);
    window.localStorage.removeItem(AUTH_KEY);
    window.location.reload();
  }
}
