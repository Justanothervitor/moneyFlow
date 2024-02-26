import { Component } from '@angular/core';
import { StorageService } from './_services/storage.service';
import { AuthServiceService } from './_services/auth-service.service';
import { AnnotationsService } from './_services/annotations.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrl: './app.component.scss'
})

export class AppComponent {
  private role: string[] = [];
  private userAnnotatitons: string[] = [];
  isLoggedIn = false;
  username? : string;

  constructor(private storage:StorageService,private authenticator:AuthServiceService,private annotations:AnnotationsService){}

  ngOnInit():void {
    this.isLoggedIn = this.storage.isLoggedIn();

    if(this.isLoggedIn)
    {
      const user = this.storage.getUser();
      this.role = user.roles;
      this.userAnnotatitons = user.annotations;
      this.username = user.username;
    }
  }

  logout(): void{
    this.storage.logoff();
  }
  
}
