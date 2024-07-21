import { Injectable } from '@angular/core';

const LOCAL_KEY='auth_user';
const AUTH_KEY = 'Authorization'

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() { }

  clean():void{
    window.localStorage.clear();
  }

  public saveUser(user:any):void{
    window.localStorage.removeItem(LOCAL_KEY);
    window.localStorage.removeItem(AUTH_KEY);
    window.localStorage.setItem(LOCAL_KEY,JSON.stringify({"cpf":user.cpf,"username":user.username,"email":user.email,"role":user.authority}));
    window.localStorage.setItem(AUTH_KEY,JSON.stringify(user.type+" "+user.token))
  }

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
    if(user)
    {
      return true;
    }
    return false;
  }

  public logoff():void{
    window.localStorage.removeItem(LOCAL_KEY);
    window.localStorage.removeItem(AUTH_KEY);
    window.location.reload();
  }
}
