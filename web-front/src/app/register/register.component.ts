import { Component } from '@angular/core';
import { AuthService } from '../_services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  form : any ={
    username : null,
    email : null,
    password : null
  };

  isSucessful = false;
  isSignUpFailed = false;
  errorMessage = '';

  constructor(private authService:AuthService){ }

  onSubmit():void{
    const {username,email,password} = this.form;

    this.authService.register(username,email,password).subscribe({
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
