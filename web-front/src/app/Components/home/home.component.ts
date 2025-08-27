import { Component,OnInit } from '@angular/core';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})

export class HomeComponent implements OnInit{

  content?: string;


  constructor(private storage:StorageService){}

  ngOnInit(): void {
    if(this.storage.isLoggedIn()){
      this.content = this.storage.getUser().username;
    }
  }

}
