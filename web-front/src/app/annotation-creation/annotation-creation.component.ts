import { Component, OnInit } from '@angular/core';
import { AnnotationsService } from '../_services/annotations.service';
import { StorageService } from '../_services/storage.service';

@Component({
  selector: 'app-annotation-creation',
  templateUrl: './annotation-creation.component.html',
  styleUrl: './annotation-creation.component.scss'
})
export class AnnotationCreationComponent implements OnInit{

  form : any ={
    name : null,
    value: null,
    dateInput: null,
    description: null
  };

  isLoggedIn = false;
  isAnnotationCreationFailed = false;
  errorMessage ='';

  constructor(private annotation:AnnotationsService,private storage:StorageService){}

  ngOnInit(): void {
      if(this.storage.isLoggedIn()){
        this.isLoggedIn = true;
      }
  }

  onSubmit():void{
    const { name, value, dateInput, description } = this.form;

    this.annotation.createAnnotation(name,value,dateInput,description,this.storage.getAuthorization()).subscribe({
      next: data =>{
        console.log(data);
      },
      error: err =>{
        this.errorMessage = err.error.message;
        this.isAnnotationCreationFailed = true;
      }
    });
  }
  
}
