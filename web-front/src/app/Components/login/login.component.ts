import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../ServicesAndHelpers/_services/auth.service';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import {formLogin} from "../../Models/formLogin";
import {User} from "../../Models/user";


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent implements OnInit {

  form: formLogin = {
    username : "",
    password: "",
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  user : User ={
    username : "",
    email : "",
    roles : [],
  };

  constructor(protected authenticator:AuthService,protected storage:StorageService){}

  ngOnInit(): void {
      if(this.storage.isLoggedIn()){
        this.isLoggedIn = true;
        this.user = this.storage.getUser();
      }
  }

  onSubmit(): void{
    const request = this.form;

    this.authenticator.login(request).subscribe({
      next : data => {
        if(data){
          const userData : User ={
            username: data?.username,
            email: data?.email,
            roles: data?.role,
          };
          const authData ={
            type: data?.type,
            token: data?.token,
          }

          this.storage.saveUser(userData,authData)
          this.isLoginFailed = false;
          this.isLoggedIn = true;
          this.reloadPage();
        }
      },
      error:err =>{
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    });
  }
  reloadPage():void{
    window.location.reload();
  }
}
