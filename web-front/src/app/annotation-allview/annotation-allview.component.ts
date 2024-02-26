import { Component, OnInit } from '@angular/core';
import { AnnotationsService } from '../_services/annotations.service';
import { StorageService } from '../_services/storage.service';

@Component({
  selector: 'app-annotation-allview',
  templateUrl: './annotation-allview.component.html',
  styleUrl: './annotation-allview.component.scss'
})

export class AnnotationAllviewComponent implements OnInit{

  content?: string;

  constructor(private annotation:AnnotationsService,private storage:StorageService){}

  ngOnInit(): void {
      this.annotation.getAllAnotations().subscribe({
        next : data=>{
          this.content = data;
        },
        error: err =>{ console.log(err);
          if(err.error){
            this.content = JSON.parse(err.error).message;
          }else{
            this.content = "Error with status" + err.status;
          }
        }
      })
  }
}