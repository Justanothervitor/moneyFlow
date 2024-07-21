import { Component, OnInit } from '@angular/core';
import { StorageService } from './Services&Helpers/_services/storage.service';
import { AuthService } from './Services&Helpers/_services/auth.service';
import { AnnotationsService } from './Services&Helpers/_services/annotations.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent implements OnInit {
  private role: string[] = [];
  isLoggedIn = false;
  username? : string;

  constructor(private storage:StorageService,private authenticator:AuthService,private annotations:AnnotationsService){}

  ngOnInit():void {
    this.isLoggedIn = this.storage.isLoggedIn();

    if(this.isLoggedIn)
    {
      const user = this.storage.getUser();
      this.role = user.roles;
      this.username = user.username;
    }
  }

  logout(): void{
    this.storage.logoff();
  }
  
}
