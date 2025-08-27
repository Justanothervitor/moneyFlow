import { Component, OnInit } from '@angular/core';
import { StorageService } from './ServicesAndHelpers/_services/storage.service';
import { AuthService } from './ServicesAndHelpers/_services/auth.service';
import { AnnotationsService } from './ServicesAndHelpers/_services/annotations.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent implements OnInit {
  isLoggedIn = false;
  username? : string;

  constructor(private storage:StorageService,private authenticator:AuthService,private annotations:AnnotationsService){}

  ngOnInit():void {
    this.isLoggedIn = this.storage.isLoggedIn();

    if(this.isLoggedIn)
    {
      const user = this.storage.getUser();
      this.username = user.username;
    }
  }

  logout(): void{
    this.storage.logoff();
  }

}
