import { Component, OnInit } from '@angular/core';
import { HomeservicesService } from '../_services/homeservices.service';
import { StorageService } from '../_services/storage.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})

export class HomeComponent implements OnInit{

  content?: string;


  constructor(private welcome:HomeservicesService,private storage:StorageService){}

  ngOnInit(): void {
    if(!this.storage.isLoggedIn()){
      this.welcome.getWelcome().subscribe({
        next: data => {
          this.content = data;
        },
        error: err => {console.log(err)
        if(err.error){
          this.content = JSON.parse(err.error).message;
        }else{
          this.content = "Error with status" + err.status;
        }
        }
      });
    }else{
      this.welcome.getWelcomeLogged().subscribe({
        next: data => {
          this.content = data;
        },
        error: err => {console.log(err)
        if(err.error){
          this.content = JSON.parse(err.error).message;
        }else{
          this.content = "Error with status" + err.status;
        }
        }
      });
    }

  }

}
