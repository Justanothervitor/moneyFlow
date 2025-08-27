import { Component, OnInit } from '@angular/core';
import { StorageService } from '../../ServicesAndHelpers/_services/storage.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit{

  currentUser : any;

  constructor(private storage:StorageService){}

  ngOnInit(): void {
      this.currentUser = this.storage.getUser();
  }
}
