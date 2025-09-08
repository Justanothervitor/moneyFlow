import { Component, OnInit } from '@angular/core';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';
import {AuthService} from "../../ServicesAndHelpers/_services/auth.service";

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit{

  currentUser : any;
  isLoading:boolean = false;

  constructor(private storage:StorageService,private auth:AuthService){}

  ngOnInit(): void {
      this.isLoading = true;
      this.currentUser = this.auth.requestProfileData().subscribe(data =>{
        this.currentUser = data;
        this.isLoading = false;
      },err =>{
        console.log(err);
        this.isLoading = false;
      })
  }
}
