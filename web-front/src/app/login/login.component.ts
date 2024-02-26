import { Component,OnInit } from '@angular/core';
import { AuthServiceService } from '../_services/auth-service.service';
import { StorageService } from '../_services/storage.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})

export class LoginComponent implements OnInit {

  form : any ={
    username : null,
    password : null
  };
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  role: string[] = [];
  annotation: string[] = [];

  constructor(private authenticator:AuthServiceService,private storage:StorageService){}

  ngOnInit(): void {
      if(this.storage.isLoggedIn()){

        this.isLoggedIn = true;
        this.role = this.storage.getUser().role;
        this.annotation = this.storage.getUser().annotations;
      }
  }

  onSubmit(): void{
    const { username, password } = this.form;

    this.authenticator.login(username,password).subscribe({
      next : data => {
        this.storage.saveUser(data);

        this.isLoginFailed = false;
        this.isLoggedIn = true;
        this.role = this.storage.getUser().role;
        this.annotation = this.storage.getUser().annotation;
        this.reloadPage();
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
