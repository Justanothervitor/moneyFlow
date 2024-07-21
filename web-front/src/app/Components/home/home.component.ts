import { Component,OnInit } from '@angular/core';
import { StorageService } from '../../Services&Helpers/_services/storage.service';
@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})

export class HomeComponent implements OnInit{

  content?: string;


  constructor(private storage:StorageService){}

  ngOnInit(): void {
    if(!this.storage.getAuthorization())
      {
        this.content ="Welcome to MoneyFlow" + this.storage.getUser().username;
      }else{
        this.content = "Welcome to MoneyFlow, please login to use your services";
      }
  }

}
