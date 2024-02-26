import { Injectable } from '@angular/core';

const LOCAL_KEY='auth_user';
const AUTH_KEY = 'Authorization'

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  clean():void{
    window.sessionStorage.clear();
  }

  public saveUser(user:any):void{
    window.sessionStorage.removeItem(LOCAL_KEY);
    window.sessionStorage.setItem(LOCAL_KEY,JSON.stringify({"id":user.id,"username":user.username,"email":user.email,"annotations":user.annotations,"role":user.role}));
    window.sessionStorage.setItem(AUTH_KEY,JSON.stringify(user.type+" "+user.token))
  }

  public getUser():any{
    const user = window.sessionStorage.getItem(LOCAL_KEY);
    if(user)
    {
      return JSON.parse(user);
    }
    return{};
  }

  public getAuthorization():any{
    const auth = window.sessionStorage.getItem(AUTH_KEY);
    if(auth)
    {
      return JSON.parse(auth);
    }
    return{};
  }

  public isLoggedIn(): boolean{
    const user = window.sessionStorage.getItem(LOCAL_KEY);
    if(user)
    {
      return true;
    }
    return false;
  }

  public logoff():void{
    window.sessionStorage.removeItem(LOCAL_KEY);
    window.sessionStorage.removeItem(AUTH_KEY);
    window.location.reload();
  }
}
