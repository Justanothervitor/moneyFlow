import { Component} from '@angular/core';
import { AuthService } from '../../ServicesAndHelpers/_services/auth.service';
import {formRegister} from "../../Models/formRegister";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss'
})
export class RegisterComponent{

  form: formRegister ={
    username : "",
    email : "",
    password : ""
  };

  isSucessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private autenticator:AuthService){}

  onSubmit():void{
    const request = this.form;

    this.autenticator.register(request).subscribe({
      next: data =>{
        console.log(data);
        this.isSucessful = true;
        this.isSignUpFailed = true;
      },
      error: err =>{
        this.errorMessage = err.error.message;
        this.isSignUpFailed = true;
      }
    });
  }
}
