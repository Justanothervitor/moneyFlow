import { Component, OnInit } from '@angular/core';
import { UserService } from '../_services/user.service';

@Component({
  selector: 'app-board-enterpress',
  templateUrl: './board-enterpress.component.html',
  styleUrl: './board-enterpress.component.css'
})

export class BoardEnterpressComponent implements OnInit{

  content?: string;

  constructor(private userService:UserService){ }
  ngOnInit(): void {
    this.userService.getEnterpressBoard().subscribe({
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
    })
}
}